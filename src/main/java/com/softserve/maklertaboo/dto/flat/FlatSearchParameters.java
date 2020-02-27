package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import java.util.List;

@Data
public class FlatSearchParameters {

    private String region;
    private String numberofRooms;

    private Integer floorLow;
    private Integer floorHigh;

    private Integer priceLow;
    private Integer priceHigh;

    private List<String> tags;

}

