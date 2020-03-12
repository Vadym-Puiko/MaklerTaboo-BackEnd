package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.chat.ChatMessageDTO;
import com.softserve.maklertaboo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController

@RequestMapping("/")
//(origins = "http://localhost:4200", maxAge = 3600)
public class MessageController {
    private MessageService messageService;
    private ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/messages/{id}")
    public List<ChatMessageDTO> getCurrentMessagesByChatId(@PathVariable Long id) {
        log.info("MessagesController get messages by chat id");
        return messageService.getMessageByChatId(id)
                .stream()
                .map(source -> modelMapper.map(source, ChatMessageDTO.class))
                .collect(Collectors.toList());
    }
}
