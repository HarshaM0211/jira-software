package com.mandark.jira.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Bunch of Web URL utilities.
 */
public final class Urls {

    private static final Logger LOGGER = LoggerFactory.getLogger(Urls.class);

    public static final String URL_STRING_PATTERN =
            "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public static final String[] URL_SCHEMES_HTTP = {"http", "https"};

    private Urls() {
        super();
        // Util class
    }


    // Util Methods
    // ------------------------------------------------------------------------

    /**
     * Validate if given URL is a valid URL or not.
     * 
     * @param url URL to validate.
     * 
     * @return true if the URL is valid.
     */
    public static boolean isValidURL(String url) {
        // final UrlValidator URL_VALIDATOR = new UrlValidator(URL_SCHEMES_HTTP);
        // return URL_VALIDATOR.isValid(url);
        throw new RuntimeException("Use Apache commons-validator lib :: UrlValidator#isValid");
    }


    /**
     * Match a URL against a URL pattern.
     * 
     * @param urlPattern pattern to match against.
     * @param url URL to be matched
     * 
     * @return true if the URL matches the pattern
     */
    public static boolean isMatchingUrl(Pattern urlPattern, URL url) {
        // Sanity checks
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("#isMatchingUrl : URL should not be null");
        }

        return isMatchingUrl(urlPattern, url.toString());
    }

    /**
     * Match a URL string against a URL pattern.
     * 
     * @param urlPattern regex pattern to match against.
     * @param url URL to be matched
     * 
     * @return true if the URL matches the pattern
     */
    public static boolean isMatchingUrl(Pattern urlPattern, String urlStr) {
        // Sanity checks
        if (Objects.isNull(urlPattern)) {
            throw new IllegalArgumentException("#isMatchingUrl : URL Pattern should not be null");
        }

        if (Objects.isNull(urlStr) || !(urlStr.length() > 0)) {
            throw new IllegalArgumentException("#isMatchingUrl : URL String should not be null or empty");
        }

        Matcher matcher = urlPattern.matcher(urlStr);
        return matcher.matches();
    }



    // Parsers
    // ------------------------------------------------------------------------

    public static String getHostURL(String urlStr) throws MalformedURLException {
        // Sanity checks
        if (Objects.isNull(urlStr) || !(urlStr.length() > 0)) {
            throw new IllegalArgumentException("#getHostURL : URL String is BLANK");
        }

        // Check for protocol
        if (!urlStr.startsWith("https://") && !urlStr.startsWith("http://")) {
            urlStr = "http://".concat(urlStr);
        }

        return getHostURL(new URL(urlStr));
    }

    public static String getHostURL(URL url) {
        // Sanity checks
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("#getHostURL : URL is NULL");
        }

        final StringBuilder host = new StringBuilder();
        host.append(url.getProtocol()).append("://");
        host.append(url.getAuthority());
        return host.toString();
    }

    public static String getPathURL(URL url) {
        // Sanity checks
        assert Objects.nonNull(url) : "#getPathURL : input URL is NULL";

        final StringBuilder host = new StringBuilder();
        host.append(url.getProtocol()).append("://");
        host.append(url.getAuthority());
        host.append(url.getPath());
        return host.toString();
    }

    public static String getDomainName(final String URLstr) throws MalformedURLException {
        // Sanity checks
        if (Objects.isNull(URLstr) || !(URLstr.length() > 0)) {
            throw new IllegalArgumentException("#getDomainName : URL String is BLANK");
        }

        // as URL
        final URL url = new URL(URLstr);

        // Get Domain
        final String domain = url.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }


    // Factory Methods
    // ------------------------------------------------------------------------

    /**
     * Converts list of Strings to URL objects.
     * 
     * @param linkStrs URL Strings collection
     * @param skipBlanks set to true, to skip all blank URL strings from the input
     * @param failOnError set to true, to fail if any error occurs
     * 
     * @return List of URL objects
     */
    public static List<URL> asURLs(Collection<String> linkStrs, boolean skipBlanks, boolean failOnError) {
        // Sanity checks
        if (Objects.isNull(linkStrs) || linkStrs.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> toConvert = new ArrayList<>(linkStrs);

        // Skip Blanks
        if (skipBlanks) {
            toConvert = linkStrs.stream().filter(link -> {
                return Objects.nonNull(link) && !link.isBlank();
            }).collect(Collectors.toList());
        }

        final List<URL> urls = toConvert.stream().map(link -> {
            try {
                return new URL(link);
            } catch (Exception ex) {
                String errMsg = String.format("Error occured in converting %s to URL : %s", link, ex.getMessage());
                LOGGER.error(errMsg, ex);

                // Fail On Error
                if (failOnError) {
                    throw new RuntimeException(ex);
                } else {
                    return null;
                }
            }
        }).collect(Collectors.toList());

        return urls;
    }



    // Parsing
    // ------------------------------------------------------------------------

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static Map<String, List<String>> getQueryParams(final URL url) {
        // Sanity checks
        if (Objects.isNull(url)) {
            return new HashMap<>();
        }

        final Map<String, List<String>> qryParams = new HashMap<>();
        try {
            final String qryStr = URLDecoder.decode(url.getQuery(), DEFAULT_ENCODING);
            final String[] pairs = qryStr.split("&");
            for (String pair : pairs) {
                final int idx = pair.indexOf("=");
                final String key = idx > 0 ? pair.substring(0, idx) : pair;
                if (!qryParams.containsKey(key)) {
                    qryParams.put(key, new ArrayList<>());
                }

                final String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
                qryParams.get(key).add(value);
            }
        } catch (UnsupportedEncodingException ex) {
            String errMsg = String.format("Failed to decode the URL Query : %s - %s", url.getQuery(), ex.getMessage());
            LOGGER.error(errMsg);
        }

        return qryParams;
    }


    public static Map<String, String> getQueryParamsSimple(final URL url) {
        // Sanity checks
        if (Objects.isNull(url)) {
            return new HashMap<>();
        }

        final Map<String, String> qryParams = new HashMap<>();
        try {
            final String qryStr = URLDecoder.decode(url.getQuery(), DEFAULT_ENCODING);
            String[] pairs = qryStr.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                qryParams.put(pair.substring(0, idx), pair.substring(idx + 1));
            }
        } catch (UnsupportedEncodingException ex) {
            String errMsg = String.format("Failed to decode the URL Query : %s - %s", url.getQuery(), ex.getMessage());
            LOGGER.error(errMsg);
        }

        return qryParams;
    }


}
