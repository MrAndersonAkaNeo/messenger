package com.ntu.messenger.api.controller;

import com.ntu.messenger.security.user.MessengerUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityController {

    protected MessengerUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (MessengerUserDetails) auth.getPrincipal();
    }
}
