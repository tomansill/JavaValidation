package com.ansill.validation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Validation class */
@Immutable
public final class Validation{

    /** Message for object with null values */
    @Nonnull
    static final String OBJECT_NULL_MESSAGE = "is expected to be non-null but is found to be null";

    /** Message for natural number error */
    @Nonnull
    static final String NATURAL_NUMBER_MESSAGE = "is expected to be a natural number (1, 2, ..., N-1, N) but it is actually not a natural number";

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
    ){
        String composed_message = ". ";
        if(indices.size() == 1) composed_message += "The invalid member is at index " + indices.iterator().next();
        else composed_message += "Invalid members are located at indices " +
                                 Arrays.toString(indices.toArray(new Integer[0]));
        return composeMessage(variable_name, message) + composed_message;
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param object object to be asserted
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    public static void assertNonnull(@Nullable Object object) throws IllegalArgumentException{
        innerAssertNonnull(object, null, 0);
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param object        object to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    public static void assertNonnull(@Nullable Object object, @Nonnull String variable_name)
    throws IllegalArgumentException{
        innerAssertNonnull(variable_name, "variable_name", -1);
        innerAssertNonnull(object, variable_name, 0);
    }

    /**
     * Asserts that object is not null. If it is null, then an exception will be thrown.
     *
     * @param object        object to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @throws IllegalArgumentException thrown if the object is invalid in any way
     */
    private static void innerAssertNonnull(@Nullable Object object, @Nullable String variable_name, int level)
    throws IllegalArgumentException{

        // Exit if not null
        if(object != null) return;

        // Otherwise go ahead and throw exception
        String message = composeMessage(variable_name, OBJECT_NULL_MESSAGE);

        // Create exception
        IllegalArgumentException iae = new IllegalArgumentException(message);

        // Update stacktrace and throw it
        throw updateStackTrace(iae, level);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number number to be asserted
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static void assertNaturalNumber(long number) throws IllegalArgumentException{
        innerAssertNaturalNumber(number, null);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static void assertNaturalNumber(long number, @Nonnull String variable_name) throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNaturalNumber(number, variable_name);
    }

    /**
     * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static void innerAssertNaturalNumber(long number, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Exit if not null
        if(number > 0) return;

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
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static void assertNonnegative(long number) throws IllegalArgumentException{
        innerAssertNonnegative(number, null);
    }

    /**
     * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    public static void assertNonnegative(long number, @Nonnull String variable_name) throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonnegative(number, variable_name);
    }

    /**
     * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
     *
     * @param number        number to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the number is invalid in any way
     */
    private static void innerAssertNonnegative(long number, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Exit if not null
        if(number >= 0) return;

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
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    public static void assertNonemptyString(@Nullable String string) throws IllegalArgumentException{
        innerAssertNonemptyString(string, null);
    }

    /**
     * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
     * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
     *
     * @param string        string to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    public static void assertNonemptyString(@Nullable String string, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonemptyString(string, variable_name);
    }

    /**
     * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
     * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
     *
     * @param string        string to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the string is invalid in any way
     */
    private static void innerAssertNonemptyString(@Nullable String string, @Nullable String variable_name)
    throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(string, variable_name, 1);

        // Exit if not null
        if(!Objects.requireNonNull(string).trim().equals("")) return;

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
     * @param collection collection to be asserted
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static void assertNonempty(@Nullable Collection collection) throws IllegalArgumentException{
        innerAssertNonempty(collection, null, 0);
    }

    /**
     * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
     *
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static void assertNonempty(@Nullable Collection collection, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonempty(collection, variable_name, 0);
    }

    /**
     * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
     *
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    private static void innerAssertNonempty(@Nullable Collection collection, @Nullable String variable_name, int level)
    throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(collection, variable_name, 1);

        // Pass it forward
        innerAssertNonempty(collection.toArray(new Object[0]), variable_name, 1 + level);
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param array array to be asserted
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static void assertNonempty(@Nullable Object[] array) throws IllegalArgumentException{
        innerAssertNonempty(array, null, 0);
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static void assertNonempty(@Nullable Object[] array, @Nonnull String variable_name)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonempty(array, variable_name, 0);
    }

    /**
     * Asserts that array is not empty. If it is empty, then an exception will be thrown.
     *
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    private static void innerAssertNonempty(@Nullable Object[] array, @Nullable String variable_name, int level)
    throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(array, variable_name, level + 1);

        // Exit if not null
        if(Objects.requireNonNull(array).length != 0) return;

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
     * @param set           set to be asserted
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    public static void assertNonnullElements(@Nullable Set set, boolean empty_allowed) throws IllegalArgumentException{
        innerAssertNonnullElements(set, null, empty_allowed);
    }

    /**
     * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if set is non-empty if requested.
     *
     * @param set           set to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    public static void assertNonnullElements(@Nullable Set set, @Nonnull String variable_name, boolean empty_allowed)
    throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonnullElements(set, variable_name, empty_allowed);
    }

    /**
     * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if set is non-empty if requested.
     *
     * @param set           set to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty set, false to assert a non-empty set
     * @throws IllegalArgumentException thrown if the set is invalid in any way
     */
    private static void innerAssertNonnullElements(
            @Nullable Set set,
            @Nullable String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(set, variable_name, 1);

        // Assert nonempty if needed
        if(!empty_allowed) innerAssertNonempty(set, variable_name, 1);

        // Check for any nulls
        if(!set.contains(null)) return;

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
     * @param collection    collection to be asserted
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static void assertNonnullElements(@Nullable Collection collection, boolean empty_allowed)
    throws IllegalArgumentException{
        if(collection instanceof Set) innerAssertNonnullElements((Set) collection, null, empty_allowed);
        else innerAssertNonnullElements(collection, null, empty_allowed);
    }

    /**
     * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if collection is non-empty if requested.
     *
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    public static void assertNonnullElements(
            @Nullable Collection collection,
            @Nonnull String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        if(collection instanceof Set) innerAssertNonnullElements((Set) collection, variable_name, empty_allowed);
        else innerAssertNonnullElements(collection, variable_name, empty_allowed);
    }

    /**
     * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if collection is non-empty if requested.
     *
     * @param collection    collection to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty collection, false to assert a non-empty collection
     * @throws IllegalArgumentException thrown if the collection is invalid in any way
     */
    private static void innerAssertNonnullElements(
            @Nullable Collection collection,
            @Nullable String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(collection, variable_name, 1);

        // Pass it forward
        innerAssertNonnullElements(collection.toArray(new Object[0]), variable_name, empty_allowed, 1);
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param array         array to be asserted
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static void assertNonnullElements(@Nullable Object[] array, boolean empty_allowed)
    throws IllegalArgumentException{
        innerAssertNonnullElements(array, null, empty_allowed, 0);
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    public static void assertNonnullElements(
            @Nullable Object[] array,
            @Nonnull String variable_name,
            boolean empty_allowed
    ) throws IllegalArgumentException{
        assertNonnull(variable_name, "variable_name");
        innerAssertNonnullElements(array, variable_name, empty_allowed, 0);
    }

    /**
     * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
     * Additionally, it can assert if array is non-empty if requested.
     *
     * @param array         array to be asserted
     * @param variable_name name of variable
     * @param empty_allowed true to allow an empty array, false to assert a non-empty array
     * @param level         level of calls. This is used to adjust the stacktrace.
     * @throws IllegalArgumentException thrown if the array is invalid in any way
     */
    private static void innerAssertNonnullElements(
            @Nullable Object[] array,
            @Nullable String variable_name,
            boolean empty_allowed,
            int level
    ) throws IllegalArgumentException{

        // Assert non null
        innerAssertNonnull(array, variable_name, level + 1);

        // Assert nonempty if needed
        if(!empty_allowed) innerAssertNonempty(array, variable_name, level + 1);

        // List to collect any nulls
        List<Integer> nulls = new LinkedList<>();

        // Check the list
        for(int i = 0; i < array.length; i++) if(array[i] == null) nulls.add(i);

        // Exit if nothing is null
        if(nulls.isEmpty()) return;

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
