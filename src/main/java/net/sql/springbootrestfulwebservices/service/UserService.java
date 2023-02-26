package net.sql.springbootrestfulwebservices.service;

import net.sql.springbootrestfulwebservices.dto.UserDto;
import net.sql.springbootrestfulwebservices.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);
}
