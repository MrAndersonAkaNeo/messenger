package com.ntu.messenger.data.dto.chat;

import com.ntu.messenger.data.dto.user.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class ChatDto {
    private Long chatId;
    private List<UserDto> chatUsers;
}
