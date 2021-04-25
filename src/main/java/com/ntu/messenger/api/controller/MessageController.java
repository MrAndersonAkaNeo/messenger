package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.criteria.MessageCriteria;
import com.ntu.messenger.api.service.MessageService;
import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.converter.MessageMapper;
import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.model.Message;
import com.ntu.messenger.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/messages")
public class MessageController extends SecurityController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping(path = "/chat/{id}")
    public List<MessageDto> getMessagesByChat(@PathVariable("id") Long chatId, @Valid MessageCriteria messageCriteria) {
        User requester = userService.findUserById(getUserDetails().getId());
        List<Message> messages = messageService.getMessagesByChatId(chatId, requester, messageCriteria);
        return MessageMapper.MAPPER.map(messages);
    }

    @GetMapping(path = "/last/chat/{id}")
    public MessageDto getLastMessage(@PathVariable("id") Long chatId) {
        User requester = userService.findUserById(getUserDetails().getId());
        Message message = messageService.getChatLastMessage(chatId, requester);
        return MessageMapper.MAPPER.map(message);
    }

    /*@DeleteMapping(path = "/{id}/chat/{chatId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMessage(@PathVariable("id") Long messageId, @PathVariable("chatId") Long chatId) {
        User requester = userService.findUserById(getUserDetails().getId());
        messageService.deleteMessage(messageId, chatId, requester);
    }*/
}
