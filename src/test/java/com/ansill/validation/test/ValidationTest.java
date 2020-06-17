package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.TestValues;
import com.ansill.validation.Validation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SuppressWarnings({"rawtypes", "ConstantConditions"})
class ValidationTest{

  static Collection<String> convertJson(String json){
    Type list_type = new TypeToken<List<String>>(){
    }.getType();
    return new Gson().fromJson(json, list_type);
  }

  static List<Integer> findNulls(Collection collection){
    return findNulls(collection.toArray(new Object[0]));
  }

  static List<Integer> findNulls(Object[] array){
    List<Integer> nulls = new LinkedList<>();
    for(int i = 0; i < array.length; i++) if(array[i] == null) nulls.add(i);
    return nulls;
  }

  @SuppressWarnings("SameParameterValue")
  static void assertStackTrace(StackTraceElement ste1, StackTraceElement ste2, int line_offset){
    assertEquals(ste1.getFileName(), ste2.getFileName());
    assertEquals(ste1.getClassName(), ste2.getClassName());
    assertEquals(ste1.getLineNumber() + line_offset, ste2.getLineNumber());
  }

  @DisplayName("Test validating a valid email address")
  @TestFactory
  Collection<DynamicTest> testValidEmailAddress(){
    return TestValues.VALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidEmailAddress(item))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a valid email address with variable name")
  @TestFactory
  Collection<DynamicTest> testValidEmailAddressWithVariableName(){
    return TestValues.VALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidEmailAddress(item, "hostname"))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating an invalid email address")
  @TestFactory
  Collection<DynamicTest> testInvalidEmailAddress(){

    return TestValues.INVALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidEmailAddress(item));
            }
          );

          assertEquals(Bypass.composeMessage(null, Bypass.INVALID_EMAIL_MESSAGE), iae.getMessage());

          assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());
  }


  @DisplayName("Test validating an invalid email address with variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidEmailAddressWithVariableName(){

    String variable_name = "email_address";

    return TestValues.INVALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidEmailAddress(item, variable_name));
            }
          );

          assertEquals(Bypass.composeMessage(variable_name, Bypass.INVALID_EMAIL_MESSAGE), iae.getMessage());

          assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());

  }

  @DisplayName("Test validating an invalid email address with null variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidEmailAddressWithNullVariableName(){

    return TestValues.INVALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidEmailAddress(item, null));
            }
          );

          assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
        }
      )
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a null email address")
  @Test
  void testNullEmailAddress(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidEmailAddress(null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null email address with variable name")
  @Test
  void testNullEmailAddressWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "email_address";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidEmailAddress(null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null email address with null variable name")
  @Test
  void testNullEmailAddressWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidEmailAddress(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a valid hostname")
  @TestFactory
  Collection<DynamicTest> testValidHostname(){
    return TestValues.VALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidHostname(item))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a valid hostname with variable name")
  @TestFactory
  Collection<DynamicTest> testValidHostnameWithVariableName(){
    return TestValues.VALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidHostname(item, "hostname"))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating an invalid hostname")
  @TestFactory
  Collection<DynamicTest> testInvalidHostname(){

    return TestValues.INVALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidHostname(item));
            }
          );

          assertEquals(Bypass.composeMessage(null, Bypass.INVALID_HOSTNAME_MESSAGE), iae.getMessage());

          assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());
  }


  @DisplayName("Test validating an invalid hostname with variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidHostnameWithVariableName(){

    String variable_name = "hostname";

    return TestValues.INVALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidHostname(item, variable_name));
            }
          );

          assertEquals(Bypass.composeMessage(variable_name, Bypass.INVALID_HOSTNAME_MESSAGE), iae.getMessage());

          assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());

  }

  @DisplayName("Test validating an invalid hostname with null variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidHostnameWithNullVariableName(){

    return TestValues.INVALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidHostname(item, null));
            }
          );

          assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
        }
      )
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a hostname object")
  @Test
  void testNullHostname(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidHostname(null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null object with variable name")
  @Test
  void testNullHostnameWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidHostname(null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null hostname with null variable name")
  @Test
  void testNullHostnameWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidHostname(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a valid port number")
  @ParameterizedTest
  @ValueSource(strings = {"1", "22", "80", "2991", "8080", "23323", "65535"})
  void testValidPortNumber(String port){
    int portnum = Integer.parseInt(port);
    assertDoesNotThrow(() -> assertEquals(portnum, Validation.assertValidPortNumber(portnum)));
  }

  @DisplayName("Test validating a valid port number with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"1", "22", "80", "2991", "8080", "23323", "65535"})
  void testValidPortNumberWithValidVariableName(String port){
    int portnum = Integer.parseInt(port);
    assertDoesNotThrow(() -> assertEquals(portnum, Validation.assertValidPortNumber(portnum, "port_number")));

  }

  @DisplayName("Test validating an invalid port number")
  @ParameterizedTest
  @ValueSource(strings = {"0", "-1231", "3523523"})
  void testInvalidPortNumber(String port){
    int portnum = Integer.parseInt(port);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        assertEquals(portnum, Validation.assertValidPortNumber(portnum));
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.INVALID_PORT_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }


  @DisplayName("Test validating an invalid port number with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"0", "-1231", "3523523"})
  void testInvalidPortNumberWithVariableName(String port){
    int portnum = Integer.parseInt(port);
    String variable_name = "port_number";

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        assertEquals(portnum, Validation.assertValidPortNumber(portnum, variable_name));
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.INVALID_PORT_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid port number with null variable name")
  @ParameterizedTest
  @ValueSource(strings = {"0", "-1231", "3523523"})
  void testInvalidPortNumberWithNullVariableName(String port){
    int portnum = Integer.parseInt(port);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        assertEquals(portnum, Validation.assertValidPortNumber(portnum, null));
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a valid object")
  @Test
  void testValidObject(){

    assertDoesNotThrow(() -> Validation.assertNonnull("hello"));

  }

  @DisplayName("Test validating a valid object with variable name")
  @Test
  void testValidObjectWithVariableName(){

    assertDoesNotThrow(() -> Validation.assertNonnull("hello", "greetings"));

  }

  @DisplayName("Test validating a null object")
  @Test
  void testNullObject(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnull(null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null object with variable name")
  @Test
  void testNullObjectWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnull(null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null object with null variable name")
  @Test
  void testNullObjectWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnull(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a valid natural number")
  @ParameterizedTest
  @ValueSource(longs = {1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNaturalNumber(long number){

    assertDoesNotThrow(() -> Validation.assertNaturalNumber(number));

  }

  @DisplayName("Test validating a valid natural number")
  @ParameterizedTest
  @ValueSource(longs = {1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNaturalNumberWithVariableName(long number){

    assertDoesNotThrow(() -> Validation.assertNaturalNumber(number, "number"));

  }

  @DisplayName("Test validating an invalid natural number")
  @ParameterizedTest
  @ValueSource(longs = {0, -1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNaturalNumber(long number){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNaturalNumber(number);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NATURAL_NUMBER_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with variable name")
  @ParameterizedTest
  @ValueSource(longs = {0, -1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNaturalNumberWithVariableName(long number){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNaturalNumber(number, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.NATURAL_NUMBER_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with null variable name")
  @Test
  void testInvalidNaturalNumberWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNaturalNumber(0, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a valid nonnegative number")
  @ParameterizedTest
  @ValueSource(longs = {0, 1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNonnegativeNumber(long number){

    assertDoesNotThrow(() -> Validation.assertNonnegative(number));

  }

  @DisplayName("Test validating a valid nonnegative number")
  @ParameterizedTest
  @ValueSource(longs = {0, 1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNonnegativeNumberWithVariableName(long number){

    assertDoesNotThrow(() -> Validation.assertNonnegative(number, "number"));

  }

  @DisplayName("Test validating an invalid nonnegative number")
  @ParameterizedTest
  @ValueSource(longs = {-1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNonnegativeNumber(long number){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnegative(number);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NONNEGATIVE_NUMBER_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid nonnegative number with variable name")
  @ParameterizedTest
  @ValueSource(longs = {-1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNonnegativeNumberWithVariableName(long number){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnegative(number, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.NONNEGATIVE_NUMBER_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid nonnegative number with null variable name")
  @Test
  void testInvalidNonnegativeNumberWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnegative(0, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a non-empty string")
  @ParameterizedTest
  @ValueSource(strings = {"hello", ". ", " happy  "})
  void testNonemptyString(String string){

    assertDoesNotThrow(() -> Validation.assertNonemptyString(string));

  }

  @DisplayName("Test validating a non-empty string")
  @ParameterizedTest
  @ValueSource(strings = {"hello", ". ", " happy  "})
  void testNonemptyStringWithVariableName(String string){

    assertDoesNotThrow(() -> Validation.assertNonemptyString(string, "number"));

  }

  @DisplayName("Test validating an empty string")
  @ParameterizedTest
  @ValueSource(strings = {"", " ", "   "})
  void testEmptyString(String string){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(string);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_STRING_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"", " ", "   "})
  void testEmptyStringWithVariableName(String string){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(string, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_STRING_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string")
  @Test
  void testNullEmptyString(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string with variable name")
  @Test
  void testNullEmptyStringWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string with null variable name")
  @Test
  void testEmptyStringWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(" ", null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

  }

  @DisplayName("Test validating a non-empty collection")
  @Test
  void testNonemptyCollection(){

    assertDoesNotThrow(() -> Validation.assertNonempty(Collections.singleton("something")));

  }

  @DisplayName("Test validating a non-empty collection")
  @Test
  void testNonemptyCollectionWithVariableName(){

    assertDoesNotThrow(() -> Validation.assertNonempty(Collections.singleton("something"), "list"));

  }

  @DisplayName("Test validating an empty collection")
  @Test
  void testEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(Collections.emptyList());
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with variable name")
  @Test
  void testEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(Collections.emptyList(), variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection")
  @Test
  void testNullEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Collection<String>) null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with variable name")
  @Test
  void testNullEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Collection<String>) null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with null variable name")
  @Test
  void testEmptyCollectionWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(Collections.emptyList(), null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a non-empty array")
  @Test
  void testNonemptyArray(){

    assertDoesNotThrow(() -> Validation.assertNonempty(new String[]{"something"}));

  }

  @DisplayName("Test validating a non-empty array")
  @Test
  void testNonemptyArrayWithVariableName(){

    assertDoesNotThrow(() -> Validation.assertNonempty(new String[]{"something"}, "list"));

  }

  @DisplayName("Test validating an empty array")
  @Test
  void testEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(new String[]{});
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with variable name")
  @Test
  void testEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(new String[]{}, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array")
  @Test
  void testNullEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Object[]) null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with variable name")
  @Test
  void testNullEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Object[]) null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with null variable name")
  @Test
  void testEmptyArrayWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(new String[]{}, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a collection without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCollectionWithoutNulls(String parameters){

    Collection<String> collection = convertJson(parameters.replaceAll("'", "\""));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, true));

  }

  @DisplayName("Test validating a collection without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCollectionWithoutNullsWithVariableName(String parameters){

    Collection<String> collection = convertJson(parameters.replaceAll("'", "\""));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, "list", true));

  }

  @DisplayName("Test validating a collection with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCollectionWithNulls(String parameters){

    Collection<String> collection = convertJson(parameters.replaceAll("'", "\""));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        null,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        findNulls(collection)
      ),
      iae.getMessage()
    );

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCollectionWithNullsWithVariableName(String parameters){

    Collection<String> collection = convertJson(parameters.replaceAll("'", "\""));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, variable_name, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        variable_name,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        findNulls(collection)
      ),
      iae.getMessage()
    );
    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }


  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptyList(), false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptyList(), variable_name, false);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls")
  @Test
  void testNullCollectionWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Collection<String>) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with variable name")
  @Test
  void testNullCollectionWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Collection<String>) null, variable_name, true);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with null variable name")
  @Test
  void testCollectionWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptyList(), null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating an array without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testArrayWithoutNulls(String parameters){

    String[] arrays = convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertDoesNotThrow(() -> Validation.assertNonnullElements(arrays, true));

  }

  @DisplayName("Test validating an array without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testArrayWithoutNullsWithVariableName(String parameters){

    String[] arrays = convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertDoesNotThrow(() -> Validation.assertNonnullElements(arrays, "list", true));

  }

  @DisplayName("Test validating an array with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testArrayWithNulls(String parameters){

    String[] arrays = convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(arrays, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        null,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        findNulls(arrays)
      ),
      iae.getMessage()
    );

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testArrayWithNullsWithVariableName(String parameters){

    String[] arrays = convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(arrays, variable_name, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        variable_name,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        findNulls(arrays)
      ),
      iae.getMessage()
    );
    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }


  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(new Object[]{}, false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(new Object[]{}, variable_name, false);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls")
  @Test
  void testNullArrayWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Object[]) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with variable name")
  @Test
  void testNullArrayWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Object[]) null, variable_name, true);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with null variable name")
  @Test
  void testArrayWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(new Object[]{}, null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a set without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetWithoutNulls(String parameters){

    Set<String> set = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(set, true));

  }

  @DisplayName("Test validating a set without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetWithoutNullsWithVariableName(String parameters){

    Set<String> set = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(set, "list", true));

  }

  @DisplayName("Test validating a set with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetWithNulls(String parameters){

    Set<String> set = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(set, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetWithNullsWithVariableName(String parameters){

    Set<String> set = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(set, variable_name, true);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());
    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptySet(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptySet(), false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptySetWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptySet(), variable_name, false);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls")
  @Test
  void testNullSetWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Set<String>) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with variable name")
  @Test
  void testNullSetWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Set<String>) null, variable_name, true);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with null variable name")
  @Test
  void testSetWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptySet(), null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a set (pretending to be a collection) without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetPretendingToBeCollectionWithoutNulls(String parameters){

    Collection<String> collection = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, true));

  }

  @DisplayName("Test validating a set (pretending to be a collection) without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetPretendingToBeCollectionWithoutNullsWithVariableName(String parameters){

    Collection<String> collection = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, "list", true));

  }

  @DisplayName("Test validating a set (pretending to be a collection) with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetPretendingToBeCollectionWithNulls(String parameters){

    Collection<String> collection = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());

    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set (pretending to be a collection) with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetPretendingToBeCollectionWithNullsWithVariableName(String parameters){

    Collection<String> collection = new HashSet<>(convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, variable_name, true);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());
    assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }
}