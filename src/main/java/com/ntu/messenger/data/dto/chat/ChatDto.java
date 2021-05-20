package com.ntu.messenger.data.dto.chat;

import com.ntu.messenger.data.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ChatDto {
    private Long chatId;
    private List<UserDto> chatUsers;
    private Date modifyDate;
}
