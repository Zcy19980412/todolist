package github.Zcy19980412.util;

import github.Zcy19980412.Constant.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtils {


    public static Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(Constant.jdbcConnectionUrl, Constant.jdbcConnectUsername,
                        Constant.jdbcConnectionPassword);
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }


}
