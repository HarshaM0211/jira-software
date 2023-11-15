package com.mandark.jira.spi.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mandark.jira.commons.util.Values;



/**
 * Query object to search/filter results.
 */
public abstract class SearchQuery<T> {

    // Formats
    public static final String DATE_FORMAT_STR = "dd-MM-yyyy";

    // Keys
    public static final String KEY_QUERY = "q";
    public static final String KEY_IS_ACTIVE = "active";
    public static final String KEY_TYPE = "type";


    // Properties
    protected final Map<String, List<String>> criteria;

    protected final String query;


    // Constructors
    // ------------------------------------------------------------------------

    protected SearchQuery(final Map<String, List<String>> criteria) {
        super();

        // init
        this.criteria = Values.getMap(criteria);

        final String qryStr = this.getFirst(KEY_QUERY);
        this.query = Objects.isNull(qryStr) ? "" : qryStr.trim();
    }


    // Methods
    // ------------------------------------------------------------------------

    protected String getFirst(final String key) {
        return this.getFirst(key, Function.identity());
    }

    protected <R> R getFirst(final String key, final Function<String, R> mapperFunction) {
        // Values
        final List<String> values = criteria.get(key);
        final String valStr = (Objects.nonNull(values) && !values.isEmpty() ? values.get(0) : null);
        if (Objects.isNull(valStr) || valStr.isBlank()) {
            return null;
        }

        return mapperFunction.apply(valStr);
    }


    protected List<String> getList(final String key) {
        return this.getList(key, Function.identity());
    }

    protected <R> List<R> getList(final String key, final Function<String, R> mapperFunction) {
        // Values
        final List<String> values = criteria.get(key);
        if (Objects.isNull(values) || values.isEmpty()) {
            return new ArrayList<>();
        }

        final List<R> resultList = values.stream() //
                .filter(Objects::nonNull) //
                .filter(s -> !s.isBlank()) //
                .map(mapperFunction) //
                .collect(Collectors.toList());

        return resultList;
    }


    // Getters and Setters
    // ------------------------------------------------------------------------

    public String getQuery() {
        return query;
    }


    // Object Methods
    // ------------------------------------------------------------------------

    @Override
    public String toString() {
        return "SearchQuery [query=" + query + ", criteria=" + criteria + "]";
    }

}
