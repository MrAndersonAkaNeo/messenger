package com.ntu.messenger.data.dto.message;

import lombok.Data;

@Data
public class MessageSendDto {

    private String text;
    private Long senderId;
    private Long recipientId;

}
