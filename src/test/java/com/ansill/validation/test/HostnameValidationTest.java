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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("Validation Test for Hostname")
class HostnameValidationTest{

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

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
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

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
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
              //noinspection ConstantConditions
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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
