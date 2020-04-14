package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private FlatComment flatComment;

    @ManyToOne
    private UserComment userComment;
}
