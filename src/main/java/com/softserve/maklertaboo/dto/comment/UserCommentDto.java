package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCommentDto {
    private Long id;
    private String text;
    private Long userId;
    private LocalDateTime publicationDate;
}
