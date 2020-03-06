package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.mapping.PassportMapper;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassportService {

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    @Autowired
    public PassportService(PassportRepository passportRepository, PassportMapper passportMapper) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
    }

    public List<PassportDto> getPassport(Long id) {
        return passportRepository.findById(id)
                .stream()
                .map(passportMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public void updatePassport(List<PassportDto> passportDtoList) {
        PassportDto dto2 = new PassportDto();
        for (PassportDto dto : passportDtoList) {
            dto2.setId(dto.getId());
            dto2.setPassportType(dto.getPassportType());
            dto2.setPassportNumber(dto.getPassportNumber());
            dto2.setNationality(dto.getNationality());
            dto2.setMiddleName(dto.getMiddleName());
            dto2.setLastName(dto.getLastName());
            dto2.setIdentificationNumber(dto.getIdentificationNumber());
            dto2.setGender(dto.getGender());
            dto2.setFirstName(dto.getFirstName());
            dto2.setExpirationDate(dto.getExpirationDate());
            dto2.setDateOfIssue(dto.getDateOfIssue());
            dto2.setBirthPlace(dto.getBirthPlace());
            dto2.setBirthDate(dto.getBirthDate());
            dto2.setAuthority(dto2.getAuthority());
        }
        passportRepository.save(passportMapper.convertToEntity(dto2));
    }

}
