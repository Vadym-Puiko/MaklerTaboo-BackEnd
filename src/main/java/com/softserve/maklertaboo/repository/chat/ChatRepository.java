package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllBySender_Id(Long id);
    List<Chat> findAllByReceiver_Id(Long id);
}
