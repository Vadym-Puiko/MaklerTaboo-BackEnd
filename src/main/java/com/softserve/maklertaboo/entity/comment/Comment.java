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

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column(nullable = false)
    private LocalDateTime deletedDate;

    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    private User userAuthor;
}