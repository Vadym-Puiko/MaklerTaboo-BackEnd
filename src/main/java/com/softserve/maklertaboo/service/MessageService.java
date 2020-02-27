package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

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
