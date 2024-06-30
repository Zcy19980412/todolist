package github.Zcy19980412.service;


import java.util.Date;

/**
 * @author calvin
 */
public interface HabitRecordService {

    /**
     * 从date的下一日开始，没生成一次习惯任务，需要间隔设定个日，
     *
     * @param date    开始时间
     * @param gapDays 间隔日
     * @return 今日是否生成习惯任务
     */
    boolean checkGapDay(Date date, int gapDays);

    /**
     * 生成今日的习惯待完成任务
     */
    void generateHabitRecord();

    /**
     * 清算所有习惯完成度
     */
    void liquidateDoneRate();

    /**
     * 此习惯最后一条习惯记录是待check
     * @param id habitId
     * @return
     */
    boolean waitCheck(Long id);
}
