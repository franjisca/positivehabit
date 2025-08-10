package com.side.positivehabit.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT 서명에 사용할 비밀 키
     */
    private String secret;

    /**
     * Access Token 유효기간 (밀리초 단위)
     */
    private long accessTokenValidity;

    /**
     * Refresh Token 유효기간 (밀리초 단위)
     */
    private long refreshTokenValidity;
}