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

@DisplayName("Validation Test for String")
class StringValidationTest{

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"", " ", "   "})
  void testEmptyStringWithVariableName(String string){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(string, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_STRING_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty string with variable name")
  @Test
  void testNullEmptyStringWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonemptyString(null, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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
        //noinspection ConstantConditions
        Validation.assertNonemptyString(" ", null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

  }
}
