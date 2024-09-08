package zerobase.fashionshopapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.fashionshopapi.domain.constant.Authority;


public class UserDto {

    // 회원가입
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUp {
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Username is required")
        @Size(min = 1, max = 20)
        private String username;

        @NotBlank(message = "Phone number is required")
        private String phonenumber;

        @NotBlank(message = "Password is required")
        @Size(min = 8)
        @Pattern(
                regexp ="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "Password must contain at least one letter, one number, and one special character"
        )
        private String password;

        @NotNull(message = "Authority is required")
        private Authority authority;
    }

    // 로그인
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    // 로그인 응답용 dto (패스워드 리턴은 보안상 이유로 x)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String email;
        private String username;
        private Authority authority;
    }
}
