package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name="passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean gender;
    private String birthDate;
    private String birthPlace;
    private Boolean passportType;
    private String nationality;
    private String authority;
    private String dateOfIssue;
    private String expirationDate;

    @Column(unique = true, nullable = false)
    private String passportNumber;

    @Column(unique = true, nullable = false)
    private Integer identificationNumber;

    @OneToOne
    private User user;

    @OneToMany
    private List<PassportPhoto> passportPhotoList;

}
