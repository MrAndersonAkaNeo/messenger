package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.ChatService;
import com.ntu.messenger.data.dto.user.ChatParticipantsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createChatBetween(@Valid @RequestBody ChatParticipantsDto dto) {
       return chatService.createChatBetween(dto.getParticipantsIds()).getId();
    }

}
