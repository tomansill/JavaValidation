package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.Validation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"rawtypes", "ConstantConditions"})
@DisplayName("Validation Test")
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

}