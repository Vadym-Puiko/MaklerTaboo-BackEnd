package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long chatId);
    Long countByChatId(Long chatId);
    List<Message> findBySender_Id(Long id);
}
