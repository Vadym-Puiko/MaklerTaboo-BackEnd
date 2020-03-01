package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
        }
    }

    public List<Message> getMessageByChatId(Long chatId) {
        return messageRepository.findAllByChatId(chatId);
    }
}
