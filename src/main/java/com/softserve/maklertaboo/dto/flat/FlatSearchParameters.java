package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import java.util.Set;

@Data
public class FlatSearchParameters {

    private String region;
    private Integer numberOfRooms;

    private Integer floorLow;
    private Integer floorHigh;

    private Integer priceLow;
    private Integer priceHigh;

    private Set<String> tags;

}

