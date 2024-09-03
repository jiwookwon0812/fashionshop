package zerobase.fashionshopapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.security.TokenProvider;
import zerobase.fashionshopapi.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final HttpServletRequest httpServletRequest; // 현재 요청 정보 (현재 로그인한 정보?)

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto.SignUp request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDto.Login request) {
        UserDto.LoginResponse user = userService.login(request);
        String token = tokenProvider.generateToken(user.getEmail(), user.getAuthority());
        log.info("user login -> " + user.getEmail());
        return ResponseEntity.ok(token);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        String token = getTokenFromRequest(httpServletRequest);
        userService.logout(token);
        return ResponseEntity.ok("User logged out successfully");
    }

    // 현재 요청 정보에서 jwt 토큰 추출
    private String getTokenFromRequest(HttpServletRequest request) {
        // Authorization 헤더에서 토큰 값 추출
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
            // "Bearer " 이후의 실체 토큰만 추출
        }
        return null;
    }
}
