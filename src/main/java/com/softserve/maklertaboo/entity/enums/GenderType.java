package com.softserve.maklertaboo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderType {
    Male,
    Female;

    public String getGetGenderValue() {
        return this.name();
    }
}
