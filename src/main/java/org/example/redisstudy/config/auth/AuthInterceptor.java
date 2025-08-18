package org.example.redisstudy.config.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String REDIS_SESSION_KEY = ":sessions:";

    @Value("${spring.session.redis.namespace}")
    private String redisNamespace;

    @Value("${server.servlet.session.cookie.name}")
    private String sessionName;

    private StringRedisTemplate redisTemplate;

    // Controller 실행 전 실행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String raw = getCookieValue(request, sessionName);

        if(raw == null || raw.isBlank()) {
            deny(response, "세선 정보가 존재하지 않습니다.");
            return false;
        }

        String sessionId = base64DecodeOrFallback(raw);
        String key = redisNamespace + REDIS_SESSION_KEY + sessionId; //spring:session:sessions:{sessionId}
        Boolean isUserSessionIdExists = redisTemplate.hasKey(key);
        if(isUserSessionIdExists.equals(Boolean.FALSE)) {
            log.warn("세션 ID 가 유효하지 않습니다. key = {}", key);
            deny(response, "유효하지 않은 세션");
            return false;
        }

        return true;
    }

    // 요청 보낼 때 쿠키 안에 해당 쿠키 이름이 있으면 쿠키 반환하는 메서드
    private static String getCookieValue(HttpServletRequest request, String name) {
        if(request.getCookies() == null)
            return null;
        for(Cookie c : request.getCookies()) {
            if(name.equals(c.getName())){
                return c.getValue();
            }
        }
        return null;
    }

    // 세션 ID 를 decode 하기 위한 메서드
    private static String base64DecodeOrFallback(String value) {
        try {
            return new String(Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } catch(IllegalArgumentException e) {
            return value;
        }
    }

    private static void deny(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }
}