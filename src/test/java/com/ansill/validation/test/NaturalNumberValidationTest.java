package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Validation Test for Natural Number")
class NaturalNumberValidationTest {

  @DisplayName("Test validating a valid natural number (integer)")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, Integer.MAX_VALUE})
  void testValidNaturalNumberLong(int number) {

    assertDoesNotThrow(() -> assertEquals(number, Validation.assertNaturalNumber(number)));

  }

  @DisplayName("Test validating a valid natural number (integer)")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, Integer.MAX_VALUE})
  void testValidNaturalNumberWithVariableNameLong(int number) {

    assertDoesNotThrow(() -> assertEquals(number, Validation.assertNaturalNumber(number, "number")));

  }

  @DisplayName("Test validating a valid natural number (long)")
  @ParameterizedTest
  @ValueSource(longs = {1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNaturalNumberLong(long number) {

    assertDoesNotThrow(() -> assertEquals(number, Validation.assertNaturalNumber(number)));

  }

  @DisplayName("Test validating a valid natural number (long)")
  @ParameterizedTest
  @ValueSource(longs = {1, 2, 3, 4, 5, Long.MAX_VALUE})
  void testValidNaturalNumberWithVariableNameLong(long number) {

    assertDoesNotThrow(() -> assertEquals(number, Validation.assertNaturalNumber(number, "number")));

  }

  @DisplayName("Test validating an invalid natural number (integer)")
  @ParameterizedTest
  @ValueSource(ints = {0, -1, -2, -3, -4, Integer.MIN_VALUE})
  void testInvalidNaturalNumber(int number) {

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with variable name (integer)")
  @ParameterizedTest
  @ValueSource(ints = {0, -1, -2, -3, -4, Integer.MIN_VALUE})
  void testInvalidNaturalNumberWithVariableName(int number) {

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNaturalNumber(number, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.NATURAL_NUMBER_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number (long)")
  @ParameterizedTest
  @ValueSource(longs = {0, -1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNaturalNumber(long number) {

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with variable name (long)")
  @ParameterizedTest
  @ValueSource(longs = {0, -1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNaturalNumberWithVariableName(long number) {

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNaturalNumber(number, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.NATURAL_NUMBER_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with null variable name")
  @Test
  void testInvalidNaturalNumberWithNullVariableName() {

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNaturalNumber(0, null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
