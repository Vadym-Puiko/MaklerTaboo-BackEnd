package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FlatFullTextSearch {

    private EntityManager entityManager;

    @Autowired
    private  FlatSearchRepository flatSearchRepository;

    @Autowired
    public FlatFullTextSearch(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Flat> search(String text, Pageable pageable, FlatSearchParameters searchParameters) {

        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Flat.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .range()
                        .onField("starred")
                        .from(0).to(3).
                        keyword()
                        .onFields("description", "title", "district")
                        .matching(text)
                        .createQuery()

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Flat.class);

        @SuppressWarnings("unchecked")
        List<Flat> results = jpaQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<Flat>(results, pageable, results.size());
    }
}
