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

@DisplayName("Validation Test for Email Address")
class EmailAddressValidationTest{

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

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
        }
      )
    ).collect(Collectors.toList());
  }


  @DisplayName("Test validating an invalid email address with variable name")
  @TestFactory
  Collection<DynamicTest> testInvalidEmailAddressWithVariableName(){

    String variableName = "email_address";

    return TestValues.INVALID_EMAIL_ADDRESSES.stream().map(item ->
      dynamicTest(item == null ? "null" : item, () -> {
          AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

          IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class,
            () -> {
              StackTraceElement[] stack = Thread.currentThread().getStackTrace();
              ste.set(Arrays.copyOfRange(stack, 1, stack.length));
              assertEquals(item, Validation.assertValidEmailAddress(item, variableName));
            }
          );

        assertEquals(Bypass.composeMessage(variableName, Bypass.INVALID_EMAIL_MESSAGE), iae.getMessage());

          ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);
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
              //noinspection ConstantConditions
              assertEquals(item, Validation.assertValidEmailAddress(item, null));
            }
          );

        assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a null email address with variable name")
  @Test
  void testNullEmailAddressWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "email_address";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertValidEmailAddress(null, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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
        //noinspection ConstantConditions
        Validation.assertValidEmailAddress(null, null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
