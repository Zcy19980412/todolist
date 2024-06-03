package github.Zcy19980412.service;


import github.Zcy19980412.domain.dto.request.HabitRequestDTO;

/**
 * @author calvin
 */
public interface HabitService {

    /**
     * 保存习惯
     * @param habitRequestDTO 习惯请求类
     */
    void save(HabitRequestDTO habitRequestDTO);

}
