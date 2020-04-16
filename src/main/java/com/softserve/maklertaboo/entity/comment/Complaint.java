package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private FlatComment flatComment;

    @ManyToOne
    private UserComment userComment;

    private String text;

    @Column(columnDefinition = "DATETIME default NOW()")
    private LocalDateTime publicationDate;

    @PrePersist
    public void prePersist() {
        if (publicationDate == null) {
            publicationDate = LocalDateTime.now().plusHours(3);
        }
    }
}
