package com.softserve.maklertaboo.dto.chat;

import lombok.Data;

@Data
public class UpdateMessageDTO {
    private Long messageId;
    private Long chatId;
    private Long senderId;
}
