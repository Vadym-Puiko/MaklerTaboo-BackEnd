package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

@Data
public class ComplaintDto {
    private Long id;
    private UserDto user;
    private FlatCommentDto flatComment;
    private UserCommentDto UserComment;
    private String text;
}
