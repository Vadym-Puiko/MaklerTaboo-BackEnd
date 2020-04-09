package com.softserve.maklertaboo.entity.flat;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FlatLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Flat flat;

    private String latitude;
    private String longitude;
}
