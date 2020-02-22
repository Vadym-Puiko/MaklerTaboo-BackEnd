package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class PassportPhoto extends PhotoAbstract {

    @ManyToOne
    private User user;

}
