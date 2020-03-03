package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
public class RequestForUserVerification extends RequestForVerification {

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
