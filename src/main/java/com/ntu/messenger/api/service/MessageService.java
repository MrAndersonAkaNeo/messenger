package com.ntu.messenger.api.service;

import com.ntu.messenger.api.criteria.MessageCriteria;
import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.dto.message.MessageSendDto;
import com.ntu.messenger.data.dto.message.MessageUpdateDto;
import com.ntu.messenger.data.model.Chat;
import com.ntu.messenger.data.model.Message;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Date lastUpdate = message.getUpdatedDate();
        Chat chat = message.getChat();
        chat.setUpdatedDate(lastUpdate);

        Hibernate.initialize(message.getRecipient());
        Hibernate.initialize(message.getSender());
        return message;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesByChatId(Long chatId, User requester, MessageCriteria messageCriteria) {
        verifyThatUserHasAccess(requester, chatId);
        Integer size = messageCriteria.getSize();
        Integer page = messageCriteria.getPage();
        return messageRepository.getMessagesByChatId(chatId, size, page * size);
    }

    @Transactional(readOnly = true)
    public Message getChatLastMessage(Long chatId, User requester) {
        verifyThatUserHasAccess(requester, chatId);
        return messageRepository.getLastMessageByChatId(chatId);
    }

    @Transactional(readOnly = true)
    public Map<String, MessageDto> getChatsLastMessages(User requester) {
        return null;
    }

    @Transactional
    public void deleteMessage(Long messageId, User requester) {
        Message msg = messageRepository.findById(messageId).orElseThrow(EntityNotFoundException::new);
        verifyUserCanModifyMessage(requester, msg);
        messageRepository.delete(msg);
    }

    @Transactional
    public void modifyMessage(Long messageId, User requester, MessageUpdateDto messageUpdateDto) {
        Message msg = messageRepository.findById(messageId).orElseThrow(EntityNotFoundException::new);
        verifyUserCanModifyMessage(requester, msg);
        msg.setText(messageUpdateDto.getText());
    }

    private void verifyThatUserHasAccess(User user, Long chatId) {
        Chat chat = chatService.getChatById(chatId);
        if (chat.getChatParticipants().contains(user)) {
            return;
        }
        throw new EntityNotFoundException("Access denied");
    }

    private void verifyUserCanModifyMessage(User user, Message message) {
        if (message.getSender().equals(user)) {
            return;
        }
        throw new EntityNotFoundException("Access denied");
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
