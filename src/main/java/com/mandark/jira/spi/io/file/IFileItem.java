package com.mandark.jira.spi.io.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;


/**
 * Simple representation of file Objects
 */
public interface IFileItem {


    // Metadata

    FileType getType();

    String getName();

    String getContentType();


    // Data

    byte[] getBytes();


    // Storage info

    String getUrl();



    // Default Methods
    // ------------------------------------------------------------------------

    default Long getSize() {
        final byte[] fileBytes = this.getBytes();
        if (Objects.isNull(fileBytes)) {
            return null;
        }

        return Long.valueOf(fileBytes.length);
    }

    default InputStream asInputStream() {
        final byte[] fileBytes = this.getBytes();
        if (Objects.isNull(fileBytes)) {
            return null;
        }

        return new ByteArrayInputStream(fileBytes);
    }


    default File asFile(final File outFile) throws Exception {
        // Out-file
        final File file = new File(outFile.getAbsolutePath() + "/" + this.getName());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(this.getBytes());
            fos.close();
        } catch (Exception e) {
            throw e;
        }

        return file;
    }

}
