package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.chat.*;
import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.repository.chat.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private ChatService chatService;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(SimpMessagingTemplate simpMessagingTemplate,
                          UserService userService,
                          ChatService chatService,
                          ModelMapper modelMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.chatService = chatService;
        this.modelMapper = modelMapper;
    }

    /**
     * @param message
     * @return
     */
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * @param id
     */
    public void updateMessage(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        message.setDataSeen(LocalDateTime.now());
        messageRepository.save(message);
    }

    /**
     * @param id
     */
    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
        }
    }

    /**
     * @param chatId
     * @param pageable
     * @return
     */
    public Page<Message> getMessageByChatId(Long chatId, Pageable pageable) {
        return messageRepository.findAllByChatId(chatId, pageable);
    }

    /**
     * @param chatMessageInfoDTO
     */
    public void sendMessage(ChatMessageInfoDTO chatMessageInfoDTO) {
        Message message = new Message();
        message.setSender(chatService.findOne(chatMessageInfoDTO.getUserId()));
        message.setChat(chatService.getChatById(chatMessageInfoDTO.getChatId()));
        message.setContent(chatMessageInfoDTO.getContent());
        Message sendBackMessage = addMessage(message);
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",
                chatMessageInfoDTO.getChatId()), modelMapper.map(sendBackMessage, ChatMessageDTO.class));
    }

    /**
     * @param deleteMessageInfoDTO
     */
    public void setStatusToDeletedMessage(DeleteMessageInfoDTO deleteMessageInfoDTO) {
        DeletedMessageDTO deletedMessageDTO = new DeletedMessageDTO();
        deletedMessageDTO.setMessageId(deleteMessageInfoDTO.getMessageId());
        deletedMessageDTO.setStatus("deleted");
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",
                deleteMessageInfoDTO.getChatId()), deletedMessageDTO);
    }

    /**
     * @param updateMessageDTO
     */
    public void setStatusAndUpdateMessage(UpdateMessageDTO updateMessageDTO) {
        UpdatedMessageDTO updatedMessageDTO = new UpdatedMessageDTO();
        updatedMessageDTO.setStatus("updated");
        updatedMessageDTO.setMessageId(updateMessageDTO.getMessageId());
        updateMessage(updateMessageDTO.getMessageId());
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",
                updateMessageDTO.getChatId()), updatedMessageDTO);
    }

    /**
     * @param counterOfUnreadMessagesDTO
     */
    public void getCountOfUnreadMessages(CounterOfUnreadMessagesDTO counterOfUnreadMessagesDTO) {
        CounterOfUnreadMessagesInfoDTO counterOfUnreadMessagesInfoDTO = new CounterOfUnreadMessagesInfoDTO();
        counterOfUnreadMessagesInfoDTO.setStatus("unread");
        counterOfUnreadMessagesInfoDTO.setCountOfUnreadMessage(chatService.getCountOfUnreadMessages(counterOfUnreadMessagesDTO.getChatId()));
        counterOfUnreadMessagesInfoDTO.setChatId(counterOfUnreadMessagesDTO.getChatId());
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",
                counterOfUnreadMessagesDTO.getChatId()), counterOfUnreadMessagesInfoDTO);
    }
}
