package com.ntu.messenger.security.jwt;

import com.ntu.messenger.security.jwt.service.JwtTokenService;
import com.ntu.messenger.security.user.MessengerUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationProvider(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        JwtTokenBasedAuthentication tokenAuthentication = (JwtTokenBasedAuthentication) authentication;
        MessengerUserDetails ud = (MessengerUserDetails) getUserDetails(tokenAuthentication);
        userDetailsChecker.check(ud);
        tokenAuthentication.setPrincipal(ud);
        tokenAuthentication.setAuthenticated(true);

        return tokenAuthentication;
    }

    private UserDetails getUserDetails(JwtTokenBasedAuthentication tokenAuthentication) {
        return jwtTokenService.getUserDetails(tokenAuthentication.getToken());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtTokenBasedAuthentication.class.isAssignableFrom(aClass);
    }
}
