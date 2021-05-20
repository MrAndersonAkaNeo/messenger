package com.ntu.messenger.security.user;

import com.ntu.messenger.config.JwtConfig;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
public class MessengerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final JwtConfig jwtConfig;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        Roles userRole = Roles.USER;
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole.name());

        return MessengerUserDetails
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .accessTokenExpiresAfter(Instant.now().plus(jwtConfig.getJwtTtl(), ChronoUnit.MINUTES))
                .isEnabled(user.isEnabled())
                .authorities(authorities).build();

    }
}
