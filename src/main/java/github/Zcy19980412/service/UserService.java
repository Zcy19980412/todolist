package github.Zcy19980412.service;


import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;

import java.util.List;

/**
 * @author calvin
 */
public interface UserService {

    /**
     * 保存用户
     * @param userRequestDTO 用户请求类
     */
    void save(UserRequestDTO userRequestDTO);

    /**
     * 查询用户列表
     * @return 用户信息返回类
     */
    List<UserResponseDTO> list();

    /**
     * 根据id删除用户
     * @param id 用户id
     */
    void deleteById(Long id);

    /**
     * 校验用户名是否存在
     * @param username 用户名
     * @return 是否存在
     * @throws Exception
     */
    boolean checkUserNameExist(String username) throws Exception;

    /**
     * 登录成功返回token
     * @param userRequestDTO 用户请求类
     * @return token
     */
    String login(UserRequestDTO userRequestDTO);
}
