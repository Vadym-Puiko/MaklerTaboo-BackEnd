package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import java.util.List;

@Data
public class NewFlatDto {
    private String description;
    private String title;
    private Double monthPrice;

    private List<byte[]> base64Photos;
    private List<String> tags;
}
