package zerobase.fashionshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.fashionshopapi.config.TestSecurityConfig;
import zerobase.fashionshopapi.domain.constant.Authority;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.security.TokenProvider;
import zerobase.fashionshopapi.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class) // 테스트용 보안 구성 추가
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider; // TokenProvider mock 추가

    @Test
    @DisplayName("회원가입 테스트 - 유효한 입력")
    void testRegister_ValidInput() throws Exception {
        // Given: 테스트 데이터 준비
        UserDto.SignUp signUpRequest = new UserDto.SignUp(
                "test@example.com", "username", "1234567890", "Password1@", Authority.ROLE_CUSTOMER
        );

        // When: UserService의 register 메서드를 모킹하여 실행
        when(userService.register(signUpRequest)).thenReturn(signUpRequest);

        // Then: MockMvc를 이용하여 /signup 엔드포인트 호출 및 검증
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk()) // HTTP 상태 코드 200 확인
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    @DisplayName("회원가입 테스트 - 잘못된 입력")
    void testRegister_InValidEmail() throws Exception {
        // given : 이메일 형식 맞지 않는 입력
        UserDto.SignUp signUpRequest = new UserDto.SignUp(
                "test.com", "testname", "1234567890", "12345678!@a", Authority.ROLE_SELLER
        );

        // when & then : @valid 애노테이션을 통해 컨트롤러에서 먼저 유효성 검사 -> 400 응답 반환, 서비스까지 도달 x
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
    }
}
