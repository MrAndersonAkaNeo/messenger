package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message m WHERE m.chat_id = :chatId " +
                   "ORDER BY m.created_date DESC " +
                   "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Message> getMessagesByChatId(@Param("chatId") Long chatId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query(value = "SELECT * FROM message m WHERE m.chat_id = :chatId " +
                   "ORDER BY m.created_date DESC LIMIT 1",
            nativeQuery = true)
    Message getLastMessageByChatId(@Param("chatId") Long chatId);

    @Query(value = "SELECT * FROM message m " +
                   "JOIN chat ch ON m.chat_id = ch.id " +
                   "WHERE m.created_date = ch.updated_date AND (m.sender_id = :userId OR m.recipient_id = :userId)", nativeQuery = true)
    List<Message> getEachUserChatLastMessage(@Param("userId") Long userId);

}
