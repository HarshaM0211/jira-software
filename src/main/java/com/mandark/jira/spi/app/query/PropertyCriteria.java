package com.mandark.jira.spi.app.query;

import java.util.Objects;


/**
 * Abstract definition of {@link Criteria} set for a specific property of Data.
 * 
 * @see CompoundCriteria
 */
public abstract class PropertyCriteria<V> implements Criteria {

    protected final String property;
    protected final V value;


    // Constructor

    protected PropertyCriteria(String property, V value) {
        super();

        assert Objects.nonNull(property) && !property.isBlank() : "PropertyCriteria :: Property is BLANK";

        // init
        this.property = property.trim();
        this.value = value;
    }


    // Getters and Setters

    /**
     * Name of the property for which the criteria is setup.
     * 
     * @return name of the property.
     */
    public String getProperty() {
        return property;
    }

    /**
     * Value(s) of the property for which the criteria is setup
     * 
     * @return value(s) of the property
     */
    public V getValue() {
        return value;
    }


    // Object Methods

    @Override
    public String toString() {
        return "PropertyCriteria [property=" + property + ", value=" + value + "]";
    }

}