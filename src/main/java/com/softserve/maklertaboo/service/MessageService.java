package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public void updateMessage(Long id, Date date) {
        Message message = messageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        message.setDataSeen(date);
        messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
        }
    }

    public Page<Message> getMessageByChatId(Long chatId, Pageable pageable) {
        return messageRepository.findAllByChatId(chatId, pageable);
    }
}
