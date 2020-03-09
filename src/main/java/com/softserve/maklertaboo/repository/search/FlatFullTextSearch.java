package com.softserve.maklertaboo.repository.search;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
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

        Query query = generateIntegerQuery(queryBuilder, flatSearchParameters);

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Flat.class);

        @SuppressWarnings("unchecked")
        List<Flat> results = jpaQuery
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<Flat>(results, pageable, results.size());
    }

    private BooleanQuery generateIntegerQuery(QueryBuilder queryBuilder, FlatSearchParameters params){
        NumericRangeQuery<Integer> monthPriceQuery = NumericRangeQuery.newIntRange("monthPrice",
                params.getPriceLow(),
                params.getPriceHigh(), true, true);

        NumericRangeQuery<Integer> numberOfRoomsQuery = NumericRangeQuery.newIntRange("numberOfRooms",
                params.getMinNumberOfRooms(),
                params.getMaxNumberOfRooms(), true, true);

        NumericRangeQuery<Integer> floorQuery = NumericRangeQuery.newIntRange("floor",
                params.getFloorLow(),
                params.getFloorHigh(), true, true);


        BooleanQuery luceneBooleanQuery = new BooleanQuery();
        luceneBooleanQuery.add(monthPriceQuery, BooleanClause.Occur.MUST);
        luceneBooleanQuery.add(numberOfRoomsQuery, BooleanClause.Occur.MUST);
        luceneBooleanQuery.add(floorQuery, BooleanClause.Occur.MUST);
        if(generateTextQuery(queryBuilder,params)!=null) {
            luceneBooleanQuery.add(generateTextQuery(queryBuilder, params), BooleanClause.Occur.MUST);
        }

        return luceneBooleanQuery;
    }

    private Query generateTextQuery(QueryBuilder queryBuilder, FlatSearchParameters params) {

        String searchText = StringUtils.join(params.getSearchText());
        BooleanJunction booleanJunction = queryBuilder.bool();

        if (searchText.length() > 3) {
            booleanJunction
                    .should(queryBuilder
                            .keyword()
                            .onFields("description","district","title")
                            .matching(searchText)
                            .createQuery()
                    );
        }
        if (params.getRegions() != null && params.getRegions().size() > 0) {
            booleanJunction
                    .must(queryBuilder
                            .keyword()
                            .onField("district")
                            .matching(params.getRegions().toString())
                            .createQuery()
                    );
        }

        if (params.getTags() != null && params.getTags().size() > 0) {
            booleanJunction
                    .must(queryBuilder
                            .keyword()
                            .onField("tags.name").ignoreFieldBridge()
                            .matching(params.getTags().toString().replaceAll(",", " "))
                            .createQuery()
                    );
        }
        if(booleanJunction.isEmpty()){
            return null;
        }
        return booleanJunction.createQuery();
    }
}
