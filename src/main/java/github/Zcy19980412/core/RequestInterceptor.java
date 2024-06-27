package github.Zcy19980412.core;


import github.Zcy19980412.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private WebService webService;

    @Autowired
    private WebConfig webConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println("preHandle of Interceptor");

        String token = request.getHeader("authorization");

        try {
            if (webConfig.checkLoginPath(request)) {
                boolean checkToken = webService.checkToken(token);
                if (!checkToken) {
                    throw new RuntimeException("token校验错误");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("token校验错误:"+e.getMessage());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("post handle of Interceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        RequestThreadContext.removeCurrentUser();
        System.out.println("afterCompletion of Interceptor");
    }

}