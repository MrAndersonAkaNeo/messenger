package com.ntu.messenger.security.jwt.service;

import com.ntu.messenger.security.jwt.dto.JwtResponseDto;
import com.ntu.messenger.security.user.MessengerUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    JwtResponseDto getToken(MessengerUserDetails user);

    UserDetails getUserDetails(String token);

}
