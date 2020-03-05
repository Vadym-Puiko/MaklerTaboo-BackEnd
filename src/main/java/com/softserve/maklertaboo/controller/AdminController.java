package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForLandlordDto;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForLandlordMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    RequestForVerificationService requestForVerificationService;
    RequestForFlatMapper requestForFlatMapper;
    RequestForLandlordMapper requestForLandlordMapper;

    @Autowired
    public AdminController(RequestForVerificationService requestForVerificationService,
                           RequestForLandlordMapper requestForLandlordMapper,
                           RequestForFlatMapper requestForFlatMapper) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForLandlordMapper = requestForLandlordMapper;
    }

    @GetMapping("/requests/flat")
    public List<RequestForFlatDto> getRequestsForFlats() {
        return requestForVerificationService
                .getAllRequestsForFlatVerification().stream()
                .map(requestForFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/user")
    public List<RequestForLandlordDto> getRequestsForLandlords() {
        return requestForVerificationService
                .getAllRequestsForLandlordVerification().stream()
                .map(requestForLandlordMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("requests/flat/{id}/approve")
    public void approveRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.approveFlatRequest(id);
    }

    @PutMapping("requests/flat/{id}/decline")
    public void declineRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.declineFlatRequest(id);
    }

    @PutMapping("requests/user/{id}/approve")
    public void approveRequestForLandlord(@PathVariable Long id) {
        requestForVerificationService.approveLandlordRequest(id);
    }

    @PutMapping("requests/user/{id}/decline")
    public void declineRequestForLandlord(@PathVariable Long id) {
        requestForVerificationService.declineLandlordRequest(id);
    }


}
