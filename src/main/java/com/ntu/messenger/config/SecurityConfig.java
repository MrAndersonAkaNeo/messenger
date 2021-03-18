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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@PropertySource("classpath:security.properties")
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${secured.api.path}")
    private String SECURE_API_PATH;

    @Value("${public.signup.path}")
    private String SIGN_UP_PATH;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_PATH).permitAll()
                .antMatchers(SECURE_API_PATH).authenticated()

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

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public CorsFilter simpleCorsFilter() {
        return new CorsFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
