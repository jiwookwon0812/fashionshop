package zerobase.fashionshopapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.fashionshopapi.domain.User;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
