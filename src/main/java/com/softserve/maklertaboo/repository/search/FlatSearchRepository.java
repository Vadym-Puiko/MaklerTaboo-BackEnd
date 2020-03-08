package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
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
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<Flat>(result, pageable, result.size());
    }

    private List<Predicate> getPredicates(FlatSearchParameters searchParameters, Root<Flat> flatRoot) {
        List<Predicate> predicates = new LinkedList<>();

        if (searchParameters.getPriceLow() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flatRoot.get("monthPrice"),
                    searchParameters.getPriceLow()));
        }

        if (searchParameters.getPriceHigh() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flatRoot.get("monthPrice"),
                    searchParameters.getPriceHigh()));
        }

        if (searchParameters.getFloorLow() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flatRoot.get("floor"),
                    searchParameters.getFloorLow()));
        }

        if (searchParameters.getFloorHigh() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flatRoot.get("floor"),
                    searchParameters.getFloorHigh()));
        }

        if (searchParameters.getMinNumberOfRooms() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flatRoot.get("numberOfRooms"),
                    searchParameters.getMinNumberOfRooms()));
        }
        if (searchParameters.getMaxNumberOfRooms() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flatRoot.get("numberOfRooms"),
                    searchParameters.getMaxNumberOfRooms()));
        }

        SetJoin<Object, Object> flatTagJoin = flatRoot.joinSet("tags");

        if (searchParameters.getTags() != null) {
            predicates.add(flatTagJoin.get("name").in(searchParameters.getTags()));
        }

        if (searchParameters.getRegions() != null) {
            predicates.add(flatRoot.get("district").in(searchParameters.getRegions()));
        }
        return predicates;
    }
}
