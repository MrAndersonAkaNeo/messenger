package com.ntu.messenger.data.dto.message;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {

    private String text;
    private String recipientName;
    private String senderName;
    private Date sentAt;

}
