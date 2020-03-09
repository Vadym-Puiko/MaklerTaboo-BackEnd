package com.softserve.maklertaboo.dto.chat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;git

@Data
@Getter
@Setter
public class ChatMessageDTO {
    private Long id;
    private String content;
    private String sendTime;
    private Long senderId;
    private String senderUsername;
    private Long chatId;
}
