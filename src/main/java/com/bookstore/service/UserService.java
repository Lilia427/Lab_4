package com.bookstore.service;

import com.bookstore.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO save(UserDTO userDTO);
    UserDTO update(Long id, UserDTO userDTO);
    void deleteById(Long id);
}
