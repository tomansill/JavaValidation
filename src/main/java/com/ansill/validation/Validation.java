package com.ansill.validation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validation class
 */
@Immutable
public final class Validation {

    /** Message for invalid hostname */
    @Nonnull
    static final String INVALID_EMAIL_MESSAGE = "is expected to be a valid email address but it is actually not a valid email address";
    /** Message for invalid hostname */
    @Nonnull
    static final String INVALID_HOSTNAME_MESSAGE = "is expected to be a valid hostname/IP address but it is actually not a valid hostname/IP address";
    /** Message for invalid port numbers */
    @Nonnull
    static final String INVALID_PORT_MESSAGE = "is expected to be within 1-65535 range but is found to be out of the range";
    /** Regex pattern for valid hostname */
    @Nonnull
    private static final Pattern VALID_HOSTNAME_REGEX = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    /** Regex pattern for valid ip address */
    @Nonnull
    private static final Pattern VALID_IP_REGEX = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
    /** Regex pattern for valid email address */
    @Nonnull
    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile(
      "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    /** Message for object with null values */
    @Nonnull
    static final String OBJECT_NULL_MESSAGE = "is expected to be non-null but is found to be null";

    /** Message for natural number error */
    @Nonnull
    static final String NATURAL_NUMBER_MESSAGE = "is expected to be a natural number (1, 2, ..., N-1, N) but it is actually not a natural number";

    /** Message for greater comparison error */
    @Nonnull
    static final String GREATER_NUMBER_MESSAGE = "is expected to be greater number, but it is not";

    /** Message for lesser comparison error */
    @Nonnull
    static final String LESSER_NUMBER_MESSAGE = "is expected to be lesser number, but it is not";

    /** Message for non-negative number error */
    @Nonnull
    static final String NONNEGATIVE_NUMBER_MESSAGE = "is expected to be non-negative but value is actually a negative number";

    /** Message for non-empty string error */
    @Nonnull
    static final String EMPTY_STRING_MESSAGE = "is expected to be non-empty but value is actually a empty string";

    /** Message for non-empty array/collection error */
    @Nonnull
    static final String EMPTY_ARRAY_MESSAGE = "is expected to be non-empty but value is actually empty";

    /** Message for nulls in array/collection error */
    @Nonnull
    static final String NULLS_IN_ARRAY_MESSAGE = "is expected to have all of its list members to be non-null but the list contains null members";

    private Validation(){
        // Prevents any instantiation
    }

    /**
     * Composes message with message and optional variable name
     *
     * @param variable_name variable name to properly reference to. If variable name is empty, then it will be omitted in the message
     * @param message       message
     * @return composed message
     */
    @Nonnull
    static String composeMessage(@Nullable String variable_name, @Nonnull String message){
        String composed_message = "Value ";
        if(variable_name != null) composed_message += "in variable '" + variable_name + "' ";
        composed_message += message;
        return composed_message;
    }

    /**
     * Composes message with message and indices of invalid values and optional variable name
     *
     * @param variable_name variable name to properly reference to. If variable name is empty, then it will be omitted in the message
     * @param message       message
     * @return composed message
     */
    @Nonnull
    static String composeMessageWithArrays(
            @Nullable String variable_name,
            @Nonnull String message,
            @Nonnull List<Integer> indices
    ) {
        String composed_message = ". ";
        if (indices.size() == 1) composed_message += "The invalid member is at index " + indices.iterator().next();
        else composed_message += "Invalid members are located at indices " +
                Arrays.toString(indices.toArray(new Integer[0]));
        return composeMessage(variable_name, message) + composed_message;
    }

    /**
     * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
     *
     * @param port port number to be asserted
     * @return valid port number
     * @throws IllegalArgumentException thrown if the port number is invalid in any way
     */
    @Nonnegative
    public static int assertValidPortNumber(int port) throws IllegalArgumentException {
        return innerAssertValidPortNumber(port, null);
    }

    /**
     * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
     *
     * @param port          port number to be asserted
     * @param variable_name name of variable
     * @return valid port number
     * @throws IllegalArgumentException thrown if the port number is invalid in any way
     */
    @Nonnegative
    public static int assertValidPortNumber(int port, @Nonnull String variable_name) throws IllegalArgumentException {
        innerAssertNonnull(variable_name, "variable_name", -1);
        return innerAssertValidPortNumber(port, variable_name);
    }

    /**
     * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
     *
     * @param port          port number to be asserted
     * @param variable_name name of variable
     * @return valid port number
     * @throws IllegalArgumentException thrown if the port number is invalid in any way
     */
    @Nonnegative
    private static int innerAssertValidPortNumber(int port, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Exit if valid
        if (port > 0 && port <= 65535) return port;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, INVALID_PORT_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
     *
     * @param hostname hostname to be asserted
     * @return valid hostname
     * @throws IllegalArgumentException thrown if the hostname is invalid in any way
     */
    @Nonnull
    public static String assertValidHostname(@Nullable String hostname) throws IllegalArgumentException {
        return innerAssertValidHostname(hostname, null);
    }

    /**
     * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
     *
     * @param hostname      hostname to be asserted
     * @param variable_name name of variable
     * @return valid hostname
     * @throws IllegalArgumentException thrown if the hostname is invalid in any way
     */
    @Nonnull
    public static String assertValidHostname(@Nullable String hostname, @Nonnull String variable_name) throws IllegalArgumentException {
        innerAssertNonnull(variable_name, "variable_name", -1);
        return innerAssertValidHostname(hostname, variable_name);
    }

    /**
     * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
     *
     * @param hostname      hostname to be asserted
     * @param variable_name name of variable
     * @return valid hostname
     * @throws IllegalArgumentException thrown if the hostname is invalid in any way
     */
    @Nonnull
    private static String innerAssertValidHostname(@Nullable String hostname, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Assert non null
        hostname = innerAssertNonnull(hostname, variable_name, 1);

        // Exit if valid
        if (VALID_HOSTNAME_REGEX.matcher(hostname).matches() || VALID_IP_REGEX.matcher(hostname).matches())
            return hostname;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, INVALID_HOSTNAME_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
     *
     * @param email_address email address to be asserted
     * @return valid email address
     * @throws IllegalArgumentException thrown if the email address is invalid in any way
     */
    @Nonnull
    public static String assertValidEmailAddress(@Nullable String email_address) throws IllegalArgumentException {
        return innerAssertValidEmailAddress(email_address, null);
    }

    /**
     * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
     *
     * @param email_address email address to be asserted
     * @param variable_name name of variable
     * @return valid email address
     * @throws IllegalArgumentException thrown if the email address is invalid in any way
     */
    @Nonnull
    public static String assertValidEmailAddress(@Nullable String email_address, @Nonnull String variable_name) throws IllegalArgumentException {
        innerAssertNonnull(variable_name, "variable_name", -1);
        return innerAssertValidEmailAddress(email_address, variable_name);
    }

    /**
     * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
     *
     * @param email_address email address to be asserted
     * @param variable_name name of variable
     * @return valid email address
     * @throws IllegalArgumentException thrown if the email address is invalid in any way
     */
    @Nonnull
    private static String innerAssertValidEmailAddress(@Nullable String email_address, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Assert non null
        email_address = innerAssertNonnull(email_address, variable_name, 1);

        // Exit if valid
        if (VALID_EMAIL_REGEX.matcher(email_address).matches()) return email_address;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, INVALID_EMAIL_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param object object to be asserted
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    public static <T> T assertNonnull(@Nullable T object) throws IllegalArgumentException {
        return innerAssertNonnull(object, null, 0);
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param object        object to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    public static <T> T assertNonnull(@Nullable T object, @Nonnull String variable_name)
            throws IllegalArgumentException {
        innerAssertNonnull(variable_name, "variable_name", -1);
        return innerAssertNonnull(object, variable_name, 0);
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param <T>           object type
     * @param object        object to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @return nonnull object
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    @Nonnull
    private static <T> T innerAssertNonnull(@Nullable T object, @Nullable String variable_name, int level)
            throws IllegalArgumentException {

        // Exit if not null
        if (object != null) return object;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, OBJECT_NULL_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, level);
    }

    /**
     * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertGreaterThan(int number, long compare) throws IllegalArgumentException{
        return (int) innerAssertGreaterThan(number, compare, null);
    }

    /**
     * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertGreaterThan(int number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return (int) innerAssertGreaterThan(number, compare, variable_name);
    }

    /**
     * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertGreaterThan(long number, long compare) throws IllegalArgumentException{
        return innerAssertGreaterThan(number, compare, null);
    }

    /**
     * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertGreaterThan(long number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return innerAssertGreaterThan(number, compare, variable_name);
    }

    /**
     * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static long innerAssertGreaterThan(long number, long compare, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Exit if not null
        if(number > compare) return number;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, GREATER_NUMBER_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertLesserThan(int number, long compare) throws IllegalArgumentException{
        return (int) innerAssertLesserThan(number, compare, null);
    }

    /**
     * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertLesserThan(int number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return (int) innerAssertLesserThan(number, compare, variable_name);
    }

    /**
     * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertLesserThan(long number, long compare) throws IllegalArgumentException{
        return innerAssertLesserThan(number, compare, null);
    }

    /**
     * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertLesserThan(long number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return innerAssertLesserThan(number, compare, variable_name);
    }


    /**
     * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertGreaterThanOrEqual(int number, long compare) throws IllegalArgumentException{
        return (int) innerAssertGreaterThanOrEqual(number, compare, null);
    }

    /**
     * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertGreaterThanOrEqual(int number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return (int) innerAssertGreaterThanOrEqual(number, compare, variable_name);
    }

    /**
     * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
     *
     * @param number  number to be asserted
     * @param compare number being compared
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertGreaterThanOrEqual(long number, long compare) throws IllegalArgumentException{
        return innerAssertGreaterThanOrEqual(number, compare, null);
    }

    /**
     * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertGreaterThanOrEqual(long number, long compare, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return innerAssertGreaterThanOrEqual(number, compare, variable_name);
    }

    /**
     * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static long innerAssertGreaterThanOrEqual(long number, long compare, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Exit if not null
        if(number > compare) return number;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, GREATER_NUMBER_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param compare       number being compared
     * @param variable_name name of variable
     * @return valid number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static long innerAssertLesserThan(long number, long compare, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Exit if not null
        if(number < compare) return number;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, LESSER_NUMBER_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number number to be asserted
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertNaturalNumber(int number) throws IllegalArgumentException{
        return (int) innerAssertNaturalNumber(number, null);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static int assertNaturalNumber(int number, @Nonnull String variable_name) throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        return (int) innerAssertNaturalNumber(number, variable_name);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number number to be asserted
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertNaturalNumber(long number) throws IllegalArgumentException{
        return innerAssertNaturalNumber(number, null);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertNaturalNumber(long number, @Nonnull String variable_name) throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNaturalNumber(number, variable_name);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @return valid natural number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static long innerAssertNaturalNumber(long number, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Exit if not null
        if (number > 0) return number;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, NATURAL_NUMBER_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
     *
     * @param number number to be asserted
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertNonnegative(long number) throws IllegalArgumentException {
        return innerAssertNonnegative(number, null);
    }

    /**
     * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static long assertNonnegative(long number, @Nonnull String variable_name) throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonnegative(number, variable_name);
    }

    /**
     * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @return valid nonnegative number
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static long innerAssertNonnegative(long number, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Exit if not null
        if (number >= 0) return number;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, NONNEGATIVE_NUMBER_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
     * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
     *
     * @param string string to be asserted
     * @return valid nonempty string
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    public static String assertNonemptyString(@Nullable String string) throws IllegalArgumentException {
        return innerAssertNonemptyString(string, null);
    }

    /**
     * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
     * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
     *
     * @param string        string to be asserted
     * @param variable_name name of variable
     * @return valid nonempty string
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    public static String assertNonemptyString(@Nullable String string, @Nonnull String variable_name)
            throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonemptyString(string, variable_name);
    }

    /**
     * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
     * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
     *
     * @param string        string to be asserted
     * @param variable_name name of variable
     * @return valid nonempty string
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    private static String innerAssertNonemptyString(@Nullable String string, @Nullable String variable_name)
            throws IllegalArgumentException {

        // Assert non null
        string = innerAssertNonnull(string, variable_name, 1);

        // Exit if not empty
        if (!string.trim().equals("")) return string;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, EMPTY_STRING_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>        type of collection
     * @param collection collection to be asserted
     * @return valid nonempty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static <T> Collection<T> assertNonempty(@Nullable Collection<T> collection) throws IllegalArgumentException {
        return innerAssertNonempty(collection, null, 0);
    }

    /**
     * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>           type of collection
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @return valid nonempty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static <T> Collection<T> assertNonempty(@Nullable Collection<T> collection, @Nonnull String variable_name)
            throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonempty(collection, variable_name, 0);
    }

    /**
     * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>           type of collection
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @return valid nonempty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    private static <T> Collection<T> innerAssertNonempty(@Nullable Collection<T> collection, @Nullable String variable_name, int level)
            throws IllegalArgumentException {

        // Assert non null
        innerAssertNonnull(collection, variable_name, 1);

        // Pass it forward
        innerAssertNonempty(collection.toArray(new Object[0]), variable_name, 1 + level);

        // Return it
        return collection;
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>   type of array
     * @param array array to be asserted
     * @return valid nonempty array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static <T> T[] assertNonempty(@Nullable T[] array) throws IllegalArgumentException {
        return innerAssertNonempty(array, null, 0);
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>           type of array
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @return valid nonempty array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static <T> T[] assertNonempty(@Nullable T[] array, @Nonnull String variable_name)
            throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonempty(array, variable_name, 0);
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param <T>           type of array
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @return valid nonempty array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    private static <T> T[] innerAssertNonempty(@Nullable T[] array, @Nullable String variable_name, int level)
            throws IllegalArgumentException {

        // Assert non null
        innerAssertNonnull(array, variable_name, level + 1);

        // Exit if not empty
        if (Objects.requireNonNull(array).length != 0) return array;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, EMPTY_ARRAY_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, level);
    }

    /**
     * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if set is non-empty if requested.
     *
     * @param <T>           type of array
     * @param set           set to be asserted
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @return valid nonnull set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    public static <T> Set<T> assertNonnullElements(@Nullable Set<T> set, boolean empty_allowed) throws IllegalArgumentException {
        return innerAssertNonnullElements(set, null, empty_allowed);
    }

    /**
     * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if set is non-empty if requested.
     *
     * @param <T>           type of array
     * @param set           set to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @return valid nonnull set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    public static <T> Set<T> assertNonnullElements(@Nullable Set<T> set, @Nonnull String variable_name, boolean empty_allowed)
            throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonnullElements(set, variable_name, empty_allowed);
    }

    /**
     * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if set is non-empty if requested.
     *
     * @param <T>           type of array
     * @param set           set to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @return valid nonnull set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    private static <T> Set<T> innerAssertNonnullElements(
            @Nullable Set<T> set,
            @Nullable String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException {

        // Assert non null
        innerAssertNonnull(set, variable_name, 1);

        // Assert nonempty if needed
        if (!empty_allowed) innerAssertNonempty(set, variable_name, 1);

        // Check for any nulls
        if (!set.contains(null)) return set;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, NULLS_IN_ARRAY_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, 0);
    }

    /**
     * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if collection is non-empty if requested.
     *
     * @param <T>           type of array
     * @param collection    collection to be asserted
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @return valid nonnull collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static <T> Collection<T> assertNonnullElements(@Nullable Collection<T> collection, boolean empty_allowed)
            throws IllegalArgumentException {
        if (collection instanceof Set) return innerAssertNonnullElements((Set<T>) collection, null, empty_allowed);
        else return innerAssertNonnullElements(collection, null, empty_allowed);
    }

    /**
     * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if collection is non-empty if requested.
     *
     * @param <T>           type of array
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @return valid nonnull collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static <T> Collection<T> assertNonnullElements(
            @Nullable Collection<T> collection,
            @Nonnull String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        if (collection instanceof Set)
            return innerAssertNonnullElements((Set<T>) collection, variable_name, empty_allowed);
        else return innerAssertNonnullElements(collection, variable_name, empty_allowed);
    }

    /**
     * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if collection is non-empty if requested.
     *
     * @param <T>           type of array
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @return valid nonnull collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    private static <T> Collection<T> innerAssertNonnullElements(
            @Nullable Collection<T> collection,
            @Nullable String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException {

        // Assert non null
        innerAssertNonnull(collection, variable_name, 1);

        // Pass it forward
        innerAssertNonnullElements(collection.toArray(new Object[0]), variable_name, empty_allowed, 1);

        // Return it
        return collection;
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param <T>           type of array
     * @param array         array to be asserted
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @return valid nonnull array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static <T> T[] assertNonnullElements(@Nullable T[] array, boolean empty_allowed)
            throws IllegalArgumentException {
        return innerAssertNonnullElements(array, null, empty_allowed, 0);
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param <T>           type of array
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @return valid nonnull array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static <T> T[] assertNonnullElements(
            @Nullable T[] array,
            @Nonnull String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException {
        assertNonnull(variable_name, "variable_name");
        return innerAssertNonnullElements(array, variable_name, empty_allowed, 0);
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param <T>           type of array
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @return valid nonnull array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    @Nonnull
    private static <T> T[] innerAssertNonnullElements(
            @Nullable T[] array,
            @Nullable String variable_name,
            boolean empty_allowed,
            int level
    ) throws IllegalArgumentException {

        // Assert non null
        innerAssertNonnull(array, variable_name, level + 1);

        // Assert nonempty if needed
        if (!empty_allowed) innerAssertNonempty(array, variable_name, level + 1);

        // List to collect any nulls
        List<Integer> nulls = new LinkedList<>();

        // Check the list
        for(int i = 0; i < array.length; i++) if(array[i] == null) nulls.add(i);

        // Exit if nothing is null
        if (nulls.isEmpty()) return array;

        // Otherwise go ahead and throw exception
        String message = composeMessageWithArrays(variable_name, NULLS_IN_ARRAY_MESSAGE, nulls);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, level);
    }

    /**
     * Updates stacktrace in a Throwable to remove or retain some of stacktrace element.
     *
     * @param throwable Throwable to be modified
     * @param level     the default stacktrace removal is 2 elements from the top but this can be adjusted by adding or removing the level
     * @param <T>       Type that extends Throwable
     * @return modified Throwable
     */
    @Nonnull
    private static <T extends Throwable> T updateStackTrace(@Nonnull T throwable, int level){

        // Get STE
        StackTraceElement[] ste = throwable.getStackTrace();

        // Modify stack trace and remove irrelevant parts
        throwable.setStackTrace(Arrays.copyOfRange(ste, 2 + level, ste.length));

        // Return it
        return throwable;
    }
}
