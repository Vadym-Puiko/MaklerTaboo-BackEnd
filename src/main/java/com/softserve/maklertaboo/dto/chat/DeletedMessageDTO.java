package com.softserve.maklertaboo.dto.chat;

import lombok.Data;

@Data
public class DeletedMessageDTO {
    private String status;
    private Long messageId;
}
