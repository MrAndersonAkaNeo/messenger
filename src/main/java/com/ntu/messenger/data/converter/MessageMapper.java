package com.ntu.messenger.data.converter;

import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.model.Message;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "recipientName", ignore = true)
    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    MessageDto map(Message message);

    @AfterMapping
    default void fixDtoConvert(@MappingTarget MessageDto dto, Message message) {
        dto.setSenderName(message.getSender().getUsername());
        dto.setRecipientName(message.getRecipient().getUsername());
        dto.setSentAt(message.getCreatedDate());
    }
}
