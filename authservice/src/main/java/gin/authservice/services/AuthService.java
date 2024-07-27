package gin.authservice.services;

import gin.authservice.exception.UnAuthorizedException;
import gin.authservice.exception.UserAlreadyExist;
import gin.authservice.exception.UserNotFoundEx;
import gin.authservice.jwt.JwtService;
import gin.authservice.type.RoleType;
import gin.authservice.type.TokenType;
import gin.authservice.models.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );
        UserDetails userDetails = customUserDetailService.loadUserByUsername(authRequest.getUserName());
        User user = userService.getByUserName(
                authRequest.getUserName()
        ).orElseThrow(
                () -> new UserNotFoundEx("User Not Found")
        );
        var accessJwt = jwtService.generateToken(userDetails);
        var refreshJwt = jwtService.generateRefreshToken(userDetails);
        revokeJwtOfUser(user);
        saveToken(accessJwt, user);
        return AuthResponse.builder()
                .accessToken(accessJwt)
                .refreshToken(refreshJwt)
                .build();
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userService.userAlreadyExist(registerRequest.getUserName())) {
            throw new UserAlreadyExist();
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByName(RoleType.CUSTOMER)
                .orElseThrow(
                        RuntimeException::new
                ));
        var user = User.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .roles(roles)
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        var saveUser = userService.saveUser(user);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getUserName());
        var accessJwt = jwtService.generateToken(userDetails);
        var refreshJwt = jwtService.generateRefreshToken(userDetails);
        saveToken(accessJwt, saveUser);
        return AuthResponse.builder()
                .accessToken(accessJwt)
                .refreshToken(refreshJwt)
                .build();

    }

    public AuthResponse refreshToken(
            HttpServletRequest httpServletRequest
    ) {
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtRefreshToken;
        final String userName;
        final UserDetails userDetails;
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        jwtRefreshToken = header.substring(7);
        userName = jwtService.extractUserName(jwtRefreshToken);
        if (userName == null) {
            throw new UnAuthorizedException();
        }
        userDetails = customUserDetailService.loadUserByUsername(userName);
        if (!jwtService.isTokenValid(jwtRefreshToken, userDetails)) {
            throw new UnAuthorizedException();
        }
        var user = userService.getByUserName(userName)
                .orElseThrow(
                        () -> new UserNotFoundEx("User Not Found")
                );
        var accessJwt = jwtService.generateToken(userDetails);
        revokeJwtOfUser(user);
        saveToken(accessJwt, user);
        return AuthResponse.builder()
                .accessToken(accessJwt)
                .build();
    }

    private void revokeJwtOfUser(User user) {
        var jwtTokens = tokenService.getAllTokenOfUser(user);
        for (var jwtToken : jwtTokens
        ) {
            jwtToken.setRevoked(true);
            jwtToken.setExpired(true);
            tokenService.saveToken(jwtToken);
        }
    }

    private void saveToken(String token, User user) {
        tokenService.saveToken(
                Token.builder()
                        .jwt(token)
                        .user(user)
                        .expired(false)
                        .revoked(false)
                        .type(TokenType.BEARER)
                        .build()
        );
    }
}
