package github.Zcy19980412.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import github.Zcy19980412.Constant.Constant;
import github.Zcy19980412.config.SystemProperties;
import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;
import github.Zcy19980412.service.UserService;
import github.Zcy19980412.core.JdbcUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author calvin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Autowired
    private SystemProperties systemProperties;

    @Override
    public void save(UserRequestDTO userRequestDTO) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //校验用户名是否已存在
            boolean existSameUsername = checkUserNameExist(userRequestDTO.getUsername());
            if (existSameUsername) {
                throw new Exception("此用户名已存在");
            }

            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "insert into user values(?,?,?,?,?,?)");

            preparedStatement.setString(1, null);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(4, userRequestDTO.getUsername());
            preparedStatement.setString(5, userRequestDTO.getRealName());
            preparedStatement.setString(6, userRequestDTO.getPassword());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<UserResponseDTO> list() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "select * from user");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setId(resultSet.getLong("id"));
                userResponseDTO.setCreateTime(resultSet.getDate("create_time"));
                userResponseDTO.setUpdateTime(resultSet.getDate("update_time"));
                userResponseDTO.setUsername(resultSet.getString("user_name"));
                userResponseDTO.setRealName(resultSet.getString("real_name"));
                userResponseDTOS.add(userResponseDTO);
            }
            return userResponseDTOS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userResponseDTOS;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(connection, "delete from user where id = ?");
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean checkUserNameExist(String username){
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "select * from user where user_name = ?");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object object = resultSet.getObject(1);
                if (object != null) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException();
    }

    @Override
    public String login(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null
                || StringUtils.isBlank(userRequestDTO.getUsername())
                || StringUtils.isBlank(userRequestDTO.getPassword())
        ) {
            throw new RuntimeException("缺少参数");
        }
        long userId = checkLogin(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        if (userId == 0) {
            throw new RuntimeException("账号或密码错误");
        }
        userRequestDTO.setId(userId);
        return createToken(userRequestDTO);
    }

    private String createToken(UserRequestDTO userRequestDTO) {
        java.util.Date now = DateTime.now();
        java.util.Date overDueTime = DateUtil.offsetHour(now, 24);
        Map<String,Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT, now); //开始时间
        payload.put(JWTPayload.EXPIRES_AT, overDueTime);//过期时间
        payload.put(JWTPayload.NOT_BEFORE, now);//生效时间
        payload.put(Constant.LOGIN.USERNAME, userRequestDTO.getUsername());
        payload.put(Constant.LOGIN.PASSWORD, userRequestDTO.getPassword());
        payload.put(Constant.LOGIN.USER_ID, userRequestDTO.getId());
        String key = systemProperties.getJwtSalt();
        return JWTUtil.createToken(payload, key.getBytes());
    }

    private long checkLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection,
                    "select id from user where user_name = ? and `password` = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            if (!next) {
                throw new RuntimeException("账号或密码不存在");
            }
            return resultSet.getLong(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录错误:" + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
