package com.softserve.maklertaboo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PassportType {

    Id,
    Passport;

   public String getPassportTypeValue(){
       return this.name();
   }
}
