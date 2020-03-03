package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "requests_user_verification")
public class RequestForUserVerification extends RequestForVerification {
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
