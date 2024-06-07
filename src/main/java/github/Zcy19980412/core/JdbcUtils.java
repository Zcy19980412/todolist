package github.Zcy19980412.core;

import github.Zcy19980412.config.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * jdbc工具类
 * @author calvin
 */
@Component
public class JdbcUtils {

    @Autowired
    private SystemProperties systemProperties;


    public Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(systemProperties.getJdbcConnectionUrl(), systemProperties.getJdbcUsername(),
                        systemProperties.getJdbcPassword());
    }

    public PreparedStatement getPreparedStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }


}
