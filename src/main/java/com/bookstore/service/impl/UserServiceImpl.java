package com.bookstore.service.impl;

import com.bookstore.dto.UserDTO;
import com.bookstore.entity.User;
import com.bookstore.repository.UserFileRepository;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserFileRepository userFileRepository;

    @Autowired
    public UserServiceImpl(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    @Override
    public UserDTO findById(Long id) {
        return userFileRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<UserDTO> findAll() {
        return userFileRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userFileRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        User existingUser = userFileRepository.findById(id).orElse(null);
        if (existingUser != null) {
            updateExistingUser(existingUser, userDTO);
            userFileRepository.save(existingUser);
            return convertToDTO(existingUser);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        userFileRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getFirstName() + " " + user.getLastName()); // Припустимо, що username - це повне ім'я
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    private void updateExistingUser(User user, UserDTO dto) {
        user.setEmail(dto.getEmail());
    }
}
