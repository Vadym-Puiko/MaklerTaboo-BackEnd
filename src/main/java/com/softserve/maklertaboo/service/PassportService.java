package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.enums.GenderType;
import com.softserve.maklertaboo.entity.enums.PassportType;
import com.softserve.maklertaboo.entity.enums.UserRole;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.DuplicateLandlordRequest;
import com.softserve.maklertaboo.exception.exceptions.DuplicateRenterRequest;
import com.softserve.maklertaboo.mapping.PassportMapper;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {

    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;
    private final UserRepository userRepository;
    private final RequestForVerificationService requestForVerificationService;

    @Autowired
    public PassportService(PassportRepository passportRepository, PassportMapper passportMapper, UserRepository userRepository, RequestForVerificationService requestForVerificationService) {
        this.passportRepository = passportRepository;
        this.passportMapper = passportMapper;
        this.userRepository = userRepository;
        this.requestForVerificationService = requestForVerificationService;

    }

    public PassportDto getPassport(Long id) {
        Passport passport;
        User user = userRepository.findById(id).get();
        passport = user.getPassport();
        if (passport == null) {
            passport = new Passport();
        }
        return passportMapper.convertToDto(passport);
    }

    public void updatePassport(UserDto userDto, PassportDto passportDto) {
        Passport passport;
        User user = userRepository.findById(userDto.getId()).get();

        passport = user.getPassport();
        if (passport == null) {
            passport = new Passport();
        }

        passport.setPassportType(PassportType.valueOf(passportDto.getPassportType()));
        passport.setPassportNumber(passportDto.getPassportNumber());
        passport.setNationality(passportDto.getNationality());
        passport.setMiddleName(passportDto.getMiddleName());
        passport.setLastName(passportDto.getLastName());
        passport.setIdentificationNumber(passportDto.getIdentificationNumber());
        passport.setGender(GenderType.valueOf(passportDto.getGender()));
        passport.setFirstName(passportDto.getFirstName());
        passport.setExpirationDate(passportDto.getExpirationDate());
        passport.setDateOfIssue(passportDto.getDateOfIssue());
        passport.setBirthPlace(passportDto.getBirthPlace());
        passport.setBirthDate(passportDto.getBirthDate());
        passport.setAuthority(passportDto.getAuthority());

        passportRepository.save(passport);
        user.setPassport(passport);
        userRepository.save(user);

        getRenterAdminApproval(userDto);
    }

    public void getRenterAdminApproval(UserDto userDto) {
        if (UserRole.valueOf(userDto.getUserRole()) != UserRole.ROLE_RENTER) {
            requestForVerificationService.createRenterRequest(userRepository.findById(userDto.getId()).get());
            throw new DuplicateRenterRequest(ErrorMessage.DUPLICATE_RENTER_REQUEST);
        }
    }

    public void getLandlordAdminApproval(UserDto userDto) {
        if (UserRole.valueOf(userDto.getUserRole()) != UserRole.ROLE_LANDLORD) {
            requestForVerificationService.createLandlordRequest(userRepository.findById(userDto.getId()).get());
            throw new DuplicateLandlordRequest(ErrorMessage.DUPLICATE_LANDLORD_REQUEST);
        }
    }

}