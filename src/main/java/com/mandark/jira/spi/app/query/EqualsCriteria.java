package com.mandark.jira.spi.app.query;

import java.util.Objects;


/**
 * A {@link PropertyCriteria} criteria to match the Data Property value with the passed value.
 */
public class EqualsCriteria extends PropertyCriteria<Object> {

    private final boolean ignoreCase;


    // Constructor

    EqualsCriteria(String property, Object value, boolean ignoreCase) {
        super(property, value);

        assert Objects.nonNull(value) : "EqualsCriteria :: value is NULL";

        this.ignoreCase = ignoreCase;
    }


    // Getters and Setters

    public boolean isIgnoreCase() {
        return ignoreCase;
    }


    // Object Methods

    @Override
    public String toString() {
        return "EqualsCriteria [property=" + property + ", value=" + value + ", ignoreCase=" + ignoreCase + "]";
    }


}
