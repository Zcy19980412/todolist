package github.Zcy19980412.config;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.HMacJWTSigner;
import cn.hutool.jwt.signers.JWTSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Key;


/**
 * webMvc配置
 * @author calvin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SystemProperties systemProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    public String generateJwtToken(String username,String password) {
        return "todo";
    }



}
