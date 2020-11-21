package com.hermes.cloudmessaging.jpa.criteria;

import com.hermes.cloudmessaging.jpa.criteria.constants.Appender;
import com.hermes.cloudmessaging.jpa.criteria.constants.Operation;
import com.hermes.cloudmessaging.jpa.criteria.dto.SearchCriteria;
import com.hermes.cloudmessaging.utils.BeanUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generates an Query for Object and string query.
 *
 * @author gaurav
 */
@UtilityClass
@Log4j2
public class MongoQueryGenerator {

    public Query generateQuery(Object o, String strQuery) {

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        BeanUtils.getObjectPropertyValueMap(o).forEach((k, v) -> criteriaList.add(Criteria.where(k).is(v)));

        if (!StringUtils.isEmpty(strQuery)) {
            parseQuery(strQuery).forEach(searchCriteria -> criteriaList.add(mapSearchCriteriaToCriteria(searchCriteria)));
        }

        return query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
    }

    /**
     * This function parses the string query into {@link SearchCriteria}.
     * Expected format :
     * val1,=,23;val2,=,ABC;val3,<,22
     *
     * @param query sent by the user
     * @return List of {@link SearchCriteria}
     */
    private List<SearchCriteria> parseQuery(String query) {
        Assert.notNull(query, "Query is null");

        String[] tokens = query.split(";");
        Assert.notEmpty(tokens, "Invalid query " + query + " :: has missing ';'");

        List<SearchCriteria> searchCriterias = new ArrayList<>();

        for (String token : tokens) {
            String[] elem = validateAndSplitToken(token, ",");

            Operation operation = Operation.getOperationForString(elem[1]);
            Assert.notNull(operation, "Invalid operation  " + token);

            String value = elem[2];
            SearchCriteria.ValueType valueType = SearchCriteria.getSearchCriteriaForValue(value);
            if (valueType == SearchCriteria.ValueType.String) {
                value = value.substring(1, value.lastIndexOf("'"));
            }

            searchCriterias.add(SearchCriteria.of(elem[0], value, operation, Appender.AND, valueType));
        }

        return searchCriterias;
    }

    private Criteria mapSearchCriteriaToCriteria(SearchCriteria searchCriteria) {

        switch (searchCriteria.getOperation()) {
            case LIKE:
                return Criteria.where(searchCriteria.getKey()).regex((String) searchCriteria.getValue());
            case EQUALS:
                return Criteria.where(searchCriteria.getKey()).is(searchCriteria.getValue());
            case LESS_THAN:
                return Criteria.where(searchCriteria.getKey()).lt(searchCriteria.getValue());
            case GREATER_THAN:
                return Criteria.where(searchCriteria.getKey()).gt(searchCriteria.getValue());
            case LESS_THAN_EQUAL_TO:
                return Criteria.where(searchCriteria.getKey()).lte(searchCriteria.getValue());
            case GREATER_THAN_EQUAL_TO:
                return Criteria.where(searchCriteria.getKey()).gte(searchCriteria.getValue());
        }

        throw new IllegalArgumentException("Invalid operation");
    }

    private String[] validateAndSplitToken(String token, String delim) {
        String[] elem = token.split(delim);

        Assert.isTrue(elem.length == 3, "Invalid query " + token);
        Assert.notNull(elem[0], "LHS is null");
        Assert.notNull(elem[1], "operation is null");
        Assert.notNull(elem[2], "RHS is null");
        elem[0] = elem[0].trim();
        elem[1] = elem[1].trim();
        elem[2] = elem[2].trim();

        return elem;
    }
}
