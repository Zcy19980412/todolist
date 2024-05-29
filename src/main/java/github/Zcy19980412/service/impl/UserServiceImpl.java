package github.Zcy19980412.service.impl;


import com.mysql.cj.jdbc.JdbcConnection;
import github.Zcy19980412.domain.dto.UserRequestDTO;
import github.Zcy19980412.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public void save(UserRequestDTO userRequestDTO) {
        //创建jdbc连接
        try {
            /**
             *
             url – a database url of the form jdbc:subprotocol:subname
             user – the database user on whose behalf the connection is being made
             password – the user's password
             */
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/todolist", "root", "root");
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into user values(?,?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,null);
            preparedStatement.setString(3,null);
            preparedStatement.setString(4,userRequestDTO.getUsername());
            preparedStatement.setString(5,userRequestDTO.getRealName());
            preparedStatement.setString(6,userRequestDTO.getPassword());
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
