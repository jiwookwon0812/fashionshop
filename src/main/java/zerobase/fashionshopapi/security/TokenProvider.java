package zerobase.fashionshopapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zerobase.fashionshopapi.domain.constant.Authority;
import zerobase.fashionshopapi.service.UserService;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 5;
    private final UserService userService;

    @Value("{jwt.secret}")
    private String secretKey;

    // jwt 의 세 부분 : header, payload, signature
    // header : 토큰의 타입 (jwt) 과 사용된 서명 알고리즘 (HMAC SHA256) 지정
    // payload : 토큰의 실제 데이터 포함 <- 여기에 claims 포함
    // signature : 토큰의 무결성 검증 위해 사용. 헤더와 페이로드를 합쳐서 비밀 키로 서명
    // 토큰 생성 메서드
    public String generateToken(String email, Authority authority) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(KEY_ROLES, authority);
    }

}
