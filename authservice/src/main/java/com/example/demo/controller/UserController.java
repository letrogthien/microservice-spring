package com.example.demo.controller;

import com.example.demo.convert.ConvertUtils;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserNotFoundEx;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//testing

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ConvertUtils convertUtils;

    @GetMapping("/all")
    public List<UserDto> getAll(){
        List<User> users = userService.getAllUser();
        return users.stream()
                    .map(convertUtils::convertToUserDto)
                    .toList();
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.getById(id).orElseThrow(() -> new UserNotFoundEx("user not found"));
        return convertUtils.convertToUserDto(user);
    }

}
