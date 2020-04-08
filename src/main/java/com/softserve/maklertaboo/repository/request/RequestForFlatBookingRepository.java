package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;

import java.util.List;

public interface RequestForFlatBookingRepository
        extends RequestBaseRepository<RequestForFlatBooking> {

    List<RequestForFlatBooking> getAllByFlat_Owner_Email(String email);
}
