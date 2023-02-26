package net.sql.springbootrestfulwebservices.mapper;

import net.sql.springbootrestfulwebservices.dto.UserDto;
import net.sql.springbootrestfulwebservices.entity.User;

public class UserMapper {

    // Convert user jpa entity into userDto
    public static UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        return userDto;
    }

    // Convert userDto into user jpa entity
    public static User mapToUser(UserDto userDto){
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );

        return user;
    }
}
