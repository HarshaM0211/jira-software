package com.mandark.jira.spi.app.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.mandark.jira.spi.lang.ValidationException;


/**
 * Class to provide a way to sort the entities
 */
public final class OrderBy {

    private final boolean isAsc;
    private final List<String> properties;


    // Constructors
    // ------------------------------------------------------------------------

    public OrderBy(List<String> properties) {
        this(false, properties);
    }

    public OrderBy(boolean isAsc, List<String> properties) {
        this.properties = Objects.isNull(properties) ? new ArrayList<>() : new ArrayList<>(properties);
        this.isAsc = isAsc;
    }

    public OrderBy(String... properties) {
        this(false, properties);
    }

    public OrderBy(boolean isAsc, String... properties) {
        // Sanity check
        if (Objects.isNull(properties)) {
            throw new ValidationException("#OrderBy :: properties object is NULL");
        }

        this.properties = Arrays.asList(properties);
        this.isAsc = isAsc;
    }


    // Getters and Setters
    // ------------------------------------------------------------------------

    public List<String> getProperties() {
        return Collections.unmodifiableList(this.properties);
    }

    public boolean isAsc() {
        return isAsc;
    }

}
