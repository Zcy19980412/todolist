package github.Zcy19980412.service;


import github.Zcy19980412.domain.dto.request.UserRequestDTO;
import github.Zcy19980412.domain.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    //CRUD
    void save(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> list();
}
