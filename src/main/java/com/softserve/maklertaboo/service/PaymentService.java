package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation of payment for apartments.
 *
 * @author Roman Blavatskyi
 */
@Slf4j
@Service
public class PaymentService {

    private final FlatBookingService bookingService;

    /**
     * Constructor with parameters of {@link PaymentService}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public PaymentService(FlatBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Method that saves payment of apartments.
     *
     * @param requestId of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    public void payForApartment(Long requestId) {

        RequestForFlatBooking requestForFlatBooking
                = bookingService.getRequestForFlatBookingById(requestId);

        requestForFlatBooking.setIsPaid(true);

        bookingService.saveFlatBookingRequest(requestForFlatBooking);
    }
}
