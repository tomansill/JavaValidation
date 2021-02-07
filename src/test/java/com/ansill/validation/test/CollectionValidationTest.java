package com.ansill.validation.test;

import com.ansill.validation.Bypass;
import com.ansill.validation.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validation Test for Collection")
class CollectionValidationTest{

  @DisplayName("Test validating a non-empty collection")
  @Test
  void testNonemptyCollection(){

    assertDoesNotThrow(() -> Validation.assertNonempty(Collections.singleton("something")));

  }

  @DisplayName("Test validating a non-empty collection")
  @Test
  void testNonemptyCollectionWithVariableName(){

    assertDoesNotThrow(() -> Validation.assertNonempty(Collections.singleton("something"), "list"));

  }

  @DisplayName("Test validating an empty collection")
  @Test
  void testEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(Collections.emptyList());
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with variable name")
  @Test
  void testEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(Collections.emptyList(), variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection")
  @Test
  void testNullEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Collection<String>) null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with variable name")
  @Test
  void testNullEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Collection<String>) null, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection with null variable name")
  @Test
  void testEmptyCollectionWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNonempty(Collections.emptyList(), null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a non-empty array")
  @Test
  void testNonemptyArray(){

    assertDoesNotThrow(() -> Validation.assertNonempty(new String[]{"something"}));

  }

  @DisplayName("Test validating a non-empty array")
  @Test
  void testNonemptyArrayWithVariableName(){

    assertDoesNotThrow(() -> Validation.assertNonempty(new String[]{"something"}, "list"));

  }

  @DisplayName("Test validating an empty array")
  @Test
  void testEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(new String[]{});
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with variable name")
  @Test
  void testEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty(new String[]{}, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array")
  @Test
  void testNullEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Object[]) null);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with variable name")
  @Test
  void testNullEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonempty((Object[]) null, variableName);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty array with null variable name")
  @Test
  void testEmptyArrayWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNonempty(new String[]{}, null);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test boolean validating a collection without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCheckCollectionWithoutNulls(String parameters) {

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    assertTrue(Validation.hasAllNonnullElements(collection, true));

  }

  @DisplayName("Test validating a collection without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCollectionWithoutNulls(String parameters) {

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, true));

  }

  @DisplayName("Test validating a collection without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCollectionWithoutNullsWithVariableName(String parameters) {

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, "list", true));

  }

  @DisplayName("Test boolean validating a collection with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCheckCollectionWithNulls(String parameters) {

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    assertFalse(Validation.hasAllNonnullElements(collection, true));

  }

  @DisplayName("Test validating a collection with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCollectionWithNulls(String parameters) {

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        null,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        ValidationTest.findNulls(collection)
      ),
      iae.getMessage()
    );

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCollectionWithNullsWithVariableName(String parameters){

    Collection<String> collection = ValidationTest.convertJson(parameters.replaceAll("'", "\""));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, variableName, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        variableName,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        ValidationTest.findNulls(collection)
      ),
      iae.getMessage()
    );
    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }


  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptyCollection(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptyList(), false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptyCollectionWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptyList(), variableName, false);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls")
  @Test
  void testNullCollectionWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Collection<String>) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with variable name")
  @Test
  void testNullCollectionWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Collection<String>) null, variableName, true);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a collection with nulls with null variable name")
  @Test
  void testCollectionWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNonnullElements(Collections.emptyList(), null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test boolean validating an array without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCheckArrayWithoutNulls(String parameters) {

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertTrue(Validation.hasAllNonnullElements(arrays, true));

  }

  @DisplayName("Test validating an array without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testArrayWithoutNulls(String parameters) {

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertDoesNotThrow(() -> Validation.assertNonnullElements(arrays, true));

  }

  @DisplayName("Test validating an array without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testArrayWithoutNullsWithVariableName(String parameters) {

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertDoesNotThrow(() -> Validation.assertNonnullElements(arrays, "list", true));

  }

  @DisplayName("Test boolean validating an array with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCheckArrayWithNulls(String parameters) {

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    assertFalse(Validation.hasAllNonnullElements(arrays, true));

  }

  @DisplayName("Test validating an array with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testArrayWithNulls(String parameters) {

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(arrays, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        null,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        ValidationTest.findNulls(arrays)
      ),
      iae.getMessage()
    );

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testArrayWithNullsWithVariableName(String parameters){

    String[] arrays = ValidationTest.convertJson(parameters.replaceAll("'", "\"")).toArray(new String[0]);

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(arrays, variableName, true);
      }
    );

    assertEquals(
      Bypass.composeMessageWithArrays(
        variableName,
        Bypass.NULLS_IN_ARRAY_MESSAGE,
        ValidationTest.findNulls(arrays)
      ),
      iae.getMessage()
    );
    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }


  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptyArray(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(new Object[]{}, false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptyArrayWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(new Object[]{}, variableName, false);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls")
  @Test
  void testNullArrayWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Object[]) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with variable name")
  @Test
  void testNullArrayWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Object[]) null, variableName, true);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an array with nulls with null variable name")
  @Test
  void testArrayWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNonnullElements(new Object[]{}, null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test boolean validating a set without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testCheckSetWithoutNulls(String parameters) {

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertTrue(Validation.hasAllNonnullElements(set, true));

  }

  @DisplayName("Test validating a set without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetWithoutNulls(String parameters) {

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(set, true));

  }

  @DisplayName("Test validating a set without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetWithoutNullsWithVariableName(String parameters) {

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(set, "list", true));

  }

  @DisplayName("Test boolean validating a set with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testCheckSetWithNulls(String parameters) {

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertFalse(Validation.hasAllNonnullElements(set, true));
  }

  @DisplayName("Test validating a set with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetWithNulls(String parameters) {

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(set, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetWithNullsWithVariableName(String parameters){

    Set<String> set = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(set, variableName, true);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());
    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members ")
  @Test
  void testNonnullEmptySet(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptySet(), false);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating an empty collection that is supposed to contain nonnull members with variable name")
  @Test
  void testNonnullEmptySetWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(Collections.emptySet(), variableName, false);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.EMPTY_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls")
  @Test
  void testNullSetWithNulls(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Set<String>) null, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with variable name")
  @Test
  void testNullSetWithNullsWithVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements((Set<String>) null, variableName, true);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set with nulls with null variable name")
  @Test
  void testSetWithNullsWithNullVariableName(){

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        //noinspection ConstantConditions
        Validation.assertNonnullElements(Collections.emptySet(), null, true);
      }
    );

    assertEquals(Bypass.composeMessage("variableName", Bypass.OBJECT_NULL_MESSAGE), iae.getMessage());
  }

  @DisplayName("Test validating a set (pretending to be a collection) without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetPretendingToBeCollectionWithoutNulls(String parameters){

    Collection<String> collection = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, true));

  }

  @DisplayName("Test validating a set (pretending to be a collection) without nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', 'else']", "['something']", "[]"})
  void testSetPretendingToBeCollectionWithoutNullsWithVariableName(String parameters){

    Collection<String> collection = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    assertDoesNotThrow(() -> Validation.assertNonnullElements(collection, "list", true));

  }

  @DisplayName("Test validating a set (pretending to be a collection) with nulls")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetPretendingToBeCollectionWithNulls(String parameters){

    Collection<String> collection = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, true);
      }
    );

    assertEquals(Bypass.composeMessage(null, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());

    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }

  @DisplayName("Test validating a set (pretending to be a collection) with nulls with variable name")
  @ParameterizedTest
  @ValueSource(strings = {"['something', null, 'hello']", "[null]", "[null, null, 'hello']"})
  void testSetPretendingToBeCollectionWithNullsWithVariableName(String parameters){

    Collection<String> collection = new HashSet<>(ValidationTest.convertJson(parameters.replaceAll("'", "\"")));

    AtomicReference<StackTraceElement[]> ste = new AtomicReference<>();

    String variableName = "hostname";

    IllegalArgumentException iae = assertThrows(
      IllegalArgumentException.class,
      () -> {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        ste.set(Arrays.copyOfRange(stack, 1, stack.length));
        Validation.assertNonnullElements(collection, variableName, true);
      }
    );

    assertEquals(Bypass.composeMessage(variableName, Bypass.NULLS_IN_ARRAY_MESSAGE), iae.getMessage());
    ValidationTest.assertStackTrace(ste.get()[0], iae.getStackTrace()[0], 2);

  }
}
