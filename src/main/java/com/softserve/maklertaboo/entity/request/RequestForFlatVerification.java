package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.flat.Flat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RequestForFlatVerification extends RequestForVerification {

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    private Flat flat;

}
