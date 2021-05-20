package com.ntu.messenger.security.filter;

import com.ntu.messenger.security.jwt.dto.JwtResponseDto;
import com.ntu.messenger.security.jwt.service.JwtTokenService;
import com.ntu.messenger.security.user.MessengerUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenService jwtTokenService;

    public UsernamePasswordFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = super.obtainPassword(request);
        if (password == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required parameter 'password' is not present");
        }
        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String login = super.obtainUsername(request);
        if (login == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Required parameter 'username' is not present");
        }
        return login;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        MessengerUserDetails userDetails = (MessengerUserDetails) authResult.getPrincipal();
        JwtResponseDto responseDto = jwtTokenService.getToken(userDetails);

        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(responseDto);
        response.getWriter().close();
    }
}
