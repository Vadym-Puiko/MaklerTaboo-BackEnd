package com.softserve.maklertaboo.dto;

import lombok.Data;

import java.util.List;

@Data
public class FlatSearchParameters {
    private String region;
    private String numberofRooms;
    private String floor;
    private String monthPrice;
    private List<String> tags;
}

