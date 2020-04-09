package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.ChatDTO;
import com.softserve.maklertaboo.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
public class ChatController {
    private ChatService chatService;
    private ModelMapper modelMapper;

    @Autowired
    public ChatController(ChatService chatService, ModelMapper modelMapper) {
        this.chatService = chatService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/chats/{chatId}/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfMessages(@PathVariable(value = "chatId") Long chatId) {
        return chatService.getCountOfMessages(chatId);
    }

    @GetMapping(value = "/chats/{chatId}/countUnread")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfUnreadMessages(@PathVariable(value = "chatId") Long chatId) {
        return chatService.getCountOfUnreadMessages(chatId);
    }



    @GetMapping("/chats/{id}")
        public List<ChatDTO> getCurrentChatsByUserId(@Valid @PathVariable Long id) {
        log.info("СhatController get chats by userId");
        return chatService.getChatByUserId(id)
                .stream()
                .map(source -> modelMapper.map(source, ChatDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/chat")
    public Long getChat(@RequestParam String recieverName, @RequestParam Long senderId) {
        return chatService.getChatId(recieverName, senderId);
    }
}
