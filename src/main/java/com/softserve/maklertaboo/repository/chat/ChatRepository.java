package com.softserve.maklertaboo.repository.chat;

import com.softserve.maklertaboo.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Provides an interface to manage {@link Chat} entity.
 *
 * @author Mykola Borovets
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * @param id Long
     * @return {@link List<Chat>}
     */
    List<Chat> findAllBySender_Id(Long id);

    /**
     * @param id Long
     * @return {@link List<Chat>}
     */
    List<Chat> findAllByReceiver_Id(Long id);
}
