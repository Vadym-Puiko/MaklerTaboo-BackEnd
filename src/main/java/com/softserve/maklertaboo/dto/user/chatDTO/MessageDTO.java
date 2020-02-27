package com.softserve.maklertaboo.dto.user.chatDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Long id;
    private String content;
    private String sendTime;
    private Long senderId;
    private String username;
    private Long chatId;
}
