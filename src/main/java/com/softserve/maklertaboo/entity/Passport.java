package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private boolean gender;
    private String birthDate;
    private String birthPlace;
    private boolean passportType;
    private String nationality;
    private String authority;
    private String dateOfIssue;
    private String expirationDate;

    @Column(unique = true, nullable = false)
    private String passportNumber;

    @Column(unique = true, nullable = false)
    private int identificationNumber;

    @OneToOne
    private User user;

    @OneToMany
    List<PassportPhoto> passportPhotoList = new ArrayList<>();
}
