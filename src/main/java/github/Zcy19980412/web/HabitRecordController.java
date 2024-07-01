package github.Zcy19980412.web;


import github.Zcy19980412.core.BaseResponse;
import github.Zcy19980412.core.ScheduledTasks;
import github.Zcy19980412.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author calvin
 */
@RestController
public class HabitRecordController {

    @Autowired
    private HabitService habitService;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @GetMapping("/trigger")
    public BaseResponse<Void> trigger(){
        scheduledTasks.liquidateDoneRateAndGenerateHabitRecord();
        return BaseResponse.success();
    }



}
