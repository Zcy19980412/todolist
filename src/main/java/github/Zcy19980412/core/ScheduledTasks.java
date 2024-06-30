package github.Zcy19980412.core;

import github.Zcy19980412.service.HabitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author calvin
 * @date 2024/6/29 0029
 */
@Component
public class ScheduledTasks {

    @Autowired
    private HabitRecordService habitRecordService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void liquidateDoneRateAndGenerateHabitRecord(){
        habitRecordService.generateHabitRecord();
        habitRecordService.liquidateDoneRate();
    }



}
