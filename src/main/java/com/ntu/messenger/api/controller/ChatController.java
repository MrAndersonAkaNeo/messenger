package com.ntu.messenger.api.controller;

import com.ntu.messenger.data.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {



    @MessageMapping("/chat")
    public void processMessageSend(@Payload Message message) {

    }



}
