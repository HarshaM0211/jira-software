package com.mandark.jira.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Odd ball utilities that are just convenient to have.
 */
public final class Strings {

    private Strings() {
        super();
        // Utility class
    }


    // String utilities
    // ------------------------------------------------------------------------


    // Join ===================================================================

    /**
     * Join all the Objects together with a separator.
     * 
     * @param separator The joining string.
     * @param iterable An iterable of Objects to join.
     * 
     * @return The joined String
     */
    public static String join(String separator, Iterable<?> iterable) {
        List<Object> items = new ArrayList<Object>();
        for (Object item : iterable) {
            items.add(item);
        }

        return join(separator, items);
    }

    /**
     * Join a list of Objects with the given separator.
     * 
     * @param separator The joining string.
     * @param items The items to be joined.
     * 
     * @return The joined String.
     */
    public static String join(String separator, List<?> items) {
        int count = items.size();
        if (count == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count - 1; ++i) {
            builder.append(items.get(i));
            builder.append(separator);
        }

        builder.append(items.get(count - 1));
        return builder.toString();
    }

    /**
     * Join any number of Objects.
     * 
     * @param separator The joining string.
     * @param items The array of Objects to join.
     * 
     * @return The joined String.
     */
    public static String join(String separator, Object... items) {
        if (items.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < items.length - 1; ++i) {
            builder.append(items[i]);
            builder.append(separator);
        }
        builder.append(items[items.length - 1]);
        return builder.toString();
    }

    // Repeat =================================================================

    /**
     * Construct a string of repeats.
     * 
     * @param value The repeating unit.
     * @param times The number of repeats.
     * 
     * @return The repeated string.
     */
    public static String repeat(String value, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            builder.append(value);
        }
        return builder.toString();
    }


    // Sub-String =============================================================

    /**
     * Returns the indicated number of prefix characters.
     * 
     * @param string The string.
     * @param count How many characters to grab.
     * 
     * @return The prefix string.
     */
    public static String head(String string, int count) {
        return head(string, count, false);
    }

    /**
     * Returns the indicated number of prefix characters.
     * 
     * @param string The string.
     * @param count How many characters to grab. isTruncated if true the string is truncated else it
     *        adds ellipsis at the end of the string
     * 
     * @return The prefix string.
     */
    public static String head(String string, int count, boolean isTruncated) {
        if (string.length() <= count) {
            return string;
        }

        if (isTruncated) {
            return string.substring(0, count);
        }

        return String.format("%s...", string.substring(0, count - 3));
    }


    /**
     * Return the tail of the given string of given size.
     * 
     * @param string The string.
     * @param count The length of the tail to return.
     * 
     * @return The tail string.
     */
    public static String tail(String string, int count) {
        int length = string.length();
        if (length < count) {
            return string;
        }
        return string.substring(length - count, length);
    }


    /**
     * Get the prefix of the given string up to but not including the given character sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The prefix. If the sequence is not found the original string is returned.
     */
    public static String beforeFirst(String string, String sequence) {
        int index = string.indexOf(sequence);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index);
    }

    /**
     * Get the prefix of the given string up to but not including the last instance of the given
     * character sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The prefix. If the sequence is not found the original string is returned.
     */
    public static String beforeLast(String string, String sequence) {
        int index = string.lastIndexOf(sequence);
        if (index == -1) {
            return "";
        }
        return string.substring(0, index);
    }

    /**
     * Get the prefix of the given string up to and including the given character sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The prefix. If the sequence is not found a 0 length string is returned.
     */
    public static String throughFirst(String string, String sequence) {
        int index = string.indexOf(sequence);
        if (index == -1) {
            return "";
        }
        return string.substring(0, index + sequence.length());
    }

    /**
     * Get the prefix of the given string up to and including the last instance of the given character
     * sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The prefix. If the sequence is not found a 0 length string is returned.
     */
    public static String throughLast(String string, String sequence) {
        int index = string.lastIndexOf(sequence);
        if (index == -1) {
            return "";
        }
        return string.substring(0, index + sequence.length());
    }

    /**
     * Get the suffix of the string after the last instance of the given sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The suffix. If the sequence is not found then the original string is returned.
     */
    public static String afterLast(String string, String sequence) {
        int index = string.lastIndexOf(sequence);
        if (index == -1) {
            return string;
        }
        return string.substring(index + sequence.length(), string.length());
    }

    /**
     * Get the suffix of the string after the first instance of the given sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The suffix. If the sequence is not found then the original string is returned.
     */
    public static String afterFirst(String string, String sequence) {
        int index = string.indexOf(sequence);
        if (index == -1) {
            return string;
        }
        return string.substring(index + sequence.length(), string.length());
    }

    /**
     * Get the suffix of the string starting at the last instance of the given sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The suffix. If the sequence is not found then a 0 length string is returned.
     */
    public static String fromLast(String string, String sequence) {
        int index = string.lastIndexOf(sequence);
        if (index == -1) {
            return "";
        }
        return string.substring(index, string.length());
    }

    /**
     * Get the suffix of the string starting at the first instance of the given sequence.
     * 
     * @param string The string.
     * @param sequence The sequence to match.
     * 
     * @return The suffix. If the sequence is not found then a 0 length string is returned.
     */
    public static String fromFirst(String string, String sequence) {
        int index = string.indexOf(sequence);
        if (index == -1) {
            return "";
        }
        return string.substring(index, string.length());
    }

    private static char[] HEXDIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};



    // Trim ===================================================================

    /**
     * Returns the string to the first {@code count} characters, adding the prefix and suffix if the
     * string is trimmed.
     * 
     * Use {@link #Head} where no prefix or suffix should be added if the result is trimmed.
     * 
     * @param string The string.
     * @param count How many characters to grab.
     * @param prefix Prefix to add if result is trimmed.
     * @param suffix Suffix to add if result is trimmed.
     * 
     * @return The possibly trimmed string.
     */
    public static String trim(String string, int count, String prefix, String suffix) {
        if (string.length() < count) {
            return string;
        }
        return prefix + string.substring(0, count) + suffix;
    }

    /**
     * Returns the string to the first {@code count} characters, adding the prefix and suffix if the
     * string is trimmed.
     * 
     */
    public static String trim(String string, int count, String suffix) {
        return trim(string, count, "", suffix);
    }

    /**
     * Works similar to {@link String#trim()} but to trim any char.
     * 
     * @param inString
     * @param toTrim
     * 
     * @return
     */
    public static String trim(String inString, char toTrim) {
        int len = inString.length();
        int st = 0;
        char[] val = inString.toCharArray();

        while ((st < len) && (val[st] <= toTrim)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= toTrim)) {
            len--;
        }

        return ((st > 0) || (len < inString.length())) ? inString.substring(st, len) : inString;
    }



    // Conversions ============================================================

    /**
     * A utility to get a hex encoded string of arbitrary byte data.
     * 
     * @param bytes The data.
     * 
     * @return A String.
     */
    public static String bytesToHex(byte[] bytes) {
        final StringBuffer out = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            out.append(HEXDIGITS[(bytes[i] >> 4) & 0xf]);
            out.append(HEXDIGITS[bytes[i] & 0xf]);
        }

        return out.toString();
    }



    public static List<String> csvStringAsValues(String csvStr) {
        // Sanity checks
        if (Objects.isNull(csvStr) || csvStr.isBlank()) {
            return new ArrayList<>();
        }

        final List<String> csv = new ArrayList<>();

        final String[] splits = csvStr.split(",");
        for (final String split : splits) {
            csv.add(split.trim());
        }

        return csv;
    }



    // String cleanup
    // ------------------------------------------------------------------------

    public static String normalize(String inName, String specialCharReplacement) {
        // Sanity checks
        if (Objects.isNull(inName) || inName.isBlank()) {
            return inName;
        }

        // Special characters
        String normalized = inName.replaceAll("[-\\[\\]/{}:,;#%=()*+?\\^$|<>&\"\'\\\\]", " ");
        return normalized.toLowerCase().trim().replaceAll("\\s+", specialCharReplacement);
    }


    public static String normalizeComparableString(String inValue) {
        // Sanity checks
        if (Objects.isNull(inValue) || inValue.isBlank()) {
            return inValue;
        }

        return inValue.trim().toLowerCase();
    }
}
