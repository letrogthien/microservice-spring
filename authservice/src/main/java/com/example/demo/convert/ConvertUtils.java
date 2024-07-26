package com.example.demo.convert;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertUtils {
    private final ModelMapper modelMapper;

    public User convertToUser(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }
    public UserDto convertToUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
