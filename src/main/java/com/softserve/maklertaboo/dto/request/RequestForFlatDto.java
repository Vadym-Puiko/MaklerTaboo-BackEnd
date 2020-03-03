package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import lombok.Data;

import java.util.Date;

@Data
public class RequestForFlatDto {
    private Long id;
    private boolean isApproved;
    private Date creationDate;
    private Date approvalDate;
    private FlatDto flat;
}
