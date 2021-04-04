package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.MessageService;
import com.ntu.messenger.data.converter.MessageMapper;
import com.ntu.messenger.data.dto.message.MessageDto;
import com.ntu.messenger.data.dto.message.MessageSendDto;
import com.ntu.messenger.data.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessageSend(@Payload MessageSendDto message) {
        Message msg = messageService.saveMessage(message);
        MessageDto dto = MessageMapper.MAPPER.toDto(msg);
        messagingTemplate.convertAndSend( "/topic/messages/user/" + message.getRecipientId(), dto);
    }

    @MessageMapping("/test")
    public void test(@Payload MessageSendDto message) {
        messagingTemplate.convertAndSend("/topic/test", "test");
    }
}
