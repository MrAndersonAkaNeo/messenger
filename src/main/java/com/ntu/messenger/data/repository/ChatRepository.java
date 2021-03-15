package com.ntu.messenger.data.repository;

import com.ntu.messenger.data.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {



}
