package com.softserve.maklertaboo.dto.chat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ChatMessageInfoDTO {
    private Long userId;
    private Long chatId;
    private String content;
}
