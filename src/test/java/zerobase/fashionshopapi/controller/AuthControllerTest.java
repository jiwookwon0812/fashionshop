package zerobase.fashionshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.fashionshopapi.dto.UserDto;
import zerobase.fashionshopapi.service.UserService;
import zerobase.fashionshopapi.domain.constant.Authority;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser_ValidInput() throws Exception {
        // 유효한 회원가입 요청 생성
        UserDto.SignUp validSignUpRequest = new UserDto.SignUp(
                "test@example.com",
                "username",
                "1234567890",
                "Password1@",
                Authority.ROLE_CUSTOMER
        );

        // 서비스의 register 메서드가 호출되면 유효한 결과 반환
        given(userService.register(validSignUpRequest)).willReturn(validSignUpRequest);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                .andExpect(status().isOk()); // 정상적인 요청은 200 OK
    }

    @Test
    void testRegisterUser_InvalidEmail() throws Exception {
        // 유효하지 않은 이메일로 회원가입 요청 생성
        UserDto.SignUp invalidSignUpRequest = new UserDto.SignUp(
                "invalid-email", // 유효하지 않은 이메일
                "username",
                "1234567890",
                "Password1@",
                Authority.ROLE_CUSTOMER
        );

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignUpRequest)))
                .andExpect(status().isBadRequest()); // 유효성 검사 실패 시 400 Bad Request
    }
}
