package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class NewFlatDto {

    private String username;

    private String description;
    private String title;

    private Integer monthPrice;
    private Integer numberOfRooms;

    private Integer floor;
    private String district;

    private Integer HouseNumber;
    private Integer FlatNumber;

    private String Street;

    private List<String> base64Photos;
    private Set<String> tags;
}
