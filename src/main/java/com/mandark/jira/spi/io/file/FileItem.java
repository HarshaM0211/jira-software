package com.mandark.jira.spi.io.file;

import java.io.File;
import java.util.Objects;

import com.mandark.jira.commons.util.FileUtil;


/**
 * Simple POJO implementation of {@link IFileItem}
 */
public class FileItem implements IFileItem {

    private FileType type;

    private String name;
    private String contentType;

    private byte[] bytes;

    private String url;


    // Constructors
    // ------------------------------------------------------------------------

    public FileItem() {
        super();
        // Default Constructor
    }

    public FileItem(final FileType type, final File file) {
        super();

        // init
        this.type = type;
        this.name = file.getName();
        this.contentType = FileUtil.guessContentType(file);

        this.bytes = FileUtil.asByteArray(file);

        this.url = file.getAbsolutePath();
    }

    public FileItem(final IFileItem inFileItem) {
        super();

        // init
        this.type = inFileItem.getType();

        this.name = inFileItem.getName();
        this.contentType = inFileItem.getContentType();

        this.bytes = inFileItem.getBytes();

        this.url = inFileItem.getUrl();
    }


    // Getters and Setters
    // ------------------------------------------------------------------------

    @Override
    public FileType getType() {
        if (Objects.isNull(type)) {
            return FileType.UNKNOWN;
        }

        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    // Object Methods
    // ------------------------------------------------------------------------

    @Override
    public String toString() {
        return "FileItem [type=" + type + ", name=" + name + ", contentType=" + contentType + ", size=" + this.getSize()
                + ", url=" + url + "]";
    }


}
