package github.Zcy19980412.service.impl;


import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;
import github.Zcy19980412.service.UserService;
import github.Zcy19980412.util.JdbcUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


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

            connection = JdbcUtils.getConnection();
            preparedStatement = JdbcUtils.getPreparedStatement(
                    connection, "insert into user values(?,?,?,?,?,?)");

            preparedStatement.setString(1, null);
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, userRequestDTO.getUsername());
            preparedStatement.setString(5, userRequestDTO.getRealName());
            preparedStatement.setString(6, userRequestDTO.getPassword());
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
    public List<UserResponseDTO> list() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        try {
            connection = JdbcUtils.getConnection();
            preparedStatement = JdbcUtils.getPreparedStatement(
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
            connection = JdbcUtils.getConnection();
            preparedStatement = JdbcUtils.getPreparedStatement(connection, "delete from user where id = ?");
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

    public static boolean checkUserNameExist(String username) throws Exception {
        if (StringUtils.isBlank(username)) {
            throw new Exception("");
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcUtils.getConnection();
            preparedStatement = JdbcUtils.getPreparedStatement(
                    connection, "select * from user where user_name = ?");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Object object = resultSet.getObject(1);
                if (object != null) {
                    return true;
                }
            }
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
        throw new Exception();
    }


}
