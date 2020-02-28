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
    public List<PassportDto> findAll() {
        return passportRepository.findAll()
                .stream()
                .map(passportMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
