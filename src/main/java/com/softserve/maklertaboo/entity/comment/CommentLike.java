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

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private FlatComment flatComment;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserComment userComment;


}
