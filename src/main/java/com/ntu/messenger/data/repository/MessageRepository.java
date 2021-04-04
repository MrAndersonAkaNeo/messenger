package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
