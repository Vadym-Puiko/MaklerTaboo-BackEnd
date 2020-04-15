package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.repository.request.RequestBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Provides an interface to manage {@link RequestForFlatBooking} entity.
 *
 * @author Roman Blavatskyi
 */
public interface FlatBookingRepository
        extends RequestBaseRepository<RequestForFlatBooking> {

    /**
     * Find {@link RequestForFlatBooking} by authorId and flatId.
     *
     * @param authorId Long
     * @param flatId   Long
     * @return {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    Optional<RequestForFlatBooking> findRequestForFlatBookingByAuthor_IdAndFlat_Id(
            Long authorId, Long flatId);

    /**
     * Find {@link RequestForFlatBooking} by page.
     *
     * @param pageable {@link Pageable}
     * @param id       Long
     * @param status   {@link RequestForVerificationStatus}
     * @return {@link Page} of {@link RequestForFlatBooking} entities
     * @author Roman Blavatskyi
     */
    Page<RequestForFlatBooking> findAllByFlat_Owner_IdAndStatus(
            Pageable pageable,
            Long id,
            RequestForVerificationStatus status);

    /**
     * Find {@link RequestForFlatBooking} by request's id.
     *
     * @param id Long
     * @return {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    Optional<RequestForFlatBooking> findRequestForFlatBookingById(Long id);

    /**
     * Find {@link List<RequestForFlatBooking>} by author's id.
     *
     * @param id Long
     * @return {@link List<RequestForFlatBooking>}
     * @author Roman Blavatskyi
     */
    Optional<List<RequestForFlatBooking>> findAllByAuthor_Id(Long id);

    /**
     * Count number of {@link RequestForFlatBooking} by {@link RequestForVerificationStatus}.
     *
     * @param status {@link RequestForVerificationStatus}
     * @return amount of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    long countAllByStatus(RequestForVerificationStatus status);

    @Query("SELECT COUNT(f)" +
            " FROM RequestForFlatBooking f" +
            " WHERE  f.status='APPROVED' AND f.verificationDate between :start AND :end")
    long countAllApprovedRequestsByVerificationDateBetween(Date start, Date end);


    @Query("SELECT r FROM RequestForFlatBooking r " +
            "WHERE r.status='APPROVED'")
    List<RequestForFlatBooking> findAllApproved();
}
