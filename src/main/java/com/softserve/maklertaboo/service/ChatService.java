package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.entity.chat.Chat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.UserNotFoundException;
import com.softserve.maklertaboo.repository.chat.ChatRepository;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Service implementation for {@link Chat} entity.
 *
 * @author Mykola Borovets
 */
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    /**
     * Constructor with parameters of {@link ChatService}.
     *
     * @author MykolaBorovets
     */
    @Autowired
    public ChatService(ChatRepository chatRepository,
                       MessageRepository messageRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method that get {@link Chat} by chatId.
     *
     * @param chatId of {@link Chat}
     * @return {@link Chat}
     * @author Mykola Borovets
     */
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Method that get {@link List<Chat>} by userId.
     *
     * @param Id of {@link User}
     * @return {@link List<Chat>}
     * @author Mykola Borovets
     */
    public List<Chat> getChatByUserId(Long Id) {
        return Stream.concat(chatRepository.findAllBySender_Id(Id).stream(),
                chatRepository.findAllByReceiver_Id(Id).stream())
                .collect(Collectors.toList());
    }

    /**
     * Method that delete {@link Chat} by chatId.
     *
     * @param id of {@link Chat}
     * @author Mykola Borovets
     */
    public void deleteChatById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            chatRepository.deleteById(id);
        }
    }

    /**
     * Method that get {@link User} by userId.
     *
     * @param id of {@link User}
     * @return
     * @author Mykola Borovets
     */
    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Method that get the count of messages by chatId.
     *
     * @param chatId of {@link Chat}
     * @return count of  messages
     * @author Mykola Borovets
     */
    public Long getCountOfMessages(Long chatId) {
        return messageRepository.countByChatId(chatId);
    }

    /**
     * Method that get the count of unread messages by chatId.
     *
     * @param chatId of {@link Chat}
     * @return count of unread messages
     * @author Mykola Borovets
     */
    public Long getCountOfUnreadMessages(Long chatId) {
        return messageRepository.countByChatIdAndDataSeenIsNull(chatId);
    }

    /**
     * Method that set the count of unread messages by chatId.
     *
     * @param chatId of {@link Chat}
     * @return count of unread messages
     * @author Mykola Borovets
     */
    public void setCountOfUnreadMessages(Long chatId) {
        messageRepository.countByChatIdAndDataSeenIsNull(chatId);
    }

    /**
     * The method that create new chat or return chat that already exists.
     *
     * @param recieverName String
     * @param  senderId Long
     * @return chat that exist or new chat
     * @author Mykola Borovets
     */
    public Long getChatId(String recieverName, Long senderId) {

        User reciever = userRepository.findUserByUsername(recieverName).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME));
        User sender = userRepository.findById(senderId).get();

        if (sender.getId().equals(reciever.getId())) {
            throw new IllegalArgumentException("YOU CANT CHAT WITH YOURSELF");
        }

        List<Chat> chatList1 = chatRepository.findAllBySender_Id(senderId);
        List<Chat> chatList2 = chatRepository.findAllByReceiver_Id(senderId);
        chatList1.addAll(chatList2);
        Long result = null;

        for (Chat chat : chatList1) {
            if (chat.getReceiver().getId().equals(reciever.getId()) ||
                    chat.getSender().getId().equals(reciever.getId())) {
                result = chat.getId();
                break;
            }
        }
        if (result != null) {
            return result;
        }
        Chat chat = new Chat();
        chat.setReceiver(reciever);
        chat.setSender(sender);
        return chatRepository.save(chat).getId();
    }
}


