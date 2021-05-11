package com.ntu.messenger.data.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String text;
    private String recipientName;
    private String senderName;
    private Date sentAt;
    private Date changedAt;
}
