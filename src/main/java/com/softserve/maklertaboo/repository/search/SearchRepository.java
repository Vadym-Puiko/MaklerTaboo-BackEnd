package com.softserve.maklertaboo.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepository<E, P> {
    Page<E> findByParams(P searchParameters, Pageable pageable);
}