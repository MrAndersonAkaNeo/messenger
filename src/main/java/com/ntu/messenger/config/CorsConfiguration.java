package com.ntu.messenger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:security.properties")
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${cors.allowed.host}")
    private String host;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(host)
                .allowedHeaders("*")
                .maxAge(7200)
                .allowCredentials(true);
    }
}
