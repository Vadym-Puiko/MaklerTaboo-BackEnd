package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import org.apache.lucene.search.Query;
import org.apache.tomcat.util.buf.StringUtils;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.BooleanJunction;
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
    private FlatSearchRepository flatSearchRepository;

    @Autowired
    public FlatFullTextSearch(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<Flat> search(FlatSearchParameters flatSearchParameters, Pageable pageable) {

        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Flat.class).get();

        Query query = generateQuery(queryBuilder, flatSearchParameters);

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Flat.class);

        @SuppressWarnings("unchecked")
        List<Flat> results = jpaQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<Flat>(results, pageable, results.size());
    }

    private Query generateQuery(QueryBuilder queryBuilder, FlatSearchParameters params) {

        String searchText = StringUtils.join(params.getSearchText()).replaceAll(",", " ");
        System.out.println(params.toString());
        BooleanJunction booleanJunction = queryBuilder
                .bool()
                .should(
                        queryBuilder
                                .range()
                                .onField("monthPrice")
                                .from(params
                                        .getPriceLow())
                                .to(params
                                        .getFloorHigh())
                                .createQuery())
                .should(
                        queryBuilder
                                .range()
                                .onField("numberOfRooms")
                                .from(params
                                        .getMinNumberOfRooms())
                                .to(params
                                        .getMaxNumberOfRooms())
                                .createQuery())
                .should(
                        queryBuilder
                                .range()
                                .onField("floor")
                                .from(params
                                        .getFloorLow())
                                .to(params
                                        .getFloorHigh())
                                .createQuery());
        if (searchText.length() > 3) {
            booleanJunction
                    .must(queryBuilder
                            .phrase()
                            .onField("description")
                            .andField("district")
                            .andField("title")
                            .sentence(searchText)
                            .createQuery()
                    );
        }
        if (params.getRegions()!=null && params.getRegions().size()>0) {
            booleanJunction
                    .must(queryBuilder
                            .keyword()
                            .onField("district")
                            .matching(params.getRegions().toString())
                            .createQuery()
                    );
        }

        if (params.getTags()!=null && params.getTags().size()>0) {
            booleanJunction
                    .must(queryBuilder
                            .keyword()
                            .onField("tags.name").ignoreFieldBridge()
                            .matching(params.getTags().toString().replaceAll(",", " "))
                            .createQuery()
                    );
        }
        return booleanJunction.createQuery();
    }
}
