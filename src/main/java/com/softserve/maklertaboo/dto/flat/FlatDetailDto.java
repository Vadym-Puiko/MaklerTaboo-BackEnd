package com.softserve.maklertaboo.dto.flat;

import com.softserve.maklertaboo.entity.comment.Comment;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlatDetailDto {

    private String description;
    private String title;
    private Double monthPrice;

    private List<String> photos;
    private Set<String> tags;
    private List<Comment> comments;
}
