package com.softserve.maklertaboo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTSuccessLogInDto {
    private Long userId;
    private String username;
    private String email;
    private String role;
}