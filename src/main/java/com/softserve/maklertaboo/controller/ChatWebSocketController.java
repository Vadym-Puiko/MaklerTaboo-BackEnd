package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.ChatMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.CounterOfUnreadMessagesDTO;
import com.softserve.maklertaboo.dto.chat.DeleteMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.UpdateMessageDTO;
import com.softserve.maklertaboo.entity.chat.Chat;
import com.softserve.maklertaboo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Controller for {@link Chat} entity.
 *
 * @author Mykola Borovets
 */
@Slf4j
@RestController
public class ChatWebSocketController {

    private MessageService messageService;

    /**
     * Constructor with parameters of {@link ChatWebSocketController}.
     *
     * @author Mykola Borovets
     */
    @Autowired
    public ChatWebSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Method to send messages via websocket
     *
     * @param chatMessageInfoDTO ChatMessageInfoDTO
     * @author Mykola Borovets
     */
    @MessageMapping("/send/message")
    public void sendMessage(@Valid @Payload ChatMessageInfoDTO chatMessageInfoDTO) {
        log.info("ChatWebsocketController send message");
        log.info(chatMessageInfoDTO.getChatId() + "    " + chatMessageInfoDTO.getContent());
        messageService.sendMessage(chatMessageInfoDTO);
    }

    /**
     * Method to delete via websocket
     *
     * @param deleteMessageInfoDTO DeleteMessageInfoDTO
     * @author Mykola Borovets
     */
    @MessageMapping("/delete/message")
    public void deleteMessage(@Valid @Payload DeleteMessageInfoDTO deleteMessageInfoDTO) {
        log.info("ChatWebsocketController delete message");
        messageService.deleteMessage(deleteMessageInfoDTO.getMessageId());
        messageService.setStatusToDeletedMessage(deleteMessageInfoDTO);
    }

    /**
     * Method to update via websocket
     *
     * @param updateMessageDTO UpdateMessageDTO
     * @author Mykola Borovets
     */
    @MessageMapping("/updateDate/message")
    public void updateMessage(@Payload UpdateMessageDTO updateMessageDTO) {
        log.info("ChatWebsocketController update messages");
        messageService.setStatusAndUpdateMessage(updateMessageDTO);
    }

    /**
     * Method to count unread messages via websocket
     *
     * @param counterOfUnreadMessagesDTO CounterOfUnreadMessagesDTO
     * @author Mykola Borovets
     */
    @MessageMapping("/countUnread/message")
    public void countOfUnreadMessages(@Payload CounterOfUnreadMessagesDTO counterOfUnreadMessagesDTO) {
        log.info("ChatWebsocketController get counter of unread messages");
        messageService.getCountOfUnreadMessages(counterOfUnreadMessagesDTO);
    }
}

