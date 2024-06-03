package github.Zcy19980412.web;



import github.Zcy19980412.config.BaseResponse;
import github.Zcy19980412.domain.dto.request.HabitRequestDTO;
import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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



}
