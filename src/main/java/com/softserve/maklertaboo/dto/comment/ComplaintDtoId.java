package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

@Data
public class ComplaintDtoId {
    private Long flatCommentId;
    private Long UserCommentId;
    private String text;
}
