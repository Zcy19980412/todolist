package github.Zcy19980412.service.impl;


import github.Zcy19980412.core.JdbcUtils;
import github.Zcy19980412.core.RequestThreadContext;
import github.Zcy19980412.domain.dto.request.HabitRequestDTO;
import github.Zcy19980412.domain.dto.response.HabitResponseDTO;
import github.Zcy19980412.domain.entity.User;
import github.Zcy19980412.service.HabitRecordService;
import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author calvin
 */
@Service
public class HabitServiceImpl implements HabitService {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Autowired
    private HabitRecordService habitRecordService;


    @Override
    public void save(HabitRequestDTO habitRequestDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //校验用户名是否已存在
            boolean existSameHabitName = checkHabitExist(
                    habitRequestDTO.getName(),RequestThreadContext.getUser().getId());
            if (existSameHabitName) {
                throw new Exception("此习惯已存在");
            }

            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "insert into habit values(?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, null);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(4, RequestThreadContext.getUser().getId());
            preparedStatement.setInt(5, habitRequestDTO.getGapDays());
            preparedStatement.setString(6, habitRequestDTO.getName());
            preparedStatement.setString(7, habitRequestDTO.getDescription());
            preparedStatement.setBigDecimal(8, new BigDecimal(0));
            preparedStatement.setLong(9, habitRequestDTO.getImportantRate());
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
    public List<HabitResponseDTO> list() {
        User user = RequestThreadContext.getUser();
        Long userId = user.getId();
        ArrayList<HabitResponseDTO> habitResponseDTOS = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "SELECT habit.*,count(*) as totalDays from habit " +
                            "left JOIN habit_record on habit_record.habit_id = habit.id " +
                            "where user_id = ? " +
                            "GROUP BY habit_id " +
                            "order by important_rate desc");
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                habitResponseDTOS.add(
                        HabitResponseDTO
                                .builder()
                                .id(resultSet.getLong("id"))
                                .userId(resultSet.getLong("user_id"))
                                .createTime(Date.from(resultSet.getTimestamp("create_time").toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()))
                                .updateTime(Date.from(resultSet.getTimestamp("update_time").toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()))
                                .gapDays(resultSet.getInt("gap_days"))
                                .name(resultSet.getString("name"))
                                .description(resultSet.getString("description"))
                                .doneRate(resultSet.getBigDecimal("done_rate"))
                                .importantRate(resultSet.getInt("important_rate"))
                                .totalDays(resultSet.getInt("totalDays"))
                                .doneDays(resultSet.getBigDecimal("done_rate").multiply(new BigDecimal(resultSet.getInt("totalDays"))).intValue())
                                .build()
                );
            }
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
        habitResponseDTOS.forEach(habitResponseDTO ->
                habitResponseDTO.setNeedCheck(
                        habitRecordService.checkGapDay(habitResponseDTO.getCreateTime(), habitResponseDTO.getGapDays())
                        && habitRecordService.waitCheck(habitResponseDTO.getId())
                ));
        return habitResponseDTOS;
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "delete from habit where id = ?");
            preparedStatement.setLong(1, id);
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
    public void check(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection,
                    "UPDATE habit_record set checked = 1 where habit_id = ? order by id desc limit 1");
            preparedStatement.setLong(1, id);
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
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "UPDATE habit set done_rate = IFNULL(" +
                            "(SELECT (SELECT count(*) FROM `habit_record` where habit_id = habit.id and checked = 1)" +
                            "/(SELECT count(*) FROM `habit_record` where habit_id =  habit.id)),0) where habit.id = ?");
            preparedStatement.setLong(1, id);
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

    private boolean checkHabitExist(String name, Long userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "SELECT EXISTS(select 1 from habit where `name` = ? and user_id = ?);");

            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
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
}
