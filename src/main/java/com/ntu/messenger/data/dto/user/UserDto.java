package com.ntu.messenger.data.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String lastname;
    private String phoneNumber;
}
