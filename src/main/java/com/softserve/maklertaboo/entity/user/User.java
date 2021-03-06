package com.softserve.maklertaboo.entity.user;
import com.softserve.maklertaboo.entity.Order;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.TelegramUserData;
import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForBanFlat;
import com.softserve.maklertaboo.entity.request.RequestForUserVerification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode(
        exclude = {"passport", "orders", "flats", "favoriteFlats", "comments", "userComments", "requestUser",
                "registrationDate", "refreshKey", "telegramUserData", "requestForBanFlat"})
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

    @Column(columnDefinition = "varchar(255) default 'ACTIVATED'")
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Flat> flats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<FavoriteFlat> favoriteFlats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAuthor")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserComment> userComments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<RequestForUserVerification> requestUser;

    @Column(columnDefinition = "DATETIME default NOW()")
    private LocalDateTime registrationDate;

    private String refreshKey;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(name = "telegram_id", referencedColumnName = "id")
    private TelegramUserData telegramUserData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<RequestForBanFlat> requestForBanFlat;

    @PrePersist
    public void prePersist() {
        setRole(UserRole.ROLE_USER);
        setUserStatus(UserStatus.DEACTIVATED);
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }

    public User(Long id, String username, String email, String password, String phoneNumber,
                String photoUrl, UserRole role, UserStatus userStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.role = role;
        this.userStatus = userStatus;
    }
}
