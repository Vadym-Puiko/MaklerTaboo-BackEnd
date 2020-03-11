package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.ChatMessageDTO;
import com.softserve.maklertaboo.dto.chat.ChatMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.DeleteMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.DeletedMessageDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@Slf4j
@CrossOrigin
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

/*    @DeleteMapping
    public void deleteMessage1(@Payload DeleteMessageInfoDTO deleteMessageInfoDTO) {

        log.info("ChatWebsocketController delete message");

        messageService.deleteMessage(deleteMessageInfoDTO.getMessageId());

        DeletedMessageDTO deletedMessageDTO = new DeletedMessageDTO();
        deletedMessageDTO.setMessageId(deleteMessageInfoDTO.getMessageId());
        deletedMessageDTO.setStatus("deleted");
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",deleteMessageInfoDTO.getChatId()),deletedMessageDTO);
    }
    @PutMapping
    public void sendMessage1(@Payload ChatMessageInfoDTO chatMessageInfoDTO) {

        log.info("ChatWebsocketController send message");
        log.info(chatMessageInfoDTO.getChatId() + "    " + chatMessageInfoDTO.getContent());
        Message message = new Message();
        message.setSender(chatService.findOne(chatMessageInfoDTO.getUserId()));
        message.setChat(chatService.getChatById(chatMessageInfoDTO.getChatId()));
        message.setContent(chatMessageInfoDTO.getContent());

        Message sendBackMessage = messageService.addMessage(message);
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s",chatMessageInfoDTO.getChatId()), modelMapper.map(sendBackMessage, ChatMessageDTO.class));

    }*/

    @MessageMapping("/send/message")
    public void sendMessage(@Payload ChatMessageInfoDTO chatMessageInfoDTO) {

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
    public void deleteMessage(@Payload DeleteMessageInfoDTO deleteMessageInfoDTO) {

        log.info("ChatWebsocketController delete message");

        messageService.deleteMessage(deleteMessageInfoDTO.getMessageId());

        DeletedMessageDTO deletedMessageDTO = new DeletedMessageDTO();
        deletedMessageDTO.setMessageId(deleteMessageInfoDTO.getMessageId());
        deletedMessageDTO.setStatus("deleted");
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", deleteMessageInfoDTO.getChatId()), deletedMessageDTO);
    }

/*    @PutMapping("/update/message")
    public void updateMessage(@Payload UpdateMessageDTO updateMessageDTO) {
        log.info("ChatWebsocketController update message");
        log.info(updateMessageDTO.getChatId() + "    " + updateMessageDTO.getContent());
        messageService.updateMessage();
        Message message = new Message();
        message.setSender(chatService.findOne(updateMessageDTO.getUserId()));
        message.setChat(chatService.getChatById(updateMessageDTO.getChatId()));
        *//*message.setSender(userService.findOne(chatMessageInfoDTO.getUserId()));
        message.setChat(chatService.getChatById(chatMessageInfoDTO.getChatId()));*//*
        message.setContent(updateMessageDTO.getContent());

        Message sendBackMessage = messageService.updateMessage(message);
        simpMessagingTemplate.convertAndSend(format("/topic/messages/%s", updateMessageDTO.getChatId()), modelMapper.map(sendBackMessage, ChatMessageDTO.class));

    }*/
}

