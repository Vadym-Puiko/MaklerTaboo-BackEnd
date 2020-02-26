package com.softserve.maklertaboo.dto;

import com.softserve.maklertaboo.entity.comment.Comment;
import lombok.Data;

import java.util.List;

@Data
public class FlatDetailDto {

    private String description;
    private String title;

    private Double monthPrice;
    private String photoUrl;
    private List<String> photos;
    private List<String> tags;

    private List<Comment> comments;
}
