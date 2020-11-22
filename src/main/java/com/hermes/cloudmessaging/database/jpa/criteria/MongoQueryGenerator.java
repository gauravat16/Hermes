package com.hermes.cloudmessaging.database.jpa.criteria;

import com.hermes.cloudmessaging.database.jpa.criteria.constants.Appender;
import com.hermes.cloudmessaging.database.jpa.criteria.constants.Operation;
import com.hermes.cloudmessaging.database.jpa.criteria.dto.SearchCriteria;
import com.hermes.cloudmessaging.core.utils.BeanUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        Criteria criteria = new Criteria();

        BeanUtils.getObjectPropertyValueMap(o).forEach((k, v) -> query.addCriteria(Criteria.where(k).is(v)));

        if (!StringUtils.isEmpty(strQuery)) {
            List<SearchCriteria> searchCriteriaList = SimpleQueryParser.parseQuery(strQuery);

            Map<Boolean, List<SearchCriteria>> criteriaPartition = searchCriteriaList.stream()
                    .collect(Collectors.partitioningBy(searchCriteria -> searchCriteria.getAppender().equals(Appender.OR)));

            if(criteriaPartition.containsKey(false) && !criteriaPartition.get(false).isEmpty()){
                List<Criteria> andCriteria = criteriaPartition.get(false).stream()
                        .map(SimpleQueryParser::mapSearchCriteriaToCriteria).collect(Collectors.toList());
                criteria.andOperator(andCriteria.toArray(new Criteria[0]));
            }

            if(criteriaPartition.containsKey(true) && !criteriaPartition.get(true).isEmpty()){
                List<Criteria> andCriteria = criteriaPartition.get(true).stream()
                        .map(SimpleQueryParser::mapSearchCriteriaToCriteria).collect(Collectors.toList());
                criteria.orOperator(andCriteria.toArray(new Criteria[0]));
            }
        }
        query.addCriteria(criteria);


        return query;
    }

}
