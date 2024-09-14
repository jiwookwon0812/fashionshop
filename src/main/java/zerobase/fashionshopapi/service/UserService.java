package zerobase.fashionshopapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.fashionshopapi.domain.User;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.repository.UserRepository;
import zerobase.fashionshopapi.security.TokenProvider;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "user not found"));
    }

    // 회원가입
    public UserDto.SignUp register(UserDto.SignUp userDto) {
        User user;
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("This email already in use");
        }

        String encodingPassword = passwordEncoder.encode(userDto.getPassword());

        user = new User(userDto.getEmail(), userDto.getUsername(),
                userDto.getPhonenumber(), encodingPassword, userDto.getAuthority());
        userRepository.save(user);
        return userDto;
    }

    // 로그인
    public String login(UserDto.Login userDto) {
        // 1. 사용자 인증
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(userDto.getEmail() + "user not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        // 2. 이미 로그인된 사용자인지 확인
        String existingToken = (String) redisTemplate.opsForValue().get("JWT:" + user.getEmail());
        if (existingToken != null) {
            throw new RuntimeException("User is already logged in");
        }

        // 3. JWT 토큰 생성 및 Redis 저장
        String token = tokenProvider.generateToken(user.getEmail(), user.getAuthority());
        redisTemplate.opsForValue().set("JWT:" + user.getEmail(), token, 5, TimeUnit.HOURS);

        return token; // JWT 토큰 반환
    }

    // 로그아웃
    public void logout(String token) {
        String email = tokenProvider.getEmail(token);
        redisTemplate.delete("JWT:" + email);
    }

}
