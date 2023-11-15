package com.mandark.jira.spi.app.query;

import java.util.Objects;


/**
 * A {@link PropertyCriteria} criteria to match the Data Property value to be at least (min.) the
 * passed value.
 */
public class MinCriteria extends PropertyCriteria<Object> {


    // Constructor

    MinCriteria(String property, Object value) {
        super(property, value);

        assert Objects.nonNull(value) : "MinCriteria :: value is NULL";
    }


    // Object Methods

    @Override
    public String toString() {
        return "MinCriteria [property=" + property + ", value=" + value + "]";
    }


}
