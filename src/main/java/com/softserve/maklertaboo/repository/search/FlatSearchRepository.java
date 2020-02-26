package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.dto.flat.FlatSearchParameters;
import com.softserve.maklertaboo.entity.Flat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class FlatSearchRepository implements SearchRepository<Flat, FlatSearchParameters> {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    public FlatSearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Page<Flat> findByParams(FlatSearchParameters searchParameters, Pageable pageable) {

        CriteriaQuery<Flat> criteriaQuery = criteriaBuilder.createQuery(Flat.class);
        Root<Flat> flatRoot = criteriaQuery.from(Flat.class);



        return new Page<>();
    }

}
