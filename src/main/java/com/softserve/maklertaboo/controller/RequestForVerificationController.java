package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.dto.request.RequestForUserDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import com.softserve.maklertaboo.mapping.request.RequestForFlatMapper;
import com.softserve.maklertaboo.mapping.request.RequestForUserMapper;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class RequestForVerificationController {

    RequestForVerificationService requestForVerificationService;
    RequestForFlatMapper requestForFlatMapper;
    RequestForUserMapper requestForUserMapper;

    public RequestForVerificationController(RequestForVerificationService requestForVerificationService,
                                            RequestForFlatMapper requestForFlatMapper,
                                            RequestForUserMapper requestForUserMapper) {
        this.requestForVerificationService = requestForVerificationService;
        this.requestForFlatMapper = requestForFlatMapper;
        this.requestForUserMapper = requestForUserMapper;
    }

    @GetMapping("/requests/flats")
    public List<RequestForFlatDto> getRequestsForFlats() {
        return requestForVerificationService
                .getAllRequestsForFlatVerification().stream()
                .map(requestForFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/users/renters")
    public List<RequestForUserDto> getRequestsForRenters() {
        return requestForVerificationService
                .getAllRequestsForRenterVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/users/landlords")
    public List<RequestForUserDto> getRequestsForLandlords() {
        return requestForVerificationService
                .getAllRequestsForLandlordVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/users/moderators")
    public List<RequestForUserDto> getRequestsForModerators() {
        return requestForVerificationService
                .getAllRequestsForModeratorVerification().stream()
                .map(requestForUserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/requests/create/renter")
    public void createRenterRequest(@RequestBody RequestForUserDto requestDto) {
        requestForVerificationService.createRequestForUserVerification(requestDto, RequestForVerificationType.RENTER);
    }

    @PostMapping("/requests/create/landlord")
    public void createLandlordRequest(@RequestBody RequestForUserDto requestDto) {
        requestForVerificationService.createRequestForUserVerification(requestDto, RequestForVerificationType.LANDLORD);
    }

    @PostMapping("/requests/create/moderator")
    public void createModeratorRequest(@RequestBody RequestForUserDto requestDto) {
        requestForVerificationService.createRequestForUserVerification(requestDto, RequestForVerificationType.MODERATOR);
    }

    @PostMapping("/requests/create/flat")
    public void createFlatRequest(@RequestBody RequestForFlatDto requestDto) {
        requestForVerificationService.createRequestForFlatVerification(requestDto);
    }

    @PutMapping("requests/flats/{id}/approve")
    public void approveRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.approveFlatRequest(id);
    }

    @PutMapping("requests/flats/{id}/decline")
    public void declineRequestForFlat(@PathVariable Long id) {
        requestForVerificationService.declineFlatRequest(id);
    }

    @PutMapping("requests/users/{id}/approve")
    public void approveRequestForUser(@PathVariable Long id) {
        requestForVerificationService.approveUserRequest(id);
    }

    @PutMapping("requests/users/{id}/decline")
    public void declineRequestForUser(@PathVariable Long id) {
        requestForVerificationService.declineUserRequest(id);
    }

}
