package zerobase.fashionshopapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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
    private final RedisTemplate<String, Object> redisTemplate; // 로그인 하면 redis 에 jwt 토큰 저장

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto.SignUp request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDto.Login request) {
        // 이미 로그인된 사용자인지 확인
        String existingToken = (String) redisTemplate.opsForValue().get("JWT:" + request.getEmail());
        if (existingToken != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already logged in");
        }

        UserDto.LoginResponse user = userService.login(request);
        String token = tokenProvider.generateToken(user.getEmail(), user.getAuthority());
        log.info("user login -> " + user.getEmail());

        // Redis 에 JWT 토큰 저장
        redisTemplate.opsForValue().set("JWT:" + user.getEmail(), token);
        return ResponseEntity.ok(token);
    }

    // 로그아웃
    @PostMapping("/user_logout") ResponseEntity<?> logout(HttpServletRequest request) {
        // 현재 로그인한 클라이언트의 요청정보 (HttpServletRequest) 에서 jwt 토큰 추출
        // 유효성 검사는 필터에서 이미 하고 넘어옴
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거

        // Redis 에서 토큰 삭제
        String email = tokenProvider.getEmail(token);
        redisTemplate.delete("JWT:" + email);

        return ResponseEntity.ok("Logged out successfully");
    }
}
