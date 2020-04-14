package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.request.RequestForFlatDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.service.FlatBookingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link RequestForFlatBooking} entity.
 *
 * @author Roman Blavatskyi
 */
@RestController
@RequestMapping("/booking")
public class FlatBookingController {

    private final FlatBookingService flatBookingService;

    /**
     * Constructor with parameters of {@link FlatBookingController}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public FlatBookingController(FlatBookingService flatBookingService) {

        this.flatBookingService = flatBookingService;
    }


    /**
     * Method that creates new request of flat booking.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Create request for flat booking")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PostMapping("/flat")
    public void createRequestForFlatBooking(@RequestBody Long id) {

        flatBookingService.createRequestForFlatBooking(id);
    }

    /**
     * Method that finds all requests of flat booking for Landlord by page.
     *
     * @param page   Integer
     * @param size   Integer
     * @param status {@link RequestForVerificationStatus}
     * @return {@link Page} of {@link List<RequestForFlatDto>}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Get page of flat booking requests for Landlord")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping(value = "/get-requests/landlord", params = {"page", "size", "status"})
    public ResponseEntity<Page<RequestForFlatDto>> getLandlordRequests(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "status", defaultValue = "NEW") RequestForVerificationStatus status) {

        return ResponseEntity.status(HttpStatus.OK).body(
                flatBookingService.getLandlordRequests(page, size, status));
    }

    /**
     * Method that finds all requests of flat booking for Renter.
     *
     * @return {@link ResponseEntity} of {@link List<RequestForFlatDto>}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Get list of flat booking requests of Renter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/get-requests/renter")
    public ResponseEntity<List<RequestForFlatDto>> getRenterRequests() {

        return ResponseEntity.status(HttpStatus.OK).body(
                flatBookingService.getRenterRequests());
    }

    /**
     * Method that approves request of flat booking.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Approve flat booking request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/flat/{id}/approve")
    public void approveRequestForFlatBooking(@PathVariable Long id) {
        flatBookingService.approveFlatRequest(id);
    }

    /**
     * Method that declines request of flat booking.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Decline flat booking request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/flat/{id}/decline")
    public void declineRequestForFlatBooking(@PathVariable Long id) {
        flatBookingService.declineFlatRequest(id);
    }

    /**
     * Method that reviews request of flat booking.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Review flat booking request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/flat/{id}/review")
    public void reviewFlatBookingRequest(@PathVariable Long id) {
        flatBookingService.reviewFlatRequest(id);
    }

    /**
     * Method that approves request of flat booking.
     *
     * @return amount of new requests of flat booking
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Get amount of new flat booking requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
    })
    @GetMapping("/new-requests")
    public Long getCountOfNewRequests() {
        return flatBookingService.getCountOfNewRequests(RequestForVerificationStatus.NEW);
    }
}
