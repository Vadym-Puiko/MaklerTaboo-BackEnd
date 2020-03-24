package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestForUserVerification extends RequestForVerification {
    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestForVerificationType type;
}

