package github.Zcy19980412.web;


import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;
import github.Zcy19980412.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/save")
    public void save(@RequestBody UserRequestDTO userRequestDTO) {
        userService.save(userRequestDTO);
    }


    @GetMapping("/user/list")
    public List<UserResponseDTO> list() {
        return userService.list();
    }

    @DeleteMapping("/user/delete")
    public void delete(@RequestParam Long id){
        userService.deleteById(id);
    }



}
