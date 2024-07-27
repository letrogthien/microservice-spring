package gin.authservice.convert;

import gin.authservice.dto.UserDto;
import gin.authservice.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertUtils {
    private final ModelMapper modelMapper;

    public UserDto convertToUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
