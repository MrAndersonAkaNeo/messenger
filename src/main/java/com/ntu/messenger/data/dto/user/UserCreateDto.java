package com.ntu.messenger.data.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateDto {

    @NotNull
    private String username;

    @NotNull
    private String firstname;

    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;



}
