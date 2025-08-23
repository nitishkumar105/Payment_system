package co.Nitish.paymentSystem.controller;

import co.Nitish.paymentSystem.dto.LoginRequestDto;
import co.Nitish.paymentSystem.dto.RegisterRequestDto;
import co.Nitish.paymentSystem.model.User;
import co.Nitish.paymentSystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Map<String, String> response = userService.login(loginRequestDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of(
                    "error", "Login failed",
                    "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        try {
            User registeredUser = userService.register(registerRequestDto);

            Map<String, Object> response = Map.of(
                    "message", "User registered successfully",
                    "userId", registeredUser.getId(),
                    "username", registeredUser.getUsername(),
                    "email", registeredUser.getEmail()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = Map.of(
                    "error", "Registration failed",
                    "message", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Auth service is running");
    }
}