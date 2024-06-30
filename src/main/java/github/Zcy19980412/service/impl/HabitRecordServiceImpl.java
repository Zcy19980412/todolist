package github.Zcy19980412.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import github.Zcy19980412.core.JdbcUtils;
import github.Zcy19980412.domain.dto.response.HabitResponseDTO;
import github.Zcy19980412.service.HabitRecordService;
import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author calvin
 */
@Service
public class HabitRecordServiceImpl implements HabitRecordService {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Autowired
    private HabitService habitService;

    @Override
    public void generateHabitRecord() {
        ArrayList<HabitResponseDTO> habitResponseDTOS = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "SELECT * from habit");
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

        if (CollectionUtil.isEmpty(habitResponseDTOS)) {
            return;
        }

        for (HabitResponseDTO habitResponseDTO : habitResponseDTOS) {
            Integer gapDays = habitResponseDTO.getGapDays();
            if (gapDays == 0 || checkGapDay(habitResponseDTO.getCreateTime(), gapDays)) {
                try {
                    connection = jdbcUtils.getConnection();
                    preparedStatement = jdbcUtils.getPreparedStatement(
                            connection, "insert into habit_record value(?,?,?,?,?,?)");
                    preparedStatement.setNull(1, Types.BIGINT);
                    preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.setLong(4, habitResponseDTO.getId());
                    preparedStatement.setBoolean(5, false);
                    preparedStatement.setNull(6, Types.VARCHAR);
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
        }
    }

    @Override
    public void liquidateDoneRate() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "UPDATE habit set done_rate = IFNULL(" +
                            "(SELECT (SELECT count(*) FROM `habit_record` where habit_id = habit.id and checked = 1)" +
                            "/(SELECT count(*) FROM `habit_record` where habit_id =  habit.id)),0)");
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
    public boolean waitCheck(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcUtils.getConnection();
            preparedStatement = jdbcUtils.getPreparedStatement(
                    connection, "select checked from habit_record where habit_id = ? order by id desc limit 1");
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean checked = resultSet.getBoolean(1);
            return !checked;
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
    public boolean checkGapDay(Date date, int gapDays) {
        if (gapDays == 0) {
            return true;
        }
        long betweenDay = DateUtil.betweenDay(date, new Date(), true);
        return (betweenDay - 1) % (gapDays + 1) == 0;
    }

}
