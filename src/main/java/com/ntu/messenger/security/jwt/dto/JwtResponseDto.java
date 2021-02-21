package com.ntu.messenger.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {

    private String access_token;

    private Long expires_in;

    @Override
    public String toString() {
        return  "{\n" +
                "\t\"access_token\": \"" + access_token + "\",\n" +
                "\t\"expires_in\": " + expires_in + "\n" +
                "}";
    }
}
