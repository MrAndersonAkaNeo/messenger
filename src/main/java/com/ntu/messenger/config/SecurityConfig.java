package com.ntu.messenger.config;

import com.ntu.messenger.data.repository.UserRepository;
import com.ntu.messenger.security.filter.CorsFilter;
import com.ntu.messenger.security.filter.JwtAuthorizationFilter;
import com.ntu.messenger.security.filter.UsernamePasswordFilter;
import com.ntu.messenger.security.jwt.JwtAuthenticationProvider;
import com.ntu.messenger.security.jwt.service.DefaultJwtTokenService;
import com.ntu.messenger.security.jwt.service.JwtTokenService;
import com.ntu.messenger.security.user.MessengerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@PropertySources(value = {@PropertySource("classpath:security.properties"), @PropertySource("classpath:application.yaml")})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secured.api.path}")
    private String SECURE_API_PATH;

    @Value("${public.signup.path}")
    private String SIGN_UP_PATH;

    @Value("${springdoc.swagger-ui.path}")
    private String API_DOC_PATH;

    @Value("${cors.allowed.host}")
    private String CORS_ALLOWED_HOST;

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers(SIGN_UP_PATH, API_DOC_PATH).permitAll().antMatchers(SECURE_API_PATH).authenticated()

                .and()

                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

                .and()

                .addFilter(new UsernamePasswordFilter(authenticationManager(), jwtTokenService()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .addFilterBefore(simpleCorsFilter(), UsernamePasswordFilter.class)

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public JwtTokenService jwtTokenService() {
        return new DefaultJwtTokenService(jwtConfig);
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtTokenService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MessengerUserDetailsService(userRepository, jwtConfig);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.delux("You know nothing Jon Snow", "40932872adb21086743faf3db57a9eaf");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public CorsFilter simpleCorsFilter() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.setHost(CORS_ALLOWED_HOST);
        return corsFilter;
    }
}
