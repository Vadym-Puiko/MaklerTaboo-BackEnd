package com.softserve.maklertaboo.dto.chat;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ChatMessageInfoDTO {
    private Long chatId;
    private Long userId;
    private String content;
}
