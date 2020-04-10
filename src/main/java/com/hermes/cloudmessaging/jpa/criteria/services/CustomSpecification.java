package com.hermes.cloudmessaging.jpa.criteria.services;

import com.hermes.cloudmessaging.exception.BaseRuntimeException;
import com.hermes.cloudmessaging.jpa.criteria.constants.Operation;
import com.hermes.cloudmessaging.jpa.criteria.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomSpecification<T> implements Specification<T> {

    private List<SearchCriteria> searchCriterion;

    public CustomSpecification() {
        this.searchCriterion = new ArrayList<>();
    }

    public List<SearchCriteria> add(SearchCriteria searchCriteria) {
        searchCriterion.add(searchCriteria);
        return searchCriterion;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = null;
        for (SearchCriteria criteria : searchCriterion) {
            predicate = buildCriteria(root, criteriaBuilder, criteria);
        }
        return predicate;
    }

    private Predicate buildCriteria(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
        switch (criteria.getAppender()) {
            case OR:
                return criteriaBuilder.or(getPredicate(root, criteriaBuilder, criteria));
            case AND:
                return criteriaBuilder.and(getPredicate(root, criteriaBuilder, criteria));
            default:
                throw new BaseRuntimeException("Invalid predicate", HttpStatus.BAD_REQUEST);
        }
    }

    private Predicate getPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchCriteria criteria) {
        Operation operation = criteria.getOperation();

        switch (operation) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue());
            case LESS_THAN_EQUAL_TO:
                return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN_EQUAL_TO:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue());
            case LIKE:
                return criteriaBuilder.like(root.get(criteria.getKey()), "%" +criteria.getValue()+ "%");
            default:
                throw new BaseRuntimeException("Invalid predicate", HttpStatus.BAD_REQUEST);
        }
    }


}
