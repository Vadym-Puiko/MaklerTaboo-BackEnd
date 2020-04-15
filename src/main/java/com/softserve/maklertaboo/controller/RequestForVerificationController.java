package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestForVerificationController {

    RequestForVerificationService requestForVerificationService;
    RequestForFlatMapper requestForFlatMapper;
    RequestForUserMapper requestForUserMapper;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public RequestForVerificationController(RequestForVerificationService requestForVerificationService,
                                            RequestForFlatMapper requestForFlatMapper,
                                            RequestForUserMapper requestForUserMapper,
                                            JWTTokenProvider jwtTokenProvider) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(path = "/flats", params = {"page", "size", "status"})
    public Page<RequestForFlatDto> getRequestsForFlats(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                       @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {
        return requestForVerificationService.getRequestsForFlatVerification(page, size, status)
                .map(requestForFlatMapper::convertToDto);
    }

    @GetMapping(path = "/renters", params = {"page", "size", "status"})
    public Page<RequestForUserDto> getRequestsForRenters(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                         @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {
        return requestForVerificationService.getRequestsForRenterVerification(page, size, status)
                .map(requestForUserMapper::convertToDto);
    }

    @GetMapping(path = "/landlords", params = {"page", "size", "status"})
    public Page<RequestForUserDto> getRequestsForLandlords(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                           @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {
        return requestForVerificationService.getRequestsForLandlordVerification(page, size, status)
                .map(requestForUserMapper::convertToDto);
    }

    @GetMapping(path = "/moderators", params = {"page", "size", "status"})
    public Page<RequestForUserDto> getRequestsForModerators(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                            @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {
        return requestForVerificationService.getRequestsForModeratorVerification(page, size, status)
                .map(requestForUserMapper::convertToDto);
    }

    @PutMapping("/flats/{id}/approve")
    public void approveRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.approveFlatRequest(id);
    }

    @PutMapping("/flats/{id}/decline")
    public void declineRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.declineFlatRequest(id);
    }

    @PutMapping("/users/{id}/approve")
    public void approveRequestForUser(@PathVariable Long id) {
        requestForVerificationService.approveUserRequest(id);
    }

    @PutMapping("/users/{id}/decline")
    public void declineRequestForUser(@PathVariable Long id) {
        requestForVerificationService.declineUserRequest(id);
    }

    @PutMapping("/review/flat/{id}")
    public void reviewFlatRequest(@PathVariable Long id) {
        requestForVerificationService.reviewFlatRequest(id);
    }

    @PutMapping("/review/user/{id}")
    public void reviewUserRequest(@PathVariable Long id) {
        requestForVerificationService.reviewUserRequest(id);
    }

    @GetMapping("/new")
    public Long getCountOfNewRequests() {
        return requestForVerificationService.getCountOfNewRequests(RequestForVerificationStatus.NEW);
    }
}
