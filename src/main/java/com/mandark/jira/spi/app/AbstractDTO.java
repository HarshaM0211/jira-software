package com.mandark.jira.spi.app;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mandark.jira.spi.lang.NotImplementedException;


/**
 * Abstract implementation of DTO definition for any Object.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDTO<T> {


    // Constructors
    // ------------------------------------------------------------------------

    public AbstractDTO(T t, Object... objs) {
        super();

        // Sanity checks
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("AbstractDTO :: DTO type argument is NULL");
        }
    }


    // Methods
    // ------------------------------------------------------------------------

    @JsonIgnore
    public Map<String, Object> lite() {
        final String clsName = this.getClass().getSimpleName();
        final String errMsg = String.format("AbstractDTO#lite() is not implemented for : %s", clsName);
        throw new NotImplementedException(errMsg);
    }


}
