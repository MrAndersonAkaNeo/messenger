package com.ntu.messenger.security.filter;

import com.ntu.messenger.security.jwt.JwtTokenBasedAuthentication;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Exception failed) {
        if (failed instanceof ExpiredJwtException) {
            response.addHeader("JWT-Expired", "true");
            Throwable cause = failed.getCause();
            if (cause instanceof ExpiredJwtException) {
                response.addHeader("JWT-Expired-Details", cause.getMessage());
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        try {
            Authentication authentication = authenticate(header);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (AuthenticationException failed) {
            log.warn("Unsuccessful authentication attempt");
            unsuccessfulAuthentication(req, res, failed);
        }
    }

    private Authentication authenticate(String authorizationHeader) {
        String token = getBearerToken(authorizationHeader);
        return getAuthenticationManager().authenticate(new JwtTokenBasedAuthentication(token));
    }

    private String getBearerToken(String headerValue) {
        String[] authTypeValue = headerValue.split(" ");

        if (authTypeValue.length != 2) {
            return null;
        }

        if (!"Bearer".equalsIgnoreCase(authTypeValue[0])) {
            return null;
        }

        return authTypeValue[1];
    }

}
