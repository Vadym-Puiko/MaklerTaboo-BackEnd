package com.softserve.maklertaboo.mapping;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.entity.Passport;
import org.springframework.stereotype.Component;

@Component
public class PassportMapper implements MapperToDto<Passport, PassportDto>, MapperToEntity<PassportDto, Passport> {

    @Override
    public PassportDto convertToDto(Passport entity) {
        PassportDto passportDto = new PassportDto();
        passportDto.setId(entity.getId());
        passportDto.setPassportType(entity.getPassportType());
        passportDto.setPassportNumber(entity.getPassportNumber());
        passportDto.setNationality(entity.getNationality());
        passportDto.setMiddleName(entity.getMiddleName());
        passportDto.setLastName(entity.getLastName());
        passportDto.setIdentificationNumber(entity.getIdentificationNumber());
        passportDto.setGender(entity.getGender());
        passportDto.setFirstName(entity.getFirstName());
        passportDto.setExpirationDate(entity.getExpirationDate());
        passportDto.setDateOfIssue(entity.getDateOfIssue());
        passportDto.setBirthPlace(entity.getBirthPlace());
        passportDto.setBirthDate(entity.getBirthDate());
        passportDto.setAuthority(entity.getAuthority());
        return passportDto;
    }

    @Override
    public Passport convertToEntity(PassportDto dto) {
        Passport passport = new Passport();
        passport.setId(dto.getId());
        passport.setPassportType(dto.getPassportType());
        passport.setPassportNumber(dto.getPassportNumber());
        passport.setNationality(dto.getNationality());
        passport.setMiddleName(dto.getMiddleName());
        passport.setLastName(dto.getLastName());
        passport.setIdentificationNumber(dto.getIdentificationNumber());
        passport.setGender(dto.getGender());
        passport.setFirstName(dto.getFirstName());
        passport.setExpirationDate(dto.getExpirationDate());
        passport.setDateOfIssue(dto.getDateOfIssue());
        passport.setBirthPlace(dto.getBirthPlace());
        passport.setBirthDate(dto.getBirthDate());
        passport.setAuthority(dto.getAuthority());
        return passport;
    }
}
