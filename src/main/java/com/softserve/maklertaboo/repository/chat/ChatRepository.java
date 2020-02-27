package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository  extends JpaRepository<Chat, Long> {

}
