package com.softserve.maklertaboo.entity.user;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.Order;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.enums.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(length = 65535, columnDefinition = "text")
    private String photoUrl;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Flat> flats;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserComment> userComments;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date registrationDate;

    private String refreshKey;

    @PrePersist
    public void prePersist() {
        setRole(UserRole.ROLE_USER);
    }
}
