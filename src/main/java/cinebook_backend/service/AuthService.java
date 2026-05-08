package cinebook_backend.service;

import cinebook_backend.dto.request.LoginRequest;
import cinebook_backend.dto.request.RegisterRequest;
import cinebook_backend.dto.response.ApiResponse;
import cinebook_backend.dto.response.AuthResponse;
import cinebook_backend.entity.User;
import cinebook_backend.exception.ResourceNotFoundException;
import cinebook_backend.repository.UserRepository;
import cinebook_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ApiResponse<AuthResponse> register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(), user.getRole().name());

        AuthResponse authResponse = new AuthResponse(
                token,
                user.getRole().name(),
                user.getName(),
                user.getEmail()
        );

        return ApiResponse.success("Registration successful", authResponse);
    }

    public ApiResponse<AuthResponse> login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(), user.getRole().name());

        AuthResponse authResponse = new AuthResponse(
                token,
                user.getRole().name(),
                user.getName(),
                user.getEmail()
        );

        return ApiResponse.success("Login successful", authResponse);
    }
}