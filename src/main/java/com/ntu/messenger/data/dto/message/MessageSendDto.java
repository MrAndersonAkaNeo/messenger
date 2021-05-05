package com.ntu.messenger.data.dto.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageSendDto {
    @NotBlank
    private String text;

    @NotNull
    private Long senderId;

    @NotNull
    private Long recipientId;
}
