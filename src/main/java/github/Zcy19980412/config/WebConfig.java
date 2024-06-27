package github.Zcy19980412.config;

import github.Zcy19980412.core.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;


/**
 * webMvc配置
 * @author calvin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许的域
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // 允许前端访问Authorization头
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }

    /**
     * 路径需要校验一登陆
     * @param httpServletRequest
     * @return 如果需要校验则返回true
     */
    public boolean checkLoginPath(HttpServletRequest httpServletRequest) {
        return !(httpServletRequest.getServletPath().startsWith("/security")
                || httpServletRequest.getServletPath().startsWith("/user/save")
                || "OPTIONS".equals(httpServletRequest.getMethod())
        );
    }



}
