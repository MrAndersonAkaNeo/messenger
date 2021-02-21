package com.ntu.messenger.security.jwt;

import com.ntu.messenger.security.user.MessengerUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

@Getter
@Setter
public class JwtTokenBasedAuthentication extends AbstractAuthenticationToken {

    private MessengerUserDetails principal;
    private String token;

    public JwtTokenBasedAuthentication(String token) {
        super(new HashSet<>());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserDetails getPrincipal() {
        return principal;
    }
}
