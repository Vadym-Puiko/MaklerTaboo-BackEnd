package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCommentDto {
    private Long id;
    private Long userId;
    private UserDto userAuthor;
    private Long commentAboutComment;
    private String text;
    private LocalDateTime publicationDate;
}
