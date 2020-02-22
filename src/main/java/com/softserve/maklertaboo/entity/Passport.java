package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private List<PassportPhoto> passportPhotoList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return gender == passport.gender &&
                passportType == passport.passportType &&
                identificationNumber == passport.identificationNumber &&
                Objects.equals(id, passport.id) &&
                Objects.equals(firstName, passport.firstName) &&
                Objects.equals(lastName, passport.lastName) &&
                Objects.equals(middleName, passport.middleName) &&
                Objects.equals(birthDate, passport.birthDate) &&
                Objects.equals(birthPlace, passport.birthPlace) &&
                Objects.equals(nationality, passport.nationality) &&
                Objects.equals(authority, passport.authority) &&
                Objects.equals(dateOfIssue, passport.dateOfIssue) &&
                Objects.equals(expirationDate, passport.expirationDate) &&
                Objects.equals(passportNumber, passport.passportNumber) &&
                Objects.equals(user, passport.user) &&
                Objects.equals(passportPhotoList, passport.passportPhotoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, middleName, gender, birthDate, birthPlace, passportType, nationality, authority, dateOfIssue, expirationDate, passportNumber, identificationNumber, user, passportPhotoList);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", gender=" + gender +
                ", birthDate='" + birthDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", passportType=" + passportType +
                ", nationality='" + nationality + '\'' +
                ", authority='" + authority + '\'' +
                ", dateOfIssue='" + dateOfIssue + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", identificationNumber=" + identificationNumber +
                ", user=" + user +
                ", passportPhotoList=" + passportPhotoList +
                '}';
    }
}
