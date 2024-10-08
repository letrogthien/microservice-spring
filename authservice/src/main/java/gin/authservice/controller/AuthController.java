package gin.authservice.controller;

import gin.authservice.models.AuthRequest;
import gin.authservice.models.AuthResponse;
import gin.authservice.models.HandleResponse;
import gin.authservice.models.RegisterRequest;
import gin.authservice.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/authenticate")
  @ResponseStatus(HttpStatus.OK)
  public HandleResponse<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
    return new HandleResponse<>(
        authService.authenticate(authRequest), HttpStatus.OK.value(), new Date());
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public HandleResponse<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
    return new HandleResponse<>(
        authService.register(registerRequest), HttpStatus.OK.value(), new Date());
  }

  @PostMapping("/refresh")
  @ResponseStatus(HttpStatus.OK)
  public HandleResponse<AuthResponse> refresh(HttpServletRequest httpServletRequest) {
    return new HandleResponse<>(
        authService.refreshToken(httpServletRequest), HttpStatus.OK.value(), new Date());
  }
}
