package github.Zcy19980412.web;


import github.Zcy19980412.domain.dto.UserRequestDTO;
import github.Zcy19980412.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/save")
    public void save(@RequestBody UserRequestDTO userRequestDTO) {
        userService.save(userRequestDTO);
    }





}
