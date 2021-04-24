package com.ntu.messenger.api.service;

import com.ntu.messenger.data.model.Chat;
import com.ntu.messenger.data.model.User;
import com.ntu.messenger.data.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Transactional
    public Chat createChatBetween(List<Long> chatParticipantsIds) {
        Chat chat = new Chat();
        Set<User> participants = userService.findUsersByIdIn(chatParticipantsIds);
        if (participants.size() == 2) {
            validateThatChatNotExists(participants);
            chatRepository.save(chat);
            participants.forEach(p -> p.assignChat(chat));
            return chat;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Chat getChatByParticipants(Long participantOne, Long participantTwo) {
        Long chatId = chatRepository.getChatIdByParticipantsIds(participantOne, participantTwo);
        if (chatId != null) {
            return chatRepository.getOne(chatId);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Chat> getUserChats(User user) {
        return new ArrayList<>(user.getUserChats());
    }

    private void validateThatChatNotExists(Set<User> participants) {
        List<Long> ids = participants.stream().map(User::getId).collect(Collectors.toList());
        Long foundedChat = chatRepository.getChatIdByParticipantsIds(ids.get(0), ids.get(1));
        if (foundedChat != null) {
            throw new EntityExistsException("Such chat already exists!");
        }
    }

}
