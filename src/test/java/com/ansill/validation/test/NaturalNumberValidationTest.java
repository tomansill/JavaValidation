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

@DisplayName("Validation Test for Natural Number")
class NaturalNumberValidationTest{

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an invalid natural number with variable name")
  @ParameterizedTest
  @ValueSource(longs = {0, -1, -2, -3, -4, Long.MIN_VALUE})
  void testInvalidNaturalNumberWithVariableName(long number){

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
  void testInvalidNaturalNumberWithNullVariableName(){

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
