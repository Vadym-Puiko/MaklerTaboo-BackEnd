package com.softserve.maklertaboo.dto.chat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class ChatDTO {
    private Long id;
    private String message;
    private Long senderId;
    private String username;
    private Long chatId;
}
