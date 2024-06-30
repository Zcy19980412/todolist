package github.Zcy19980412.web;



import github.Zcy19980412.core.BaseResponse;
import github.Zcy19980412.domain.dto.request.HabitRequestDTO;
import github.Zcy19980412.domain.dto.response.HabitResponseDTO;
import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author calvin
 */
@Controller
@ResponseBody
@RequestMapping("/habit")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping("/save")
    public BaseResponse<Void> save(@Validated @RequestBody HabitRequestDTO habitRequestDTO) {
        habitService.save(habitRequestDTO);
        return BaseResponse.success();
    }

    @GetMapping("/list")
    public BaseResponse<List<HabitResponseDTO>> list() {
        return BaseResponse.success(habitService.list());
    }

    @GetMapping("/delete")
    public BaseResponse<Void> delete(@RequestParam Long id) {
        habitService.delete(id);
        return BaseResponse.success();
    }

    @GetMapping("/check")
    public BaseResponse<Void> check(@RequestParam Long id) {
        habitService.check(id);
        return BaseResponse.success();
    }

}
