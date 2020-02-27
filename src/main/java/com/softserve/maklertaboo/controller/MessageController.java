package com.softserve.maklertaboo.controller;

import org.modelmapper.ModelMapper;
import com.softserve.maklertaboo.dto.user.chatDTO.MessageDTO;
import com.softserve.maklertaboo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private ModelMapper modelMapper;

    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper){
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/messages/{id}")
//    @PreAuthorize("hasRole('USER')")
    public List<MessageDTO> getCurrentMessagesByChatId(@PathVariable Long id) {

        log.info("MessagesController get messages by chat id");

        return messageService.getMessageByChatId(id)
                .stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
