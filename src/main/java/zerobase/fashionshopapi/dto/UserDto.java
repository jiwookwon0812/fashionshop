package zerobase.fashionshopapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.fashionshopapi.domain.constant.Authority;

import java.util.List;

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
}
