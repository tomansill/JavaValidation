package com.ansill.validation;

import java.util.Arrays;
import java.util.HashSet;

public class TestValues{
  public static final HashSet<String> VALID_HOSTNAMES = new HashSet<>(Arrays.asList(
    "ansill.com",
    "google.com",
    "cloudflare.com",
    "foo.bar.something.lse.com",
    "192.192.129.112",
    "something.else"
  ));

  public static final HashSet<String> INVALID_HOSTNAMES = new HashSet<>(Arrays.asList(
    "f3q^&*3faw",
    "google.$$#com",
    "cloudf##@lare.com",
    "foo.ba$$#r.something.lse.com",
    "192.192.12#$9.112"
  ));

  public static final HashSet<String> VALID_EMAIL_ADDRESSES = new HashSet<>(Arrays.asList(
    "email@example.com",
    "firsStname.lastname@example.com",
    "firstn123ame.lastname@example.com",
    "f7L6G@fake.com",
    "email@subdomain.example.com",
    "firstname+lastname@example.com",
    "email@123.123.123.123",
    "email@[123.123.123.123]",
    //"“email”@example.com",
    "1234567890@example.com",
    "email@example-one.com",
    "_______@example.com",
    "email@example.name",
    "email@example.museum",
    "email@example.co.jp",
    "firstname-lastname@example.com"
    //"much.“more\\ unusual”@example.com",
    //"very.unusual.“@”.unusual.com@example.com",
    //"very.“(),:;<>[]”.VERY.“very@\\\\ \"very”.unusual@strange.example.com"
  ));

  public static final HashSet<String> INVALID_EMAIL_ADDRESSES = new HashSet<>(Arrays.asList(
    "plainaddress",
    "#@%^%#$@#$@#.com",
    "@example.com",
    "Joe Smith <email@example.com>",
    "email.example.com",
    "email@example@example.com",
    ".email@example.com",
    //"email.@example.com", // How is this invalid?
    "email..email@example.com",
    "あいうえお@example.com",
    "email@example.com (Joe Smith)",
    "email@example",
    "email@-example.com",
    //"email@example.web", // How is this invalid?
    //"email@111.222.333.44444",
    "email@example..com",
    "Abc..123@example.com"
  ));
}
