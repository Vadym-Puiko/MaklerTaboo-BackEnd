package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class FlatCommentDto {
    private Long id;
    private Long flatId;
    private UserDto userAuthor;
    private Long commentAboutComment;
    private Long commentLikes;
    private String text;
    private LocalDateTime publicationDate;
}
