package com.mandark.jira.commons.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;


/**
 * Assertion/Verification utility class that assists in validating arguments.
 *
 * <p>
 * This class is similar to JUnit's assertion library. If an argument value is deemed invalid, an
 * {@link IllegalArgumentException} is thrown (typically).
 * </p>
 */
public final class Verify {

    /**
     * Verifies a boolean expression, throwing {@code IllegalArgumentException} if the test result is
     * {@code false}.
     * 
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verifies a boolean expression, throwing {@code IllegalArgumentException} if the test result is
     * {@code false}.
     * 
     * @param expression a boolean expression
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if expression is {@code false}
     */
    public static void isTrue(boolean expression, RuntimeException exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * Verifies a boolean expression, throwing {@code IllegalArgumentException} if the test result is
     * {@code false}.
     * 
     * @param expression a boolean expression
     * 
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[failed] - this expression must be true");
    }

    /**
     * Verify that an object is not {@code null} .
     * 
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that an object is not {@code null} .
     * 
     * @param object the object to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the object is {@code null}
     */
    public static void notNull(Object object, RuntimeException exception) {
        if (Objects.isNull(object)) {
            throw exception;
        }
    }

    /**
     * Verify that an object is not {@code null} .
     * 
     * @param object the object to check
     * 
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(Object object) {
        notNull(object, "[failed] - this argument is required; it must not be null");
    }

    /**
     * Verify that all passed objects are not {@code null} .
     * 
     * @param args the array of objects to be verified.
     * 
     * @throws IllegalArgumentException if any one of the objects is {@code null}
     */
    public static void notNull(Object... args) {
        if (Objects.isNull(args))
            return;

        for (Object object : args) {
            notNull(object, "[failed] - this argument is required; it must not be null");
        }
    }

    /**
     * Verify that the given String is not empty; that is, it must not be {@code null} and not the empty
     * String.
     * 
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     */
    public static void hasLength(String text, String message) {
        if (Objects.isNull(text) || !(text.length() > 0)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that the given String is not empty; that is, it must not be {@code null} and not the empty
     * String.
     * 
     * @param text the String to check
     * @param exception the exception to be thrown if the assertion fails
     */
    public static void hasLength(String text, RuntimeException exception) {
        if (Objects.isNull(text) || !(text.length() > 0)) {
            throw exception;
        }
    }

    /**
     * Verify that the given String is not empty; that is, it must not be {@code null} and not the empty
     * String.
     * 
     * @param text the String to check
     */
    public static void hasLength(String text) {
        hasLength(text, "[failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * Verify that the given String has valid text content; that is, it must not be {@code null} and
     * must contain at least one non-whitespace character.
     * 
     * @param text the String to check
     * @param message the exception message to use if the assertion fails
     */
    public static void hasText(String text, String message) {
        hasLength(text, message);

        // Check white spaces ...
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that the given String has valid text content; that is, it must not be {@code null} and
     * must contain at least one non-whitespace character.
     * 
     * @param text the String to check
     * @param exception the exception to be thrown if the assertion fails
     */
    public static void hasText(String text, RuntimeException exception) {
        hasLength(text, exception);

        // Check white spaces ...
        if (text.trim().isEmpty()) {
            throw exception;
        }
    }

    /**
     * Verify that the given String has valid text content; that is, it must not be {@code null} and
     * must contain at least one non-whitespace character.
     * 
     * @param text the String to check
     */
    public static void hasText(String text) {
        hasText(text, "[failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * Verify that an array has elements; that is, it must not be {@code null} and must have at least
     * one element.
     * 
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (Objects.isNull(array) || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that an array has elements; that is, it must not be {@code null} and must have at least
     * one element.
     * 
     * @param array the array to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, RuntimeException exception) {
        if (Objects.isNull(array) || array.length == 0) {
            throw exception;
        }
    }

    /**
     * Verify that an array has elements; that is, it must not be {@code null} and must have at least
     * one element.
     * 
     * @param array the array to check
     * 
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Verify that an array has no null elements. Note: Does not complain if the array is empty!
     * 
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array, String message) {
        if (Objects.isNull(array)) {
            return;
        }

        for (Object element : array) {
            if (Objects.isNull(element)) {
                throw new IllegalArgumentException(message);
            }
        }
    }


    /**
     * Verify that an array has no null elements. Note: Does not complain if the array is empty!
     * 
     * @param array the array to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array, RuntimeException exception) {
        if (Objects.isNull(array)) {
            return;
        }

        for (Object element : array) {
            if (Objects.isNull(element)) {
                throw exception;
            }
        }
    }

    /**
     * Verify that a set has no null elements. Note: Does not complain if the set is empty!
     * 
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Collection<? extends Object> collection, String message) {
        if (Objects.isNull(collection)) {
            return;
        }

        for (final Object element : collection) {
            if (Objects.isNull(element)) {
                throw new IllegalArgumentException(message);
            }
        }
    }


    /**
     * Verify that a set has no null elements. Note: Does not complain if the set is empty!
     * 
     * @param collection the collection to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the object array contains a {@code null} element
     */
    public static void noNullElements(Collection<? extends Object> collection, RuntimeException exception) {
        if (Objects.isNull(collection)) {
            return;
        }

        for (final Object element : collection) {
            if (Objects.isNull(element)) {
                throw exception;
            }
        }
    }


    /**
     * Verify that an array has no null elements. Note: Does not complain if the array is empty!
     * 
     * @param array the array to check
     * 
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[failed] - this array must not contain any null elements");
    }

    /**
     * Verify that a set has no null elements. Note: Does not complain if the set is empty!
     * 
     * @param collection the collection to check
     * 
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(Collection<? extends Object> collection) {
        noNullElements(collection, "[failed] - this set must not contain any null elements");
    }


    /**
     * Verify that a collection has elements; that is, it must not be {@code null} and must have at
     * least one element.
     * 
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<? extends Object> collection, String message) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that a collection has elements; that is, it must not be {@code null} and must have at
     * least one element.
     * 
     * @param collection the collection to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<? extends Object> collection, RuntimeException exception) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw exception;
        }
    }

    /**
     * Verify that a collection has elements; that is, it must not be {@code null} and must have at
     * least one element.
     * 
     * @param collection the collection to check
     * 
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<? extends Object> collection) {
        notEmpty(collection, "[failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Verify that a Map has entries; that is, it must not be {@code null} and must have at least one
     * entry.
     * 
     * @param map the map to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<? extends Object, ? extends Object> map, String message) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verify that a Map has entries; that is, it must not be {@code null} and must have at least one
     * entry.
     * 
     * @param map the map to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<? extends Object, ? extends Object> map, RuntimeException exception) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw exception;
        }
    }

    /**
     * Verify that a Map has entries; that is, it must not be {@code null} and must have at least one
     * entry.
     * 
     * @param map the map to check
     * 
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<? extends Object, ? extends Object> map) {
        notEmpty(map, "[failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * Verify that the provided object is an instance of the provided class.
     * 
     * @param clazz the required class
     * @param obj the object to check
     * 
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * 
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * Verify that the provided object is an instance of the provided class.
     * 
     * @param type the type to check against
     * @param obj the object to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the object is not an instance of class
     * 
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + " " + "Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    /**
     * Verify that the provided object is an instance of the provided class.
     * 
     * @param type the type to check against
     * @param obj the object to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the object is not an instance of class
     * 
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> type, Object obj, RuntimeException exception) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw exception;
        }
    }

    /**
     * Verify that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 
     * @param superType the super type to check
     * @param subType the sub type to check
     * 
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * Verify that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param message the exception message to use if the assertion fails
     * 
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (Objects.isNull(subType) || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * Verify that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param exception the exception to be thrown if the assertion fails
     * 
     * @throws the required exception if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, RuntimeException exception) {
        notNull(superType, "Type to check against must not be null");
        if (Objects.isNull(subType) || !superType.isAssignableFrom(subType)) {
            throw exception;
        }
    }



    /**
     * TODO Complete ...
     * 
     * @param values
     * @param value
     * 
     * @throws IllegalArgumentException
     */
    public static void containsValue(Collection<? extends Object> values, Object value) {
        // Err Message
        final String errMsg = "[failed] - the collection does not contain the passed entry";

        // Check if Empty
        notEmpty(values, errMsg);

        for (Object val : values) {
            if (Objects.nonNull(val) && val.equals(value)) {
                return;
            }
        }

        throw new IllegalArgumentException(errMsg);
    }


    // URL
    // ------------------------------------------------------------------------

    /**
     * Checks if the argument is a well formed URL
     * 
     * @param url
     * 
     * @throws IllegalArgumentException
     */
    public static void isURL(String url) {
        isURL(url, "[failed] - the URL is not well formed");
    }

    /**
     * Checks if the argument is a well formed URL
     * 
     * @param url
     * 
     * @throws IllegalArgumentException
     */
    public static void isURL(String url, String message) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(message);
        }
    }
}
