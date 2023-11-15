package com.mandark.jira.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        super();
        // Util class
    }


    // File :: Classpath
    // ------------------------------------------------------------------------

    public static File getClasspathFile(final String filename) {
        // Sanity checks
        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("getClasspathFile : filename is BLANK");
        }


        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final File file = new File(classLoader.getResource(filename).getFile());

        return file;
    }


    // File :: IO
    // ------------------------------------------------------------------------

    public static byte[] asByteArray(final File file) {
        // Sanity checks
        if (Objects.isNull(file)) {
            throw new IllegalArgumentException("asByteArray : input file is NULL");
        }

        byte[] data = null;
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final ByteArrayOutputStream baout = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int c;
            while ((c = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
                baout.write(buffer, 0, c);
            }
            fileInputStream.close();
            baout.close();
            data = baout.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Exception occured while reading file as Byte Array", e);
        }

        return data;
    }


    // File :: Content
    // ------------------------------------------------------------------------

    public static String asString(final File file) {
        // Read data as Byte Array
        final byte[] data = asByteArray(file);
        return Objects.isNull(data) ? null : new String(data);
    }

    public static List<String> readLinesAtRandom(final String filepath, final int batchSize) throws IOException {
        // File Lines
        final List<String> allLines = Files.readAllLines(Paths.get(filepath));
        final int allLinesCount = allLines.size();
        if (allLinesCount <= batchSize) {
            return allLines;
        }

        // Random Lines
        final Random random = new Random();
        final Set<String> randomLinesSet = new HashSet<>();
        while (randomLinesSet.size() < batchSize) {
            // Get Random idx
            final int randomIdx = random.nextInt(allLinesCount);
            final String randomLine = allLines.get(randomIdx);

            randomLinesSet.add(randomLine);
        }

        return new ArrayList<>(randomLinesSet);
    }


    // File :: Names
    // ------------------------------------------------------------------------

    public static String getFileName(final String filepath) {
        // Sanity checks
        if (Objects.isNull(filepath) || filepath.isBlank()) {
            throw new IllegalArgumentException("#getFileName : filepath is BLANK");
        }

        // Read filepath to Path
        final Path path = Paths.get(filepath);
        final Path fileName = path.getFileName();

        return fileName.toString();
    }


    public static String getExtension(final String filename) {
        // Sanity checks
        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("#getExtension : filename is BLANK");
        }

        final String extn = filename.substring(filename.lastIndexOf(".") + 1);
        return extn;
    }

    public static String normaliseFilename(final String inFilename) {
        return normaliseFilename(inFilename, "_");
    }

    public static String normaliseFilename(final String filename, final String replaceWith) {
        // Sanity checks
        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("#normaliseFilename : filename is BLANK");
        }

        if (Objects.isNull(replaceWith) || !(replaceWith.length() > 0)) {
            throw new IllegalArgumentException("#normaliseFilename : Replace with String is BLANK");
        }

        // Case
        final String lowerCase = filename.toLowerCase();

        // Special characters
        String fileName = lowerCase.replaceAll("[-\\[\\]/{}:;#%=()*+?\\^$|<>&\"\']", replaceWith);
        if (fileName.endsWith(".jpg")) { // JPEG vs JPG
            fileName = fileName.replace(".jpg", ".jpeg");
        }

        return fileName;
    }

    public static String appendToFilename(final String filename, final String suffix) {
        // Sanity checks
        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("#appendToFilename : filename is BLANK");
        }

        // empty suffix
        if (Objects.isNull(suffix) || suffix.trim().isEmpty()) {
            return filename;
        }

        // Name and Extension
        final int lastIdx = filename.lastIndexOf(".");
        final String baseName = filename.substring(0, lastIdx);
        final String extension = filename.substring(lastIdx + 1);

        final String newName = baseName.concat("_").concat(suffix).concat(".").concat(extension);
        return newName;
    }


    // File :: Type
    // ------------------------------------------------------------------------

    public static String guessContentType(final File file) {
        // Sanity checks
        if (Objects.isNull(file)) {
            throw new IllegalArgumentException("#guessContentType : file is NULL");
        }

        // Get Bytes
        final byte[] fileBytes = FileUtil.asByteArray(file);
        final InputStream is = new ByteArrayInputStream(fileBytes);

        try {
            final String contentType = URLConnection.guessContentTypeFromStream(is);
            LOGGER.debug("Content-Type detected for {} : {}", file.getAbsolutePath(), contentType);

            return contentType;
        } catch (IOException ex) {
            LOGGER.error("Error while reading InputStream to detect File content type", ex);
        }

        return null;
    }

    public static String guessContentTypeFromExtension(final String filename) {
        // Sanity checks
        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("#guessContentTypeFromExtension : filename is BLANK");
        }

        final String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "mp3":
                return "audio/mpeg";
            case "mp4":
                return "video/mpeg";
            case "avi":
                return "video/avi";
            case "jpg":
                return "image/jpeg";
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            default:
                return null;
        }
    }


    // File :: Create / Write
    // ------------------------------------------------------------------------

    /**
     * Creates a file and returns it.
     * 
     * @param filename Name of the File to create
     * 
     * @return the {@link File} object that is newly created
     */
    public static File createNestedFile(final String rootFolder, final String filename) {
        // Sanity checks
        if (Objects.isNull(rootFolder) || !(rootFolder.length() > 0)) {
            throw new IllegalArgumentException("#createNestedFile : rootFolder path is BLANK");
        }

        if (Objects.isNull(filename) || !(filename.length() > 0)) {
            throw new IllegalArgumentException("#createNestedFile : filename is BLANK");
        }

        // File
        final File root = new File(rootFolder);
        final File targetFile = new File(root, filename);

        final File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        return targetFile;
    }


    /**
     * Write contents to a File with the give name at the given file directory path.
     * 
     * @param fileBaseDir directory in which the files to be written
     * @param filename name of the file to be written
     * @param content file contents to write
     */
    public static void writeToFile(String fileBaseDir, String filename, String content) {
        // Sanity checks
        if (Objects.isNull(content) || !(content.length() > 0)) {
            LOGGER.info("writeToFile :: content is passed is empty. Dir : {} , File : {}", fileBaseDir, filename);
            return;
        }

        final File targetFile = createNestedFile(fileBaseDir, filename);
        if (Objects.isNull(targetFile)) {
            LOGGER.error("Unable to create file: {}, in dir: {}", filename, fileBaseDir);
            return;
        }

        try (final FileWriter fileWriter = new FileWriter(targetFile)) {
            LOGGER.debug("Writing content to file: {}", filename);
            fileWriter.write(content);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    /**
     * 
     * @param filePathStr
     * 
     * @throws IOException
     */
    public static void deleteFile(final String filePathStr) throws IOException {
        // Sanity check
        if (Objects.isNull(filePathStr) || !(filePathStr.length() > 0)) {
            throw new IllegalArgumentException("#deleteFile : file path is BLANK");
        }

        // Path
        final Path filePath = Paths.get(filePathStr);
        Files.deleteIfExists(filePath);
        LOGGER.debug("Successfully deleted file[path] :: {}", filePath);
    }


    // File :: Directory
    // ------------------------------------------------------------------------

    /**
     * Creates the directory named by this abstract pathname Ex: path = /home/user/A/B - - - > creates
     * sub-folder inside the folder, if the folder A does not exist.
     * 
     * @param path path of the folder
     */
    public static void createDir(String path) {
        // Sanity checks
        if (Objects.isNull(path) || !(path.length() > 0)) {
            throw new IllegalArgumentException("#createDir : path is BLANK");
        }

        final File preDir = new File(path);
        if (!preDir.exists()) {
            preDir.mkdirs();
        }
    }

    /**
     * Creates a temporary folder/directory inside the System's temp directory.
     * 
     * @param dirName folder/director name to be created
     * 
     * @return absolute path of the directory created.
     */
    public static String createTempDir(String dirName) {
        // Sanity checks
        if (Objects.isNull(dirName) || !(dirName.length() > 0)) {
            throw new IllegalArgumentException("#createTempDir : dirName is BLANK");
        }

        // dir path
        final String sysTempDirPath = System.getProperty("java.io.tmpdir");

        // temp dir
        final File tempDir = Paths.get(sysTempDirPath, dirName).toFile();
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        return tempDir.getAbsolutePath();
    }


    public static boolean emptyDir(final String dirPath) {
        LOGGER.info("#clearFilesAndDeleteDir :: Cleaning directory[path] : {}", dirPath);

        // File case
        final File workspace = new File(dirPath);
        if (!workspace.isDirectory()) {
            return false;
        }

        // Directory case
        for (File child : workspace.listFiles()) {
            final String childPath = child.getAbsolutePath();
            boolean success = FileUtil.emptyDirAndDelete(childPath);
            if (!success) {
                return false;
            }
        }

        return true;
    }

    public static boolean emptyDirAndDelete(final String dirPath) {
        LOGGER.debug("#emptyDirAndDelete :: Cleaning directory[path] : {}", dirPath);

        // File case
        final File workspace = new File(dirPath);
        if (!workspace.isDirectory()) {
            return workspace.delete();
        }

        // Directory case
        for (final File child : workspace.listFiles()) {
            final String childPath = child.getAbsolutePath();
            boolean success = FileUtil.emptyDirAndDelete(childPath);
            if (!success) {
                return false;
            }
        }

        // Delete the directory
        workspace.delete();

        return true;
    }


    // File :: Download
    // ------------------------------------------------------------------------

    /**
     * Download a web URL to local file path
     * 
     * @param fileUrlStr Web URL String
     * @param localFilePath Local file location path where the file to be downloaded
     * 
     * @throws IOException if any paths are not accessible.
     */
    // public static void downloadFile(final String fileUrlStr, final String localFilePath) throws
    // IOException {
    // LOGGER.info("#downloadFile :: Downloading file to - {}", localFilePath);
    //
    // // local path
    // final File localFile = new File(localFilePath);
    //
    // // Download
    // final URL fileURL = new URL(fileUrlStr);
    // FileUtils.copyURLToFile(fileURL, localFile, 30000, 300000);
    //
    // LOGGER.info("Successfully downloaded file to Local path : {}", localFilePath);
    // }



}
