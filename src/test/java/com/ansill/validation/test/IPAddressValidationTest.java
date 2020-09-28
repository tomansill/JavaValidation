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

@DisplayName("Validation Test for IP Address")
class IPAddressValidationTest{

  @DisplayName("Test validating a valid IP address")
  @TestFactory
  Collection<DynamicTest> testValidIPAddress(){
    return TestValues.VALID_IP_ADDRESSES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidIPAddress(item))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a valid IP address with variable name")
  @TestFactory
  Collection<DynamicTest> testValidIPAddressWithVariableName(){
    return TestValues.VALID_IP_ADDRESSES.stream().map(item ->
      dynamicTest(item, () ->
        assertDoesNotThrow(() -> assertEquals(item, Validation.assertValidIPAddress(item, "IPAddress"))))
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating an invalid IP address")
  @TestFactory
  Collection<DynamicTest> testInvalidIPAddress(){

    return TestValues.INVALID_IP_ADDRESSES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidIPAddress(item));
            }
          );

          assertEquals(Bypass.composeMessage(null, Bypass.INVALID_IP_MESSAGE), iae.getMessage());

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());
  }


  @DisplayName("Test validating an invalid IP address with variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidIPAddressWithVariableName(){

    String variable_name = "IPAddress";

    return TestValues.INVALID_IP_ADDRESSES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidIPAddress(item, variable_name));
            }
          );

          assertEquals(Bypass.composeMessage(variable_name, Bypass.INVALID_IP_MESSAGE), iae.getMessage());

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());

  }

  @DisplayName("Test validating an invalid IP address with null variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidIPAddressWithNullVariableName(){

    return TestValues.INVALID_IP_ADDRESSES.stream().map(item ->
      dynamicTest(item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              //noinspection ConstantConditions
              assertEquals(item, Validation.assertValidIPAddress(item, null));
            }
          );

          assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
        }
      )
    ).collect(Collectors.toList());
  }

  @DisplayName("Test validating a IPAddress object")
  @Test
  void testNullIPAddress(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidIPAddress(null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null object with variable name")
  @Test
  void testNullIPAddressWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variable_name = "IPAddress";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidIPAddress(null, variable_name);
      }
    );

    assertEquals(Bypass.composeMessage(variable_name, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null IPAddress with null variable name")
  @Test
  void testNullIPAddressWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertValidIPAddress(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
