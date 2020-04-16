package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.chat.ChatDTO;
import com.softserve.maklertaboo.entity.chat.Chat;
import com.softserve.maklertaboo.service.ChatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller for {@link Chat} entity.
 *
 * @author Mykola Borovets
 */
@Slf4j
@RestController
public class ChatController {

    private ChatService chatService;
    private ModelMapper modelMapper;

    /**
     * Constructor with parameters of {@link ChatController}.
     *
     * @author Mykola Borovets
     */
    @Autowired
    public ChatController(ChatService chatService, ModelMapper modelMapper) {
        this.chatService = chatService;
        this.modelMapper = modelMapper;
    }

    /**
     * Method to get count of messages by chatId.
     *
     * @param chatId Long
     * @return {@link Long}
     * @author Mykola Borovets
     */
    @ApiOperation(value = "Get count of all messages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping(value = "/chats/{chatId}/count")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfMessages(@PathVariable(value = "chatId") Long chatId) {
        return chatService.getCountOfMessages(chatId);
    }

    /**
     * Method to get count of unread messages
     *
     * @param chatId Long
     * @return amount of unread messages
     * @author Mykola Borovets
     */
    @ApiOperation(value = "Get count of unread messages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping(value = "/chats/{chatId}/countUnread")
    @ResponseStatus(HttpStatus.OK)
    public Long countOfUnreadMessages(@PathVariable(value = "chatId") Long chatId) {
        return chatService.getCountOfUnreadMessages(chatId);
    }


    /**
     * Method to get all chats by UserId
     *
     * @param id Long
     * @return {@link List<ChatDTO>}
     * @author Mykola Borovets
     */
    @ApiOperation(value = "Get all chats by UserId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/chats/{id}")
    public List<ChatDTO> getCurrentChatsByUserId(@Valid @PathVariable Long id) {
        log.info("СhatController get chats by userId");
        return chatService.getChatByUserId(id)
                .stream()
                .map(source -> modelMapper.map(source, ChatDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Method to get chat by senderId and recieverName
     *
     * @param recieverName String
     * @param senderId     Long
     * @return chat between two user
     * @author Mykola Borovets
     */
    @ApiOperation(value = "Get  chat by senderId and receiverName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/chat")
    public Long getChat(@RequestParam String recieverName, @RequestParam Long senderId) {
        return chatService.getChatId(recieverName, senderId);
    }
}
