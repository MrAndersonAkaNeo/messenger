package com.ntu.messenger.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource("classpath:security.properties")
public class JwtConfig {

    @Value("${jwt.access.ttl.minutes}")
    private Long jwtTtl;

    @Value("${jwt.access.secret}")
    private String jwtSecret;

}
