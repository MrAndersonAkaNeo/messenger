package com.ntu.messenger.api.service;

import com.ntu.messenger.data.dto.user.UserDto;
import com.ntu.messenger.data.model.Chat;
import com.ntu.messenger.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Transactional
    public Long createChatBetween(Set<UserDto> chatParticipants) {
        Chat chat = new Chat();

        List<String> usernames = chatParticipants.stream()
                .map(UserDto::getUsername)
                .collect(Collectors.toList());

        chat.setChatParticipants(userService.findUsersByUsernames(usernames));

        chatRepository.save(chat);
        return chat.getId();
    }




}
