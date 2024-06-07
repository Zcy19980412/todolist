package github.Zcy19980412.web;


import github.Zcy19980412.core.BaseResponse;
import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;
import github.Zcy19980412.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author calvin
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public BaseResponse<Void> save(@RequestBody UserRequestDTO userRequestDTO) {
        userService.save(userRequestDTO);
        return BaseResponse.success();
    }


    @GetMapping("/user/list")
    public BaseResponse<List<UserResponseDTO>> list() {
        return BaseResponse.success(userService.list());
    }

    @DeleteMapping("/user/delete")
    public BaseResponse<Void> delete(@RequestParam Long id){
        userService.deleteById(id);
        return BaseResponse.success();
    }



}
