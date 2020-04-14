package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestForBanFlatDto {
    private Long id;
    private String status;
    private LocalDateTime creationDate;
    private FlatDto flat;
    private UserDto author;
}
