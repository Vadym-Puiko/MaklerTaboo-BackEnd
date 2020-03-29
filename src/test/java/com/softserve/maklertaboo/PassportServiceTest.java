package com.softserve.maklertaboo;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.mapping.PassportMapper;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.PassportService;
import io.jsonwebtoken.lang.Assert;
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
       Passport passport = passportRepository.findById((long) 2).get();
       PassportDto passportDto = passportMapper.convertToDto(passport);
       assertEquals(passportDto.getIdentificationNumber(),passportService.getPassport((long) 1).getIdentificationNumber());
   }
    @Test
    public void getPassportNotNull() {
        PassportDto passportDto = passportService.getPassport((long) 5);
        Assert.notNull(passportDto);
   }

}
