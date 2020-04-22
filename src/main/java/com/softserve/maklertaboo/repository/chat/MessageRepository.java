package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides an interface to manage {@link Message} entity.
 *
 * @author Mykola Borovets
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * @param chatId
     * @param pageable
     * @author Mykola Borovets
     */
    Page<Message> findAllByChatId(Long chatId, Pageable pageable);

    /**
     * @param chatId Long
     * @author Mykola Borovets
     */
    Long countByChatIdAndDataSeenIsNull(Long chatId);

    /**
     * @param chatId Long
     * @author Mykola Borovets
     */
    Long countByChatId(Long chatId);
}
