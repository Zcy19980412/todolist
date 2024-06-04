package github.Zcy19980412.web;

import github.Zcy19980412.config.BaseResponse;
import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/security")
@ResponseBody
public class SecurityController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody UserRequestDTO userRequestDTO) {
        return BaseResponse.success(userService.login(userRequestDTO));
    }


}
