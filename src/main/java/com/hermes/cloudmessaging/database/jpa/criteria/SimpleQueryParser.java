package com.hermes.cloudmessaging.database.jpa.criteria;

import com.hermes.cloudmessaging.database.jpa.criteria.constants.Appender;
import com.hermes.cloudmessaging.database.jpa.criteria.constants.Operation;
import com.hermes.cloudmessaging.database.jpa.criteria.dto.SearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SimpleQueryParser {

    /**
     * This function parses the string query into {@link SearchCriteria}.
     * Expected format :
     * val1 <= 23 $and val2 = 'ABC' $or val3 < 22
     *
     * @param query sent by the user
     * @return List of {@link SearchCriteria}
     */
    public static List<SearchCriteria> parseQuery(String query) {
        Assert.notNull(query, "Query is null");

        Map<Appender, List<String>> appenderListMap = parseForAppender(query);

        Assert.notEmpty(appenderListMap, "Invalid query " + query + " :: has missing $or or $and");

        log.debug("appenderListMap generated {}", appenderListMap);

        List<SearchCriteria> searchCriterias = new ArrayList<>();

        appenderListMap.forEach((appender, tokens) -> {
            for (String token : tokens) {
                String[] elem = validateAndSplitToken(token);
                log.debug("Tokenized element generated {}", Arrays.asList(elem));

                Operation operation = Operation.getOperationForString(elem[1]);

                log.debug("Operation {} ", operation);

                Assert.notNull(operation, "Invalid operation  " + token);

                String value = elem[2];
                SearchCriteria.ValueType valueType = SearchCriteria.getSearchCriteriaForValue(value);

                log.debug("ValueType {} ", valueType);

                if (valueType == SearchCriteria.ValueType.String) {
                    value = value.substring(1, value.lastIndexOf("'"));
                }

                searchCriterias.add(SearchCriteria.of(elem[0], value, operation, appender, valueType));
            }
        });


        return searchCriterias;
    }

    public static Criteria mapSearchCriteriaToCriteria(SearchCriteria searchCriteria) {

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

    private static String[] validateAndSplitToken(String token) {
        String[] rhsSplicedArr = removeExtraSpacesAndSplitRHS(token);

        String[] elem = rhsSplicedArr[0].split("\\s+");
        Assert.isTrue((elem.length == 2 && !rhsSplicedArr[1].isEmpty()) ||
                (elem.length == 3), "Invalid query " + token);

        String lhs = elem[0];
        String op = elem[1];
        String rhs = (elem.length == 3)? elem[2] : rhsSplicedArr[1];

        Assert.notNull(lhs, "LHS is null");
        Assert.notNull(op, "operation is null");
        Assert.notNull(rhs, "RHS is null");
        lhs = lhs.trim();
        op = op.trim();
        rhs = rhs.trim();

        log.debug("LHS = {}, OP = {}, RHS = {}", lhs, op, rhs);

        return new String[]{lhs, op, rhs};
    }

    private static String[] removeExtraSpacesAndSplitRHS(String token) {
        token = token.trim();
        String RHS = "";
        String LHSAndOperation = token;

        if (token.contains("'")) {
            int fIndex = token.indexOf("'");
            String tempRhs = token.substring(fIndex + 1);
            if (tempRhs.contains("'")) {
                RHS = "'" + tempRhs;
                LHSAndOperation = token.substring(0, fIndex);
            }
        }
        String[] equation = new String[]{LHSAndOperation.trim().replace(" +", " "), RHS};

        log.debug("equation {}", Arrays.asList(equation));
        return equation;
    }

    private static Map<Appender, List<String>> parseForAppender(String query) {
        Map<Appender, List<String>> tokens = new HashMap<>();

        String escapedOr = Appender.getEscapedValue(Appender.OR);
        String escapedAnd = Appender.getEscapedValue(Appender.AND);
        String[] splits = query.split(escapedAnd);

        Map<Boolean, List<String>> partitionedTokens =
                Arrays.stream(splits).collect(Collectors.partitioningBy(s -> s.contains(Appender.OR.getValue())));

        if (partitionedTokens.get(false) != null || !partitionedTokens.get(false).isEmpty()) {
            tokens.put(Appender.AND, partitionedTokens.get(false));
        }
        List<String> orTokens = partitionedTokens.get(true).stream().map(s -> Arrays.asList(s.split(escapedOr)))
                .reduce((strings, strings2) -> {
                    strings.addAll(strings2);
                    return strings;
                }).orElse(new ArrayList<>());
        if (!orTokens.isEmpty()) {
            tokens.put(Appender.OR, orTokens);
        }

        return tokens;
    }
}


