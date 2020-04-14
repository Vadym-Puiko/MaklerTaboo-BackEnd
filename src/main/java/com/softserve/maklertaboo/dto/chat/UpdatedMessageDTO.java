package com.softserve.maklertaboo.dto.chat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdatedMessageDTO {
    private String status;
    private Long messageId;
    private LocalDateTime dateSeen;
}
