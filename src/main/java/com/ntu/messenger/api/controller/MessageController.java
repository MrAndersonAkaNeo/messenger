package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.criteria.MessageCriteria;
import com.ntu.messenger.api.service.MessageService;
import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.converter.MessageMapper;
import com.ntu.messenger.data.dto.message.LastMessageDto;
import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.dto.message.MessageSendDto;
import com.ntu.messenger.data.dto.message.MessageUpdateDto;
import com.ntu.messenger.data.model.Message;
import com.ntu.messenger.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/messages")
public class MessageController extends SecurityController {

    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping(path = "chat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> getMessagesByChat(@PathVariable("id") Long chatId, @Valid MessageCriteria messageCriteria) {
        User requester = userService.findUserById(getUserDetails().getId());
        List<Message> messages = messageService.getMessagesByChatId(chatId, requester, messageCriteria);
        return MessageMapper.MAPPER.map(messages);
    }

    @GetMapping(path = "last")
    public List<LastMessageDto> getLastChatMessages() {
        User requester = userService.findUserById(getUserDetails().getId());
        return messageService.getChatsLastMessages(requester);
    }

    @GetMapping(path = "last/chat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto getLastMessage(@PathVariable("id") Long chatId) {
        User requester = userService.findUserById(getUserDetails().getId());
        Message message = messageService.getChatLastMessage(chatId, requester);
        return MessageMapper.MAPPER.map(message);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto sendMessage(@RequestBody @Valid MessageSendDto messageSendDto) {
        Message msg = messageService.saveMessage(messageSendDto);
        MessageDto dto = MessageMapper.MAPPER.map(msg);
        messagingTemplate.convertAndSend("/topic/messages/user/" + messageSendDto.getRecipientId(), dto);
        return dto;
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMessage(@PathVariable("id") Long messageId) {
        User requester = userService.findUserById(getUserDetails().getId());
        messageService.deleteMessage(messageId, requester);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifyMessage(@PathVariable("id") Long messageId, @RequestBody MessageUpdateDto updateDto) {
        User requester = userService.findUserById(getUserDetails().getId());
        messageService.modifyMessage(messageId, requester, updateDto);
    }
}
