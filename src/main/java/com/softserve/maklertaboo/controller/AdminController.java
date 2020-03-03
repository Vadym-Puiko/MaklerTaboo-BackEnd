package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    RequestForVerificationService requestForVerificationService;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService) {
        this.requestForVerificationService = requestForVerificationService;
    }

    @GetMapping("/requests/flat")
    public List<RequestForFlatVerification> getActiveRequestsForFlats() {
        return requestForVerificationService.getAllRequestsForFlatVerification();
    }


}
