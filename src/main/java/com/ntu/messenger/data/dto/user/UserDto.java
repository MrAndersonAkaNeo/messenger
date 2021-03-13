package com.ntu.messenger.data.dto.user;

import lombok.Data;

@Data
public class UserDto {

    private String username;

    private String email;

    private String lastname;

    private String password;

    private Boolean isEnabled;

    private Boolean isAdmin;

}
