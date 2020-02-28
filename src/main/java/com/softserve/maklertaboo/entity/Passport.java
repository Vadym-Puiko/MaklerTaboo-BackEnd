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
    private String passportNumber;
    private Integer identificationNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PassportPhoto> passportPhotoList;

}
