package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.enums.GenderType;
import com.softserve.maklertaboo.entity.enums.PassportType;
import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    @Enumerated(value = EnumType.STRING)
    private GenderType gender;
    private java.time.LocalDate birthDate;
    private String birthPlace;
    @Enumerated(value = EnumType.STRING)
    private PassportType passportType;
    private String nationality;
    private String authority;
    private java.time.LocalDate dateOfIssue;
    private java.time.LocalDate expirationDate;
    private String passportNumber;
    private Long identificationNumber;

    @OneToOne(cascade = CascadeType.DETACH, mappedBy = "passport")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PassportPhoto> passportPhotoList;


}
