package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.chat.ChatMessageDTO;
import com.softserve.maklertaboo.entity.chat.Message;
import com.softserve.maklertaboo.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for {@link Message} entity.
 *
 * @author Mykola Borovets
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/")
public class MessageController {

    private MessageService messageService;
    private ModelMapper modelMapper;

    /**
     * Constructor with parameters of {@link MessageController}.
     *
     * @author Mykola Borovets
     */
    @Autowired
    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    /**
     * Method to get messages by page number and chatId.
     *
     * @param id Long
     * @param  page Integer
     * @return {@link List<ChatMessageDTO>}
     * @author Mykola Borovets
     */
    @ApiOperation(value = "Get messages by chatId and page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/messages/{id}/{page}")
    public List<ChatMessageDTO> getCurrentMessagesByChatId(@PathVariable Long id, @PathVariable Integer page) {
        log.info("MessagesController get messages by chat id");
        Pageable pageable = PageRequest.of(page, 25, Sort.by("id").descending());
        List <ChatMessageDTO> list = messageService.getMessageByChatId(id, pageable)
                .stream()
                .map(source -> modelMapper.map(source, ChatMessageDTO.class))
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }
}
