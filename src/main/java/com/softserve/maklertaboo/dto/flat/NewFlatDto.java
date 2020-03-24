package com.softserve.maklertaboo.dto.flat;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class NewFlatDto {

    private String email;
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
