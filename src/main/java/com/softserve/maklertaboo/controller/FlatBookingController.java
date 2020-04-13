package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.service.FlatBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class FlatBookingController {

    private final FlatBookingService flatBookingService;

    @Autowired
    public FlatBookingController(FlatBookingService flatBookingService) {
        this.flatBookingService = flatBookingService;
    }

    @PostMapping("/flat")
    public void createRequestForFlatBooking(@RequestBody Long id) {

        flatBookingService.createRequestForFlatBooking(id);
    }

    @GetMapping(value = "/get-requests/landlord", params = {"page", "size", "status"})
    public ResponseEntity<Page<RequestForFlatDto>> getLandlordRequests(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {

        return ResponseEntity.status(HttpStatus.OK).body(
                flatBookingService.getLandlordRequests(page, size, status));
    }

    @GetMapping("/get-requests/renter")
    public ResponseEntity<List<RequestForFlatDto>> getRenterRequests() {

        return ResponseEntity.status(HttpStatus.OK).body(
                flatBookingService.getRenterRequests());
    }

    @PutMapping("/flat/{id}/approve")
    public void approveRequestForFlatBooking(@PathVariable Long id) {
        flatBookingService.approveFlatRequest(id);
    }

    @PutMapping("/flat/{id}/decline")
    public void declineRequestForFlatBooking(@PathVariable Long id) {
        flatBookingService.declineFlatRequest(id);
    }

    @PutMapping("/flat/{id}/review")
    public void reviewFlatBookingRequest(@PathVariable Long id) {
        flatBookingService.reviewFlatRequest(id);
    }

    @GetMapping("/new-requests")
    public Long getCountOfNewRequests() {
        return flatBookingService.getCountOfNewRequests(RequestForVerificationStatus.NEW);
    }
}
