package org.example.redisstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID"); //쿠키 이름 설정
        serializer.setCookiePath("/");          //쿠키가 적용될 경우 '/' 로 할 경우 모든 경로에 쿠키 전송
        serializer.setDomainNamePattern("^.+?(\\w+\\.[a-z]+)$");
        serializer.setUseBase64Encoding(true);
        return serializer;
    }
}
