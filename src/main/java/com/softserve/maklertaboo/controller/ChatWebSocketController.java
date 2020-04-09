package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.*;
import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.service.ChatService;
import com.softserve.maklertaboo.service.MessageService;
import com.softserve.maklertaboo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@Slf4j

@RestController
public class ChatWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private ChatService chatService;
    private MessageService messageService;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public ChatWebSocketController(SimpMessagingTemplate simpMessagingTemplate, UserService userService, ChatService chatService, MessageService messageService, ModelMapper modelMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @MessageMapping("/send/message")
    public void sendMessage(@Valid @Payload ChatMessageInfoDTO chatMessageInfoDTO) {

        log.info("ChatWebsocketController send message");
        log.info(chatMessageInfoDTO.getChatId() + "    " + chatMessageInfoDTO.getContent());
        Message message = new Message();
        message.setSender(chatService.findOne(chatMessageInfoDTO.getUserId()));
        message.setChat(chatService.getChatById(chatMessageInfoDTO.getChatId()));
        message.setContent(chatMessageInfoDTO.getContent());

        Message sendBackMessage = messageService.addMessage(message);
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", chatMessageInfoDTO.getChatId()), modelMapper.map(sendBackMessage, ChatMessageDTO.class));

    }

    @MessageMapping("/delete/message")
    public void deleteMessage(@Valid @Payload DeleteMessageInfoDTO deleteMessageInfoDTO) {

        log.info("ChatWebsocketController delete message");

        messageService.deleteMessage(deleteMessageInfoDTO.getMessageId());

        DeletedMessageDTO deletedMessageDTO = new DeletedMessageDTO();
        deletedMessageDTO.setMessageId(deleteMessageInfoDTO.getMessageId());
        deletedMessageDTO.setStatus("deleted");
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", deleteMessageInfoDTO.getChatId()), deletedMessageDTO);
    }


    @MessageMapping("/updateDate/message")
    public void updateMessage(@Payload UpdateMessageDTO updateMessageDTO) {
        log.info("ChatWebsocketController send message");
//        Optional<Message> sendBackMessage = messageService.updateMessage(updateMessageDTO.getMessageId());
//        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", updateMessageDTO.getChatId()),  modelMapper.map(sendBackMessage, ChatMessageDTO.class));
          UpdatedMessageDTO updatedMessageDTO = new UpdatedMessageDTO();
          updatedMessageDTO.setStatus("updated");
          updatedMessageDTO.setMessageId(updateMessageDTO.getMessageId());
          messageService.updateMessage(updateMessageDTO.getMessageId());
          simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", updateMessageDTO.getChatId()), updatedMessageDTO);

//    }
    }

   /* @MessageMapping(value = "/chats/countUnread/message")
    public void countOfUnreadMessages(@Payload Long chatId) {
        chatService.getCountOfUnreadMessages(chatId);
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", chatId), chatId);
    }*/
    @MessageMapping("/countUnread/message")
    public void countOfUnreadMessages(@Payload CounterOfUnreadMessagesDTO counterOfUnreadMessagesDTO) {
        CounterOfUnreadMessagesInfoDTO counterOfUnreadMessagesInfoDTO = new CounterOfUnreadMessagesInfoDTO();
        counterOfUnreadMessagesInfoDTO.setStatus("unread");
        counterOfUnreadMessagesInfoDTO.setCountOfUnreadMessage(chatService.getCountOfUnreadMessages(counterOfUnreadMessagesDTO.getChatId()));
        counterOfUnreadMessagesInfoDTO.setChatId(counterOfUnreadMessagesDTO.getChatId());
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", counterOfUnreadMessagesDTO.getChatId()), counterOfUnreadMessagesInfoDTO);
/*
        return chatService.getCountOfUnreadMessages(counterOfUnreadMessagesDTO.getChatId());
*/
    }

   /* @PutMapping("/updateDate/message")
    public void updateMessage(@Payload UpdateMessageDTO updateMessageDTO) {
        log.info("ChatWebsocketController send message");
        List<Long> id = updateMessageDTO.getMessagesId();
        id.stream().forEach(message -> messageService.updateMessage(message));
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", updateMessageDTO.getChatId()),  updateMessageDTO);
    }*/
}

