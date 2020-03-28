package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class NewFlatDto {

    private String email;
    @NotEmpty
    private String description;
    @NotEmpty
    private String title;
    @NotNull
    private Integer monthPrice;
    @NotNull
    private Integer numberOfRooms;
    @NotNull
    private Integer floor;
    @NotEmpty
    private String district;
    @NotNull
    private Integer HouseNumber;
    @NotNull
    private Integer FlatNumber;
    @NotEmpty
    private String Street;

    private List<String> base64Photos;
    private Set<String> tags;
}
