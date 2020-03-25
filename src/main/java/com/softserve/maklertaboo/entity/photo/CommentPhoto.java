package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.entity.comment.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class CommentPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "url")
    private String url;


    @ManyToOne(cascade = CascadeType.ALL)
    private FlatComment flatComment;
}
