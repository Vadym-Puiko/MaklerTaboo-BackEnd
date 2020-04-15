package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.ChatMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.CounterOfUnreadMessagesDTO;
import com.softserve.maklertaboo.dto.chat.DeleteMessageInfoDTO;
import com.softserve.maklertaboo.dto.chat.UpdateMessageDTO;
import com.softserve.maklertaboo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ChatWebSocketController {
    private MessageService messageService;

    @Autowired
    public ChatWebSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send/message")
    public void sendMessage(@Valid @Payload ChatMessageInfoDTO chatMessageInfoDTO) {
        log.info("ChatWebsocketController send message");
        log.info(chatMessageInfoDTO.getChatId() + "    " + chatMessageInfoDTO.getContent());
        messageService.sendMessage(chatMessageInfoDTO);
    }

    @MessageMapping("/delete/message")
    public void deleteMessage(@Valid @Payload DeleteMessageInfoDTO deleteMessageInfoDTO) {
        log.info("ChatWebsocketController delete message");
        messageService.deleteMessage(deleteMessageInfoDTO.getMessageId());
        messageService.setStatusToDeletedMessage(deleteMessageInfoDTO);
    }

    @MessageMapping("/updateDate/message")
    public void updateMessage(@Payload UpdateMessageDTO updateMessageDTO) {
        log.info("ChatWebsocketController update messages");
        messageService.setStatusAndUpdateMessage(updateMessageDTO);
    }

    @MessageMapping("/countUnread/message")
    public void countOfUnreadMessages(@Payload CounterOfUnreadMessagesDTO counterOfUnreadMessagesDTO) {
        log.info("ChatWebsocketController get counter of unread messages");
        messageService.getCountOfUnreadMessages(counterOfUnreadMessagesDTO);
    }
}

