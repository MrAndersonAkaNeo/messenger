package com.ntu.messenger.api.service;

import com.ntu.messenger.data.dto.message.MessageSendDto;
import com.ntu.messenger.data.model.Chat;
import com.ntu.messenger.data.model.Message;
import com.ntu.messenger.data.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageRepository messageRepository;


    @Transactional
    public Message saveMessage(MessageSendDto messageSendDto) {
        Message message = new Message();
        message.setText(messageSendDto.getText());
        message.setRecipient(userService.findUserById(messageSendDto.getRecipientId()));
        message.setSender(userService.findUserById(messageSendDto.getSenderId()));

        createChatIfNotExists(messageSendDto, message);

        messageRepository.save(message);
        Hibernate.initialize(message.getRecipient());
        Hibernate.initialize(message.getSender());
        return message;
    }

    private void createChatIfNotExists(MessageSendDto messageSendDto, Message message) {
        Chat chat = chatService.getChatByParticipants(messageSendDto.getSenderId(), messageSendDto.getRecipientId());
        if (chat != null) {
            message.setChat(chat);
        } else {
            Chat newChat = chatService.createChatBetween(Arrays.asList(messageSendDto.getRecipientId(), messageSendDto.getSenderId()));
            message.setChat(newChat);
        }
    }

}
