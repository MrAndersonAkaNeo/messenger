package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT c1.chat_id FROM user_chat c1 " +
                   "JOIN user_chat c2 ON c1.chat_id = c2.chat_id " +
                   "WHERE c1.user_id = :firstParticipant AND c2.user_id = :secondParticipant", nativeQuery = true)
    Long getChatIdByParticipantsIds(Long firstParticipant, Long secondParticipant);

}
