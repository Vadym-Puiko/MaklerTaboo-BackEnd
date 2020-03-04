package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public abstract class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String text;


    private LocalDateTime publicationDate;


    private LocalDateTime deletedDate;

    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    private User userAuthor;
}