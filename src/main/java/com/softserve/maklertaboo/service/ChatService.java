package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.chat.Chat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.chat.ChatRepository;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat createChatbyId(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteChatById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            chatRepository.deleteById(id);
        }
    }
/*

    public List<Chat> getAllChatBySenderId(Long id) {
        return chatRepository.findAllBySenderId(id);
    }
*/


    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
      /*  .orElseThrow(IllegalArgumentException::new)*/
    }

    public Long getCountOfMessages(Long chatId) {
        return messageRepository.countByChatId(chatId);
    }

}


