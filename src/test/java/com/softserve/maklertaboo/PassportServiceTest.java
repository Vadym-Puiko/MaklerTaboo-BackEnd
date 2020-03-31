package com.softserve.maklertaboo;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.DuplicateLandlordRequest;
import com.softserve.maklertaboo.exception.exceptions.DuplicateRenterRequest;
import com.softserve.maklertaboo.mapping.PassportMapper;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.PassportService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
public class PassportServiceTest {

    @Autowired
    PassportService passportService;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    PassportMapper passportMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

   @Test()
   public void getPassport() {
       Passport passport = passportRepository.findById((long) 1).get();
       PassportDto passportDto = passportMapper.convertToDto(passport);
       assertEquals(passportDto.getIdentificationNumber(),passportService.getPassport((long) 1).getIdentificationNumber());
   }
    @Test
    public void getPassportNotNull() {
        PassportDto passportDto = passportService.getPassport((long) 2);
        Assert.notNull(passportDto);
   }
    @Test
    public void  getRenterAdminApproval(){
        User user = userRepository.findById((long) 1).get();
        UserDto userDto = userMapper.convertToDto(user);
       Assertions.assertThrows(DuplicateRenterRequest.class, () ->
        { passportService.getRenterAdminApproval(userDto);});
    }
    @Test
    public void  getLandlordAdminApproval(){
        User user = userRepository.findById((long) 1).get();
        UserDto userDto = userMapper.convertToDto(user);
        Assertions.assertThrows(DuplicateLandlordRequest.class, () ->
        { passportService.getLandlordAdminApproval(userDto);});
    }
}
