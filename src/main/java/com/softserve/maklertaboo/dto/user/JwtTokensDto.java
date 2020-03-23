package com.softserve.maklertaboo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtTokensDto {
    private String accessToken;
    private String refreshToken;
}
