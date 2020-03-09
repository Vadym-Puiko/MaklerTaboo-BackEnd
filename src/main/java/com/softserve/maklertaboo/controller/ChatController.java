package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class ChatController {
    private ChatService chatService;
    private ModelMapper modelMapper;
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(value = "/chats/{chatId}/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfMessages(@PathVariable(value = "chatId") Long chatId) {
        return chatService.getCountOfMessages(chatId);
    }

    /*PostMapping("/new/chat")
    public void newChat(@RequestBody MessageChatDto newChatDto) {
     chatService.createChat(newChatDto);
    }*/
}
