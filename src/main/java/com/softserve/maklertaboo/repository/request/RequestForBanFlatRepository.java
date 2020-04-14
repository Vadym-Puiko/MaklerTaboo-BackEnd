package com.softserve.maklertaboo.repository.request;

import com.softserve.maklertaboo.entity.request.RequestForBanFlat;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides an interface to manage {@link RequestForBanFlat} entity.
 */
@Repository
public interface RequestForBanFlatRepository extends JpaRepository<RequestForBanFlat, Long> {

    /**
     * Find {@link RequestForBanFlat} by page.
     *
     * @param pageable {@link Pageable}.
     * @param statusForVerification {@link String}.
     * @return {@link Page}.
     * @author Vadym Puiko
     */
    Page<RequestForBanFlat> findAllByStatusForVerificationLike(
            Pageable pageable, String statusForVerification);

    /**
     * Find {@link RequestForBanFlat} by id.
     *
     * @param id {@link Long}.
     * @return {@link RequestForBanFlat}.
     * @author Vadym Puiko
     */
    Optional<RequestForBanFlat> findById(Long id);


}
