package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.flat.Flat;
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

    private Long commentAboutComment;

    private String text;

    @Column(columnDefinition = "DATETIME default NOW()")
    private LocalDateTime publicationDate;

    private LocalDateTime deletedDate;

    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean isActive;

//    @Column(columnDefinition = "bigint default 0")
//    private Long commentLike;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isCommentBad;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User userAuthor;

    @PrePersist
    public void prePersist() {
        if (isActive == null) {
            isActive = true;
        }
        if (publicationDate == null) {
            publicationDate= LocalDateTime.now();
        }
        if (isCommentBad==null){
            isCommentBad=false;
        }
    }
}