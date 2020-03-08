package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCommentDto {
    private Long id;
    private Long userId;
    private User userAuthor;
    private String text;
    private LocalDateTime publicationDate;
}
