package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FlatCommentDto {
    private Long id;
    private Long flatId;
    private UserDto userAuthor;
    private Long commentAboutComment;
    private String text;
    private List<String> commentPhotos;
    private LocalDateTime publicationDate;
}
