package com.softserve.maklertaboo.dto.chat;

import lombok.Data;

@Data
public class CounterOfUnreadMessagesInfoDTO {
    private String status;
    private Long chatId;
    private Long countOfUnreadMessage;
}
