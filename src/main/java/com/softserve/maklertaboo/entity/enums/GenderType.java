package com.softserve.maklertaboo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderType {
    Men,
    Woman;

    public String getGetGenderValue() {
        return this.name();
    }
}
