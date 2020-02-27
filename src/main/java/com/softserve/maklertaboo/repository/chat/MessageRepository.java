package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long chatId);
    Optional<Message> findMessageById (Long id);
    Long countByChatId(Long chatId);
}
