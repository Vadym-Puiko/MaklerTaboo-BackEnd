package com.softserve.maklertaboo.dto.chat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class ChatDTO {
    private Long id;
    private String messages;
    private Long senderId;
    private Long receiverId;
    private String senderUsername;
    private String receiverUsername;
    private Long chatId;
    private Long countOfUnreadMessages;
}
