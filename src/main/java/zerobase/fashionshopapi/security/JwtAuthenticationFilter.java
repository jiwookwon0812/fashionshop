package zerobase.fashionshopapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 jwt 토큰 추출
        String jwt = getJwtFromRequest(request);
        log.info("Token from request: {}", jwt);

        // 2. 토큰 유효성 검증
        if (jwt != null && tokenProvider.validateToken(jwt)) {
            log.info("Token successfully validated");

            // 3. Redis 에서 토큰 검증
            String email = tokenProvider.getEmail(jwt);
            String token = (String) redisTemplate.opsForValue().get("JWT:" + email);
            if (token == null || !token.equals(jwt)) {
                log.warn("Token not found or does not match in Redis.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return; // 필터 체인 진행 중단
            }

            // 3. 객체 생성
            Authentication auth = tokenProvider.getAuthentication(jwt);
            log.info("Authentication: {}", auth);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            log.warn("Invalid token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token");

            return; // 필터 체인 중단
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // /signup, /login 경로에서는 필터가 적용되지 않도록 설정
        return path.equals("/signup") || path.equals("/login");
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        log.info("Bearer token: {}", bearerToken);
        // 현재 들어온 http 요청에서 토큰의 헤더값 추출

        if (bearerToken != null && bearerToken.startsWith(TOKEN_BEARER_PREFIX)) {
            return bearerToken.substring(TOKEN_BEARER_PREFIX.length());
        }
        // 토큰이 비어있지 않은지 + bearer 로 시작하는지 확인
        // => 조건 만족 시 Bearer 접두사 제거한 실제 토큰 문자열 반환
        return null;
    }
}
