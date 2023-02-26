package net.sql.springbootrestfulwebservices.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sql.springbootrestfulwebservices.dto.UserDto;
import net.sql.springbootrestfulwebservices.entity.User;
import net.sql.springbootrestfulwebservices.exception.ErrorDetails;
import net.sql.springbootrestfulwebservices.exception.ResourceNotFoundException;
import net.sql.springbootrestfulwebservices.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    // build create user rest api
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<UserDto>(savedUser, HttpStatus.CREATED);
    }

    //api/users/1
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    //RequestBody annotation converts json to java object
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto user) {
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<String>("User successfully deleted", HttpStatus.OK);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}
