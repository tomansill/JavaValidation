package com.ansill.validation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Class that bypass the access mechanisms and to be used for testing
 */
public final class Bypass{

  @Nonnull
  public static final String INVALID_EMAIL_MESSAGE = Validation.INVALID_EMAIL_MESSAGE;

  @Nonnull
  public static final String INVALID_HOSTNAME_MESSAGE = Validation.INVALID_HOSTNAME_MESSAGE;

  @Nonnull
  public static final String INVALID_IP_MESSAGE = Validation.INVALID_IP_MESSAGE;

  @Nonnull
  public static final String INVALID_PORT_MESSAGE = Validation.INVALID_PORT_MESSAGE;

  @Nonnull
  public static final String OBJECT_NULL_MESSAGE = Validation.OBJECT_NULL_MESSAGE;

  @Nonnull
  public static final String NATURAL_NUMBER_MESSAGE = Validation.NATURAL_NUMBER_MESSAGE;

  @Nonnull
  public static final String NONNEGATIVE_NUMBER_MESSAGE = Validation.NONNEGATIVE_NUMBER_MESSAGE;

  @Nonnull
  public static final String EMPTY_STRING_MESSAGE = Validation.EMPTY_STRING_MESSAGE;

  @Nonnull
  public static final String EMPTY_ARRAY_MESSAGE = Validation.EMPTY_ARRAY_MESSAGE;

  @Nonnull
  public static final String NULLS_IN_ARRAY_MESSAGE = Validation.NULLS_IN_ARRAY_MESSAGE;

  private Bypass(){
  }

  public static String composeMessage(@Nullable String variable_name, @Nonnull String message){
    return Validation.composeMessage(variable_name, message);
  }

  public static String composeMessageWithArrays(
    @Nullable String variable_name,
    @Nonnull String message,
    @Nonnull List<Integer> indices
  ){
    return Validation.composeMessageWithArrays(variable_name, message, indices);
  }

}
