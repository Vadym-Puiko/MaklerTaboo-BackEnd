package com.softserve.maklertaboo.dto.flat;

import com.softserve.maklertaboo.entity.Address;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlatDetailDto {

    private String username;
    private Long userId;
    private String userPhoto;

    private String description;
    private String title;

    private Integer monthPrice;
    private String creationDate;

    private Integer numberOfRooms;
    private Integer floor;

    private String region;
    private Address address;

    private List<String> photos;
    private Set<String> tags;
}
