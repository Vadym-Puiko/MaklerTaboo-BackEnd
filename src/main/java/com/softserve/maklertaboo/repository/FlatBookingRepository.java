package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FlatBookingRepository
        extends RequestBaseRepository<RequestForFlatBooking> {

    Optional<RequestForFlatBooking> findRequestForFlatBookingByAuthor_IdAndFlat_Id(
            Long authorId, Long flatId);

    Page<RequestForFlatBooking> findAllByFlat_Owner_IdAndStatus(Pageable pageable, Long id, RequestForVerificationStatus status);

    Optional<RequestForFlatBooking> findRequestForFlatBookingById(Long id);

    Optional<List<RequestForFlatBooking>> findAllByAuthor_Id(Long id);

    Long countAllByStatus(RequestForVerificationStatus status);
}
