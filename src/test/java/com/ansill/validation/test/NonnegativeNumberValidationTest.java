package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validation Test for Nonnegative Number")
class NonnegativeNumberValidationTest{

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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
        //noinspection ConstantConditions
        Validation.assertNonnegative(0, null);
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
