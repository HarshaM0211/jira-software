package com.mandark.jira.spi.io.file;

import java.util.Objects;


/**
 * Data Formats of data.
 */
public enum DataFormat {
    // Excel
    XLSX,

    // Comma separated values
    CSV,

    // Simple JSON
    JSON,

    // file extensions - "geojson"
    GEOJSON;


    // Static Methods
    // ------------------------------------------------------------------------

    /**
     * Get {@link DataFormat} from String value
     */
    public static DataFormat from(final String dataFormatStr, final DataFormat defaultFormat) {
        // Sanity checks
        if (Objects.isNull(dataFormatStr) || dataFormatStr.isBlank()) {
            return defaultFormat;
        }

        return DataFormat.valueOf(dataFormatStr.trim().toUpperCase());
    }


}
