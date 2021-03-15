package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.MessageService;
import com.ntu.messenger.data.dto.message.MessageSendDto;
import com.ntu.messenger.data.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessageSend(@Payload MessageSendDto message) {
        Message msg = messageService.saveMessage(message);
        messagingTemplate.convertAndSend(String.format("/user/%d/messages", message.getRecipientId()), msg);
    }

}
