package zerobase.fashionshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.fashionshopapi.domain.User;
import zerobase.fashionshopapi.domain.constant.Authority;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // 모든 사용자 데이터 삭제
    }

    @Test
    @DisplayName("로그인 테스트 - 유효한 입력")
    void testLogin_ValidInput() throws Exception {
        // Given: 테스트 사용자 저장
        User user = new User("test@example.com", "username", "1234567890", passwordEncoder.encode("password"), Authority.ROLE_CUSTOMER);
        userRepository.save(user);

        UserDto.Login loginRequest = new UserDto.Login("test@example.com", "password");

        // When & Then: 로그인 요청 수행 및 검증
        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("mock-token"));
    }
}
