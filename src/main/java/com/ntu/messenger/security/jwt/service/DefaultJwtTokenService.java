package com.ntu.messenger.security.jwt.service;

import com.ntu.messenger.config.JwtConfig;
import com.ntu.messenger.security.jwt.dto.JwtResponseDto;
import com.ntu.messenger.security.user.MessengerUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DefaultJwtTokenService implements JwtTokenService {

    private final JwtConfig jwtConfig;

    public DefaultJwtTokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public JwtResponseDto getToken(MessengerUserDetails user) {
        return createAccessToken(user);
    }

    @Override
    public UserDetails getUserDetails(String token) {
       return parseToken(token);
    }

    private UserDetails parseToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(jwtConfig.getJwtSecret().getBytes())
                            .parseClaimsJws(token).getBody();

        List<String> authorities = (List)claims.get("authorities");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));

        Long userId = claims.get("userId", Long.class);
        String username = claims.getSubject();

        return new MessengerUserDetails(userId, username, null, true, claims.getExpiration().toInstant(), grantedAuthorities);
    }

    private JwtResponseDto createAccessToken(MessengerUserDetails userDetails) {
        Long accessExpiresIn = Duration.between(Instant.now(), userDetails.getAccessTokenExpiresAfter()).getSeconds();
        String accessToken = buildAccessToken(userDetails);
        return new JwtResponseDto(accessToken, accessExpiresIn);
    }

    private String buildAccessToken(MessengerUserDetails userDetails) {
        String tokenId = UUID.randomUUID().toString();
        Date expirationDate = userDetails.getAccessTokenExpiresAfter() == null ? null : Date.from(userDetails.getAccessTokenExpiresAfter());

        return Jwts.builder()
                   .setId(tokenId)
                   .setIssuer("messenger-api")
                   .setSubject(userDetails.getUsername())
                   .claim("authorities", AuthorityUtils.authorityListToSet(userDetails.getAuthorities()))
                   .claim("userId", userDetails.getId())
                   .setIssuedAt(new Date()).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret().getBytes()).compact();
    }

}
