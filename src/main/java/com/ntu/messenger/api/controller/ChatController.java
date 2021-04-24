package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.ChatService;
import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.converter.ChatMapper;
import com.ntu.messenger.data.dto.chat.ChatDto;
import com.ntu.messenger.data.model.Chat;
import com.ntu.messenger.data.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/chat")
@RequiredArgsConstructor
public class ChatController extends SecurityController {

    private final ChatService chatService;
    private final UserService userService;

    @GetMapping
    public List<ChatDto> getUserChats() {
        User current = userService.findUserById(getUserDetails().getId());
        List<Chat> userChats = chatService.getUserChats(current);
        return ChatMapper.MAPPER.map(userChats);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUserChat(@PathVariable("id") Long chatId) {
        User requester = userService.findUserById(getUserDetails().getId());
        chatService.removeChat(requester, chatId);
    }
}
