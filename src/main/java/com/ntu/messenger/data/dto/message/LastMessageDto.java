package com.ntu.messenger.data.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LastMessageDto {
    private Long id;
    private String senderName;
    private String text;
    private Long chatId;
    private Date sentAt;
    private Date changedAt;
}
