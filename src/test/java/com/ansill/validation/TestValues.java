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
    "domain.with.idn.tld.उदाहरण.परीक्षा",
    //"\uD83D\uDE09.tld", // Figure this out later
    "192.192.129", // Technically valid since it's not IP address, but just a strange FQDN
    "something.else"
  ));

  public static final HashSet<String> VALID_TLD = new HashSet<>(Arrays.asList(
    "com",
    "net",
    "amazon",
    "セール",
    "WEIOFOWIFMW",
    "米",
    "北部湾",
    "娱乐",
    "उदाहरण",
    "परीक्षा"
  ));

  public static final HashSet<String> INVALID_TLD = new HashSet<>(Arrays.asList(
    "  fwaf",
    "--wwf--",
    "$wf#",
    "娱##@@乐",
    "",
    "   ",
    "3200())",
    "com*"
  ));

  public static final HashSet<String> INVALID_HOSTNAMES = new HashSet<>(Arrays.asList(
    "f3q^&*3faw",
    "google.$$#com",
    "cloudf##@lare.com",
    "foo.ba$$#r.something.lse.com",
    "go--ogle.com",
    "192.192.12#$9.112"
  ));

  public static final HashSet<String> VALID_IP_ADDRESSES = new HashSet<>(Arrays.asList(
    "127.0.0.1",
    "192.168.1.1",
    "192.168.1.255",
    "255.255.255.255",
    "0.0.0.0",
    "1.1.1.1",
    "1200:0000:AB00:1234:0000:2552:7777:1313",
    "21DA:D3:0:2F3B:2AA:FF:FE28:9C5A"
  ));

  public static final HashSet<String> INVALID_IP_ADDRESSES = new HashSet<>(Arrays.asList(
    "30.168.1.255.1",
    "127.1",
    "192.168.1.256",
    "-1.2.3.4",
    "1.1.1.1.",
    "3...3",
    "124.14.32.abc",
    "231.54.11.987",
    "23.64.12",
    "1.1.1.01",
    "1200:0000:AB00:1234:O000:2552:7777:1313",
    "1200::AB00:1234::2552:7777:1313"
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
    "firstname-lastname@example.com",
    "test@domain.with.idn.tld.उदाहरण.परीक्षा"
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
    "email@example.com (Joe Smith)",
    "email@example",
    "email@-example.com",
    //"email@example.web", // How is this invalid?
    //"email@111.222.333.44444",
    "email@example..com",
    "Abc..123@example.com"
  ));
}
