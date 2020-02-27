package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.dto.flat.FlatSearchParameters;
import com.softserve.maklertaboo.entity.Flat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

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

        List<Predicate> predicates = getPredicates(searchParameters, flatRoot);

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Flat> result = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<Flat>(result, pageable, result.size());
    }

    private List<Predicate> getPredicates(FlatSearchParameters searchParameters, Root<Flat> flatRoot) {
        List<Predicate> predicates = new LinkedList<>();

        if (searchParameters.getRegion() != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(flatRoot.get("district")), searchParameters.getRegion()));
        }

        if (searchParameters.getPriceLow() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flatRoot.get("monthPrice"), searchParameters.getPriceLow()));
        }

        if (searchParameters.getPriceHigh() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flatRoot.get("monthPrice"), searchParameters.getPriceHigh()));
        }

        if (searchParameters.getPriceHigh() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flatRoot.get("monthPrice"), searchParameters.getPriceHigh()));
        }

        if (searchParameters.getFloorLow() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flatRoot.get("monthPrice"), searchParameters.getFloorLow()));
        }

        if (searchParameters.getTags() != null) {
            for (String tag : searchParameters.getTags()) {
                predicates.add(criteriaBuilder.like(flatRoot.join("flat_tag_list", JoinType.INNER).get("name"), tag));
            }
        }

        return predicates;
    }


}
