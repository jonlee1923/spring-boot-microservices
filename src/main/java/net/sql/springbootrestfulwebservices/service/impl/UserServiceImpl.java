package net.sql.springbootrestfulwebservices.service.impl;

import lombok.AllArgsConstructor;
import net.sql.springbootrestfulwebservices.dto.UserDto;
import net.sql.springbootrestfulwebservices.entity.User;
import net.sql.springbootrestfulwebservices.exception.EmailAlreadyExistsException;
import net.sql.springbootrestfulwebservices.exception.ResourceNotFoundException;
import net.sql.springbootrestfulwebservices.mapper.UserMapper;
import net.sql.springbootrestfulwebservices.repository.UserRepository;
import net.sql.springbootrestfulwebservices.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor //to automatically create constructor for this user
public class UserServiceImpl implements UserService {
    //We have a userServiceimpl as a spring bean and the spring bean only has one parameterized constructor
    //Thus dont need autotwired annotation
    private UserRepository userRepository;

//    Inject springbean
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
//        convert userDto into user JPA entity
//        User user = UserMapper.mapToUser(userDto);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email already exists for User");
        }

        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

//        convert user JPA entity to userDto, otherwise itll contain private key
//        UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", userId)
        );

//        User user = optionalUser.get();
//        return UserMapper.mapToUserDto(user);
        return modelMapper.map(user, UserDto.class);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
        return users.stream().map((user)-> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }


    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("Users", "Id", user.getId())
        );

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
//        return UserMapper.mapToUserDto(updatedUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }


    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", userId)
        );
        userRepository.deleteById(userId);
    }
}
