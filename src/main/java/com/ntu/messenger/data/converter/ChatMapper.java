package com.ntu.messenger.data.converter;

import com.ntu.messenger.data.dto.chat.ChatDto;
import com.ntu.messenger.data.model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = UserMapper.class)
public interface ChatMapper {

    ChatMapper MAPPER =  Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "chatUsers", source = "chatParticipants")
    @Mapping(target = "chatId", source = "id")
    ChatDto map(Chat chat);

    List<ChatDto> map(List<Chat> chats);
}
