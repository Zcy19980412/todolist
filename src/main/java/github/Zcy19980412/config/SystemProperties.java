package github.Zcy19980412.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取项目配置文件
 * @author calvin
 * @date 2024/6/1 0001
 */
@Data
@Component
public class SystemProperties {


    @Value("${jdbc.connectionUrl}")
    public String jdbcConnectionUrl;

    @Value("${jdbc.username}")
    public String jdbcUsername;

    @Value("${jdbc.password}")
    public String jdbcPassword;

    @Value("${jwt.salt}")
    public String jwtSalt;


}
