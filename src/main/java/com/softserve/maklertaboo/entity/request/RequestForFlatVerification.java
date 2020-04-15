package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.flat.Flat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RequestForFlatVerification extends RequestForVerification {

    @NonNull
    @ManyToOne
    private Flat flat;
}
