package com.softserve.maklertaboo.entity.photo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public abstract class PhotoAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User userAuthor;

    private String urlPhoto;
    private LocalDateTime dateOfCreation;

}
