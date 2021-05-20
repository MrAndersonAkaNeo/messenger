package com.ntu.messenger.data.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String lastname;
    private String firstname;
    private String phoneNumber;
}
