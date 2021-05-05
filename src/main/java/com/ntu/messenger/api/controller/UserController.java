package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.converter.UserMapper;
import com.ntu.messenger.data.dto.user.UserCreateDto;
import com.ntu.messenger.data.dto.user.UserDto;
import com.ntu.messenger.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController extends SecurityController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getCurrentUser() {
        User current = userService.findUserById(getUserDetails().getId());
        UserDto dto = UserMapper.MAPPER.map(current);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserCreateDto updateDto) {
        User current = userService.findUserById(getUserDetails().getId());
        UserDto dto = UserMapper.MAPPER.map(userService.updateUser(current, updateDto));
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
