package github.Zcy19980412.config;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@ResponseBody
public class ExceptionConfig {

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> controllerExceptionHandler(Exception e){
        return BaseResponse.fail(e.getMessage());
    }


}
