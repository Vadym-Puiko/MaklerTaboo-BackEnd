package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.entity.Passport;
import com.softserve.maklertaboo.repository.passport.PassportRepository;
import com.sun.istack.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin
@RestController
public class PassportController {
   Logger logger=Logger.getLogger(PassportController.class);
    @Autowired
    PassportRepository passportRepository;

    @GetMapping("/passport")
    public void  getAllPassport(){
      List<Passport> passportList= passportRepository.findAll();
        logger.info(String.valueOf(passportList));
    }
}
