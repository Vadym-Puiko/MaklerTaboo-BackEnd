package com.softserve.maklertaboo.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Positive;

@Slf4j
@Controller
public class ChatController {
 /*   private ChatService chatService;*/

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/chats/{chatId}/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfMessages(@PathVariable(value = "chatId") @Positive @NonNull Long chatId) {

 /*       return chatService.getCountOfMessages(chatId);*/
    }
}
