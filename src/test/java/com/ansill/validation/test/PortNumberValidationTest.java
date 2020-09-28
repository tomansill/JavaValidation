package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validation Test for Port Number")
class PortNumberValidationTest{

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

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
        //noinspection ConstantConditions
        assertEquals(portnum, Validation.assertValidPortNumber(portnum, null));
      }
    );

    assertEquals(Bypass.composeMessage("variable_name", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }
}
