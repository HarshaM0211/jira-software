package com.mandark.jira.spi.io.file;

import java.util.Objects;


public enum FileType {

    ARCHIVE,


    // Media

    AUDIO,

    IMAGE,

    VIDEO,


    // installation

    EXECUTABLE,


    // Text

    PDF,

    PLAIN_TEXT,


    // Office Suite

    DOCUMENT,

    PRESENTATION,

    SPREADSHEET,


    UNKNOWN;


    // Content Types
    // ------------------------------------------------------------------------

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CONTENT_TYPE_PDF = "application/pdf";

    public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";

    public static final String CONTENT_TYPE_JPG = "image/jpeg";


    // Util Methods
    // ------------------------------------------------------------------------

    public String asFolderName() {
        switch (this) {
            case ARCHIVE:
                return "archives";
            case AUDIO:
                return "audio";
            case IMAGE:
                return "images";
            case VIDEO:
                return "videos";
            case EXECUTABLE:
                return "executables";
            case PDF:
            case PLAIN_TEXT:
            case DOCUMENT:
            case PRESENTATION:
            case SPREADSHEET:
                return "documents";
            default:
                return "other-files";
        }
    }


    // Static methods
    // ------------------------------------------------------------------------

    public static FileType fromContentType(String inContentType) {
        // Sanity checks
        if (Objects.isNull(inContentType) || inContentType.isBlank()) {
            return FileType.UNKNOWN;
        }

        final String contentType = inContentType.toLowerCase().trim();

        if (contentType.startsWith("image")) {
            return FileType.IMAGE;
        }

        if (contentType.startsWith("video")) {
            return FileType.VIDEO;
        }

        if (contentType.startsWith("audio")) {
            return FileType.AUDIO;
        }

        if (contentType.startsWith("text")) {
            return FileType.PLAIN_TEXT;
        }

        if (contentType.equals(CONTENT_TYPE_PDF)) {
            return FileType.PDF;
        }

        if (contentType.startsWith("application")) {
            // Don't know
        }

        return FileType.UNKNOWN;
    }

}
