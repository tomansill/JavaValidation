package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.TestValues;
import com.ansill.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("Validation Test for Hostname")
class HostnameValidationTest {

  @DisplayName("Test validating a valid Top Level Domain name")
  @TestFactory
  Collection<DynamicTest> testValidTLD() {
    return TestValues.VALID_TLD.stream().map(item ->
      dynamicTest("Testing '" + item + "'", () ->
        assertTrue(Validation.isTopLevelDomainValid(item)))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating an invalid Top Level Domain name")
  @TestFactory
  Collection<DynamicTest> testInvalidTLD() {
    return TestValues.INVALID_TLD.stream().map(item ->
      dynamicTest("Testing '" + item + "'", () ->
        assertFalse(Validation.isTopLevelDomainValid(item)))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a valid hostname")
  @TestFactory
  Collection<DynamicTest> testValidHostname() {
    return Stream.concat(TestValues.VALID_HOSTNAMES.stream(), TestValues.VALID_IP_ADDRESSES.stream()).map(item ->
      dynamicTest("Testing '" + item + "'", () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidHostname(item))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a valid hostname with variable name")
  @TestFactory
  Collection<DynamicTest> testValidHostnameWithVariableName(){
    return Stream.concat(TestValues.VALID_HOSTNAMES.stream(), TestValues.VALID_IP_ADDRESSES.stream()).map(item ->
      dynamicTest("Testing '" + item + "'", () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidHostname(item, "hostname"))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating an invalid hostname")
  @TestFactory
  Collection<DynamicTest> testInvalidHostname() {

    return TestValues.INVALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item == null ? "Testing null" : "Testing '" + item + "'", () -> {
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

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());
  }


  @DisplayName("Test validating an invalid hostname with variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidHostnameWithVariableName() {

    String variableName = "hostname";

    return TestValues.INVALID_HOSTNAMES.stream().map(item ->
      dynamicTest(item == null ? "Testing null" : "Testing '" + item + "'", () -> {
        AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

        IllegalArgumentException iae = assertThrows(
          IllegalArgumentException.class,
          () -> {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            ste.set(Arrays.copyOfRange(stack, 1, stack.length));
            assertEquals(item, Validation.assertValidHostname(item, variableName));
          }
        );

        assertEquals(Bypass.composeMessage(variableName, Bypass.INVALID_HOSTNAME_MESSAGE), iae.getMessage());

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());

  }

  @DisplayName("Test validating an invalid hostname with null variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidHostnameWithNullVariableName(){

    return Stream.concat(TestValues.INVALID_HOSTNAMES.stream(), TestValues.INVALID_IP_ADDRESSES.stream()).map(item ->
      dynamicTest("Testing '" + item + "'", () -> {
        AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

        IllegalArgumentException iae = assertThrows(
          IllegalArgumentException.class,
          () -> {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            ste.set(Arrays.copyOfRange(stack, 1, stack.length));
            //noinspection ConstantConditions
            assertEquals(item, Validation.assertValidHostname(item, null));
          }
          );

        assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null object with variable name")
  @Test
  void testNullHostnameWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidHostname(null, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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
        //noinspection ConstantConditions
        Validation.assertValidHostname(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
