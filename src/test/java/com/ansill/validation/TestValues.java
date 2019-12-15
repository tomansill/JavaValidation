package com.ansill.validation;

import java.util.Arrays;
import java.util.HashSet;

public class TestValues {
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
            "test@jaymail.com",
            "help@google.com",
            "helpdesk@google.com"
    ));

    public static final HashSet<String> INVALID_EMAIL_ADDRESSES = new HashSet<>(Arrays.asList(
            "34f3gagf",
            "help@googl@e.com",
            "helpdesk@google"
    ));
}
