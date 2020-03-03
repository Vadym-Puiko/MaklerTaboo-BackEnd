package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

@Data
public class UserCommentDto {
    private String text;
    private Long landlordId;
}
