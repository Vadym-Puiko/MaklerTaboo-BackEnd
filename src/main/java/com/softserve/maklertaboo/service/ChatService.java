package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Chat;
import com.softserve.maklertaboo.repository.chat.ChatRepository;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    public Optional<Chat> getChatById(Long id) {
        return chatRepository.findById(id);
    }

    public void deleteChatById(Long id){
        Optional<Chat> chat= chatRepository.findById(id);
        if(chat.isPresent()){
            chatRepository.deleteById(id);
        }
    }

    public List<Chat> getAllChatBySenderId(Long id){
        return chatRepository.findAllBySenderId(id);
    }

    public Long getCountOfMessages(Long chatId) {
        return messageRepository.countByChatId(chatId);
    }
}


