package com.softserve.maklertaboo.entity.flat;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Entity
public class FlatSearchParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> regions;

    private Integer minNumberOfRooms;
    private Integer maxNumberOfRooms;

    private Integer floorLow;
    private Integer floorHigh;

    private Integer priceLow;
    private Integer priceHigh;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> searchText;

}
