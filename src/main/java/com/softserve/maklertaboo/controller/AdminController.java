package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.service.RequestForVerificationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    RequestForVerificationService requestForVerificationService;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService) {
        this.requestForVerificationService = requestForVerificationService;
    }




}
