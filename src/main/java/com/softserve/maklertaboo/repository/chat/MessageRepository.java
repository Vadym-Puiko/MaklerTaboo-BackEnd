package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByChatId(Long chatId, Pageable pageable);
    Long countByChatIdAndDataSeenIsNull(Long chatId);
    Long countByChatId(Long chatId);
    List<Message> findBySender_Id(Long id);
}
