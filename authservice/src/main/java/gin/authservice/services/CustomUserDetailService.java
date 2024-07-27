package gin.authservice.services;

import gin.authservice.exception.UserNotFoundEx;
import gin.authservice.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username)
                .orElseThrow(()-> new UserNotFoundEx("User Not Found With "+ username));
        Set<GrantedAuthority> authorities= user.getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),authorities);
    }
}
