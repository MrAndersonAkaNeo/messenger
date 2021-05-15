package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message m WHERE m.chat_id = :chatId " +
                   "ORDER BY m.created_date DESC " +
                   "LIMIT :offset, :limit", nativeQuery = true)
    List<Message> getMessagesByChatId(@Param("chatId") Long chatId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query(value = "SELECT * FROM message m WHERE m.chat_id = :chatId " +
                   "ORDER BY m.created_date DESC LIMIT 1",
            nativeQuery = true)
    Message getLastMessageByChatId(@Param("chatId") Long chatId);

    @Query(value = "SELECT m1.* FROM message m1 " +
                   "JOIN " +
                   "(" +
                   "SELECT MAX(created_date) AS lastDate, chat_id " +
                   "FROM message " +
                   "GROUP BY chat_id" +
                   ") AS m2 " +
                   "ON m1.chat_id = m2.chat_id AND m1.created_date = m2.lastDate " +
                   "WHERE m1.sender_id = :userId OR m1.recipient_id = :userId", nativeQuery = true)
    List<Message> getEachUserChatLastMessage(@Param("userId") Long userId);

}
