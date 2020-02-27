package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public abstract class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private User userAuthor;
}