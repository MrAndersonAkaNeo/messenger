package com.ntu.messenger.data.converter;

import com.ntu.messenger.data.dto.message.LastMessageDto;
import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.model.Message;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MessageMapper {

    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "recipientName", ignore = true)
    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "changedAt", ignore = true)
    @Mapping(target = "chatId", ignore = true)
    MessageDto map(Message message);

    @AfterMapping
    default void fixDtoConvert(@MappingTarget MessageDto dto, Message message) {
        dto.setSenderName(message.getSender().getUsername());
        dto.setRecipientName(message.getRecipient().getUsername());
        dto.setSentAt(message.getCreatedDate());
        dto.setChangedAt(message.getUpdatedDate());
        dto.setChatId(message.getChat().getId());
    }

    List<MessageDto> map(List<Message> messages);

    @Mapping(target = "changedAt", source = "message.updatedDate")
    @Mapping(target = "sentAt", source = "message.createdDate")
    @Mapping(target = "senderName", source = "message.sender.username")
    @Mapping(target = "chatId", source = "message.chat.id")
    LastMessageDto toLastMessageDto(Message message);

}
