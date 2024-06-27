package github.Zcy19980412.service;


import github.Zcy19980412.domain.dto.request.HabitRequestDTO;
import github.Zcy19980412.domain.dto.response.HabitResponseDTO;

import java.util.List;

/**
 * @author calvin
 */
public interface HabitService {

    /**
     * 保存习惯
     * @param habitRequestDTO 习惯请求类
     */
    void save(HabitRequestDTO habitRequestDTO);

    /**
     * 查询习惯列表
     * @return
     */
    List<HabitResponseDTO> list();

    /**
     * 根据id删除习惯
     * @param id
     */
    void delete(Long id);
}
