package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PassportPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "url")
    private String url;


    @ManyToOne(cascade = CascadeType.ALL)
    private User userAuthor;


    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Passport pasport;
}
