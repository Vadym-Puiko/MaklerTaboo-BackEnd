package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.mapping.PassportMapper;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    @Autowired
    public PassportService(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    public PassportDto getPassport(Long id) {
        Passport passport = passportRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return passportMapper.convertToDto(passport);
    }

    public void updatePassport(Long id, PassportDto passportDto) {
        Passport passport = passportRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        passport.setPassportType(passportDto.getPassportType());
        passport.setPassportNumber(passportDto.getPassportNumber());
        passport.setNationality(passportDto.getNationality());
        passport.setMiddleName(passportDto.getMiddleName());
        passport.setLastName(passportDto.getLastName());
        passport.setIdentificationNumber(passportDto.getIdentificationNumber());
        passport.setGender(passportDto.getGender());
        passport.setFirstName(passportDto.getFirstName());
        passport.setExpirationDate(passportDto.getExpirationDate());
        passport.setDateOfIssue(passportDto.getDateOfIssue());
        passport.setBirthPlace(passportDto.getBirthPlace());
        passport.setBirthDate(passportDto.getBirthDate());
        passport.setAuthority(passportDto.getAuthority());
        passportRepository.save(passport);
    }

}
