package com.softserve.maklertaboo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FlatDto {

    private Long id;

    private String description;
    private String title;

    private Double monthPrice;
    private String photoUrl;

    private Date creationDate;
}