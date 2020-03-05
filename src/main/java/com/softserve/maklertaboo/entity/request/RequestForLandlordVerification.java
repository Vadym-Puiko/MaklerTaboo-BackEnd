package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestForLandlordVerification extends RequestForVerification {

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private User user;
}
