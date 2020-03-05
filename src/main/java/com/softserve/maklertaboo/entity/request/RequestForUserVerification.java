package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestForUserVerification extends RequestForVerification {

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestForVerificationType type;
}
