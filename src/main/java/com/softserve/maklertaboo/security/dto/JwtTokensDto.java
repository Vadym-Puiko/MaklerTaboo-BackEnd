package com.softserve.maklertaboo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtTokensDto {
    private String accesstoken;
    private String refreshtoken;
}
