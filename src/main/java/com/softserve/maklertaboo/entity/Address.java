package com.softserve.maklertaboo.entity;

import lombok.Data;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.hibernate.search.annotations.Index.YES;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(index= YES, analyze= Analyze.YES, store= Store.YES)
    private String street;

    private Integer houseNumber;

    private Integer flatNumber;

}
