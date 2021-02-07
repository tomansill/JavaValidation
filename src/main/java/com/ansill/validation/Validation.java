package com.ansill.validation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.net.IDN;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validation class
 */
@Immutable
public final class Validation {

	/**
	 * Message for invalid hostname
	 */
	@Nonnull
	static final String INVALID_EMAIL_MESSAGE = "is expected to be a valid email address but it is actually not a valid email address";

	/**
	 * Message for invalid hostname
	 */
	@Nonnull
	static final String INVALID_HOSTNAME_MESSAGE = "is expected to be a valid hostname but it is actually not a valid hostname";

	/**
	 * Message for invalid ip address
	 */
	@Nonnull
	static final String INVALID_IP_MESSAGE = "is expected to be a valid IP but it is actually not a valid IP";

	/**
	 * Message for invalid port numbers
	 */
	@Nonnull
	static final String INVALID_PORT_MESSAGE = "is expected to be within 1-65535 range but is found to be out of the range";

	/**
	 * Message for object with null values
	 */
	@Nonnull
	static final String OBJECT_NULL_MESSAGE = "is expected to be non-null but is found to be null";

	/**
	 * Message for natural number error
	 */
	@Nonnull
	static final String NATURAL_NUMBER_MESSAGE = "is expected to be a natural number (1, 2, ..., N-1, N) but it is actually not a natural number";

	/**
	 * Message for greater comparison error
	 */
	@Nonnull
	static final String GREATER_NUMBER_MESSAGE = "is expected to be greater number, but it is not";

	/**
	 * Message for lesser comparison error
	 */
	@Nonnull
	static final String LESSER_NUMBER_MESSAGE = "is expected to be lesser number, but it is not";

	/**
	 * Message for non-negative number error
	 */
	@Nonnull
	static final String NONNEGATIVE_NUMBER_MESSAGE = "is expected to be non-negative but value is actually a negative number";

	/**
	 * Message for non-empty string error
	 */
	@Nonnull
	static final String EMPTY_STRING_MESSAGE = "is expected to be non-empty but value is actually a empty string";

	/**
	 * Message for non-empty array/collection error
	 */
	@Nonnull
	static final String EMPTY_ARRAY_MESSAGE = "is expected to be non-empty but value is actually empty";

	/**
	 * Message for nulls in array/collection error
	 */
	@Nonnull
	static final String NULLS_IN_ARRAY_MESSAGE = "is expected to have all of its list members to be non-null but the list contains null members";

	/**
	 * Regex pattern for valid hostname
	 */
	@Nonnull
	private static final Pattern VALID_HOSTNAME_REGEX = Pattern.compile(
		"^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

	/**
	 * Regex pattern for valid IPv4 address
	 */
	@Nonnull
	private static final Pattern VALID_IPV4_REGEX = Pattern.compile(
		"^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$");

	/**
	 * Regex pattern for valid IPv6 address
	 */
	@Nonnull
	private static final Pattern VALID_IPV6_REGEX = Pattern.compile(
		"(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");

	/**
	 * Set of allowed characters for local part in email
	 */
	@Nonnull
	private static final Set<Character> EMAIL_LOCAL_ALLOWED_SPECIAL_CHARS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		'!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^', '_', '`', '{', '|', '}', '~'
	)));

	/**
	 * Set of allowed characters for local part in email
	 */
	@Nonnull
	private static final Set<Character> EMAIL_LOCAL_ALLOWED_SPECIAL_CHARS_WITH_ESCAPE = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
		'\t', '[', '\\', '^', (char) 13, (char) 10, '\"'
	)));

	private Validation() {
		// Prevents any instantiation
		throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
	}

	/**
	 * Composes message with message and optional variable name
	 *
	 * @param variableName variable name to properly reference to. If variable name is empty, then it will be omitted in the message
	 * @param message      message
	 * @return composed message
	 */
	@Nonnull
	static String composeMessage(@Nullable String variableName, @Nonnull String message) {
		String composedMessage = "Value ";
		if (variableName != null) composedMessage += "in variable '" + variableName + "' ";
		composedMessage += message;
		return composedMessage;
	}

	/**
	 * Composes message with message and indices of invalid values and optional variable name
	 *
	 * @param variableName variable name to properly reference to. If variable name is empty, then it will be omitted in the message
	 * @param message      message
	 * @return composed message
	 */
	@Nonnull
	static String composeMessageWithArrays(
		@Nullable String variableName,
		@Nonnull String message,
		@Nonnull List<Integer> indices
	) {
		String composedMessage = ". ";
		if (indices.size() == 1) composedMessage += "The invalid member is at index " + indices.iterator().next();
		else composedMessage += "Invalid members are located at indices " +
			Arrays.toString(indices.toArray(new Integer[0]));
		return composeMessage(variableName, message) + composedMessage;
	}

	/**
	 * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param port port number to be asserted
	 * @return valid port number
	 * @throws IllegalArgumentException thrown if the port number is invalid in any way
	 */
	@Nonnegative
	public static int assertValidPortNumber(int port) throws IllegalArgumentException {
		return innerAssertValidPortNumber(port, null);
	}

	/**
	 * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param port         port number to be asserted
	 * @param variableName name of variable
	 * @return valid port number
	 * @throws IllegalArgumentException thrown if the port number is invalid in any way
	 */
	@Nonnegative
	public static int assertValidPortNumber(int port, @Nonnull String variableName) throws IllegalArgumentException {
		innerAssertNonnull(variableName, "variableName", -1);
		return innerAssertValidPortNumber(port, variableName);
	}

	/**
	 * Asserts that port number is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param port         port number to be asserted
	 * @param variableName name of variable
	 * @return valid port number
	 * @throws IllegalArgumentException thrown if the port number is invalid in any way
	 */
	@Nonnegative
	private static int innerAssertValidPortNumber(int port, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if valid
		if (isPortNumberValid(port)) return port;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, INVALID_PORT_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	// TODO docs
	public static boolean isPortNumberValid(int port) {

		// Return result
		return port > 0 && port <= 65535;
	}

	/**
	 * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param hostname hostname to be asserted
	 * @return valid hostname
	 * @throws IllegalArgumentException thrown if the hostname is invalid in any way
	 */
	@Nonnull
	public static String assertValidHostname(@Nullable String hostname) throws IllegalArgumentException {
		return innerAssertValidHostname(hostname, null);
	}

	/**
	 * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param hostname     hostname to be asserted
	 * @param variableName name of variable
	 * @return valid hostname
	 * @throws IllegalArgumentException thrown if the hostname is invalid in any way
	 */
	@Nonnull
	public static String assertValidHostname(@Nullable String hostname, @Nonnull String variableName)
		throws IllegalArgumentException {
		innerAssertNonnull(variableName, "variableName", -1);
		return innerAssertValidHostname(hostname, variableName);
	}

	/**
	 * Asserts that hostname is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param hostname     hostname to be asserted
	 * @param variableName name of variable
	 * @return valid hostname
	 * @throws IllegalArgumentException thrown if the hostname is invalid in any way
	 */
	@Nonnull
	private static String innerAssertValidHostname(@Nullable String hostname, @Nullable String variableName)
		throws IllegalArgumentException {

		// Assert non null
		hostname = innerAssertNonnull(hostname, variableName, 1);

		// Exit if valid
		if (isHostnameValid(hostname)) return hostname;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, INVALID_HOSTNAME_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	// TODO docs
	public static boolean isHostnameValid(@Nullable String hostname) {

		// Return false if null
		if (hostname == null) return false;

		// Valid IP is acceptable
		if (isIPAddressValid(hostname)) return true;

		// Split the dots
		String[] split = hostname.split("\\.");

		// Fail if not enough dots
		if (split.length < 2) return false;

		// Get last entry, test it for TLD
		if (!isTopLevelDomainValid(split[split.length - 1])) return false;

		// Test all other remaining dots
		for (int splitIndex = 0; splitIndex < split.length - 1; splitIndex++) {

			// Get string
			String part = split[splitIndex];

			// Make sure it's not blank
			if (part.trim().isEmpty()) return false;

			// Iterate through characters
			for (int charIndex = 0; charIndex < part.length(); charIndex++) {

				// Get character
				char character = part.charAt(charIndex);

				// All good if alphanumeric
				if (Character.isLetterOrDigit(character)) continue;

				// All good if punycode
				if (isPunyCode(character)) continue;

				// If hyphen, make sure only one hyphen and is not at either ends
				if (character == '-') {
					if (charIndex == 0) return false;
					if (charIndex == (part.length() - 1)) return false;
					if (part.charAt(charIndex - 1) == '-') return false;
					continue;
				}

				// Fail f arrived here
				return false;
			}

		}

		// True if fallen through to here
		return true;
	}

	/**
	 * Asserts that IP address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param address IP address to be asserted
	 * @return valid IP address
	 * @throws IllegalArgumentException thrown if the IP address is invalid in any way
	 */
	@Nonnull
	public static String assertValidIPAddress(@Nullable String address) throws IllegalArgumentException {
		return innerAssertValidIPAddress(address, null);
	}

	/**
	 * Asserts that IP address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param address      IP address to be asserted
	 * @param variableName name of variable
	 * @return valid IP address
	 * @throws IllegalArgumentException thrown if the IP address is invalid in any way
	 */
	@Nonnull
	public static String assertValidIPAddress(@Nullable String address, @Nonnull String variableName)
		throws IllegalArgumentException {
		innerAssertNonnull(variableName, "variableName", -1);
		return innerAssertValidIPAddress(address, variableName);
	}

	/**
	 * Asserts that IP address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param address      IP address to be asserted
	 * @param variableName name of variable
	 * @return valid IP address
	 * @throws IllegalArgumentException thrown if the IP address is invalid in any way
	 */
	@Nonnull
	private static String innerAssertValidIPAddress(@Nullable String address, @Nullable String variableName)
		throws IllegalArgumentException {

		// Assert non null
		address = innerAssertNonnull(address, variableName, 1);

		// Exit if valid
		if (isIPAddressValid(address)) return address;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, INVALID_IP_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	// TODO docs
	public static boolean isIPAddressValid(@Nullable String address) {

		// Return false if null
		if (address == null) return false;

		// Exit if valid
		return VALID_IPV4_REGEX.matcher(address).matches() || VALID_IPV6_REGEX.matcher(address).matches();
	}

	/**
	 * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param emailAddress email address to be asserted
	 * @return valid email address
	 * @throws IllegalArgumentException thrown if the email address is invalid in any way
	 */
	@Nonnull
	public static String assertValidEmailAddress(@Nullable String emailAddress) throws IllegalArgumentException {
		return innerAssertValidEmailAddress(emailAddress, null);
	}

	/**
	 * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param emailAddress email address to be asserted
	 * @param variableName name of variable
	 * @return valid email address
	 * @throws IllegalArgumentException thrown if the email address is invalid in any way
	 */
	@Nonnull
	public static String assertValidEmailAddress(@Nullable String emailAddress, @Nonnull String variableName)
		throws IllegalArgumentException {
		innerAssertNonnull(variableName, "variableName", -1);
		return innerAssertValidEmailAddress(emailAddress, variableName);
	}

	/**
	 * Asserts that email address is valid. If it is invalid, then an exception will be thrown.
	 *
	 * @param emailAddress email address to be asserted
	 * @param variableName name of variable
	 * @return valid email address
	 * @throws IllegalArgumentException thrown if the email address is invalid in any way
	 */
	@Nonnull
	private static String innerAssertValidEmailAddress(@Nullable String emailAddress, @Nullable String variableName)
		throws IllegalArgumentException {

		// Assert non null
		emailAddress = innerAssertNonnull(emailAddress, variableName, 1);

		// Assert nonempty
		emailAddress = innerAssertNonemptyString(emailAddress, variableName, 1);

		// Check email
		if (isEmailAddressValid(emailAddress)) return emailAddress;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, INVALID_EMAIL_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Checks if character is indeed a punycode
	 *
	 * @param character character to be tested
	 * @return true if punycode, false if not punycode
	 */
	private static boolean isPunyCode(char character) {

		try {

			// Attempt to convert char to punycode
			String str = IDN.toASCII(character + "", IDN.USE_STD3_ASCII_RULES);

			// Check if valid punycode
			if (str.startsWith("xn--")) return true;

		} catch (IllegalArgumentException e) {
			// Do nothing - let it flow
		}

		// Return false
		return false;
	}

	/**
	 * Checks if provided Top Level Domain name string is indeed valid Top Level Domain name
	 *
	 * @param tld string to be tested
	 * @return true if valid, false if not valid
	 */
	public static boolean isTopLevelDomainValid(@Nullable String tld) {

		// Null check
		if (tld == null) return false;

		// Blank check
		if (tld.trim().isEmpty()) return false;

		// Scan through characters
		for (int i = 0; i < tld.length(); i++) {

			// Get character
			char character = tld.charAt(i);

			// If alphanumeric, then check if in list of approved unicode
			if (Character.isLetterOrDigit(character)) continue;

			// Check if punycode
			if (isPunyCode(character)) continue;

			// Otherwise false
			return false;
		}

		// True if reached here
		return true;
	}

	// TODO docs
	private static boolean checkEmailAddressLocalPart(@Nonnull String recipient) {

		// Check if blank
		if (recipient.trim().isEmpty()) return false;

		// 64 characters maximum
		if (recipient.length() > 64) return false;

		// Iterate over string
		boolean quoted = false;
		for (int charIndex = 0; charIndex < recipient.length(); charIndex++) {

			// Get character
			char character = recipient.charAt(charIndex);

			// Check if escape
			boolean escaped = false;
			if (character == '\\') {

				// If not in quoted space, hard fail
				if (!quoted) return false;

				// Toggle it
				escaped = true;

				// Check if there's next character
				if (charIndex + 1 >= recipient.length() - 1) return false;

				// Get next character
				char nextCharacter = recipient.charAt(charIndex + 1);

				// Check if escape is reasonable
				if (!EMAIL_LOCAL_ALLOWED_SPECIAL_CHARS_WITH_ESCAPE.contains(nextCharacter)) return false;
			}

			// Handle quotes
			if (!escaped && character == '"') quoted = !quoted;

			// Enter non-quoted mode
			if (!quoted) {

				// Handle if dot
				if (character == '.') {

					// Must not begin or end
					if (charIndex == 0 || charIndex == recipient.length() - 1) return false;

					// Must not successive, get prev and compare
					if (recipient.charAt(charIndex - 1) == '.') return false;

					// Continue
					continue;
				}

				// Check allowed chars
				if (Character.isLetterOrDigit(character)) continue;

				// Check if punycode
				if (isPunyCode(character)) continue;

				// Check specials
				if (!EMAIL_LOCAL_ALLOWED_SPECIAL_CHARS.contains(character)) return false;
			}
		}

		// Fail if not quote is not closed, otherwise success
		return !quoted;
	}

	// TODO docs
	// http://rumkin.com/software/email/rules.php
	public static boolean isEmailAddressValid(@Nullable String emailAddress) {

		// Null check
		if (emailAddress == null) return false;

		// Blank check
		if (emailAddress.trim().isEmpty()) return false;

		// Lowercase email address
		emailAddress = emailAddress.toLowerCase();

		// Search fo @ symbol
		int split = emailAddress.indexOf("@");

		// Make sure @ symbol exists
		if (split == -1) return false;

		// Split it
		String first = emailAddress.substring(0, split); // Guaranteed no @ in this one, so no need to check that
		String second = emailAddress.substring(split + 1);

		// Check first one
		if (!checkEmailAddressLocalPart(first)) return false;

		// Check second one
		return checkEmailHostnamePart(second);
	}

	// TODO docs
	private static boolean checkEmailHostnamePart(@Nonnull String hostname) {

		// If hostname is valid, then shortcut
		if (isHostnameValid(hostname)) return true;

		// Check if bracketed
		if (hostname.charAt(0) == '[') {

			// Make sure there's a bracket on other side
			if (hostname.charAt(hostname.length() - 1) != ']') return false;

			// Check length
			if (hostname.length() >= 245) return false;

			// Substring then do hostname check again
			return isHostnameValid(hostname.substring(1, hostname.length() - 1));
		}

		// False if arrived here
		return false;
	}

	/**
	 * Asserts that object is not null. If it is null, then an exception will be thrown.
	 *
	 * @param <T>    return value type
	 * @param object object to be asserted
	 * @return non-null object
	 * @throws IllegalArgumentException thrown if the object is invalid in any way
	 */
	@Nonnull
	public static <T> T assertNonnull(@Nullable T object) throws IllegalArgumentException {
		return innerAssertNonnull(object, null, 0);
	}

	/**
	 * Asserts that object is not null. If it is null, then an exception will be thrown.
	 *
	 * @param <T>          return value type
	 * @param object       object to be asserted
	 * @param variableName name of variable
	 * @return non-null object
	 * @throws IllegalArgumentException thrown if the object is invalid in any way
	 */
	@Nonnull
	public static <T> T assertNonnull(@Nullable T object, @Nonnull String variableName)
		throws IllegalArgumentException {
		innerAssertNonnull(variableName, "variableName", -1);
		return innerAssertNonnull(object, variableName, 0);
	}

	/**
	 * Asserts that object is not null. If it is null, then an exception will be thrown.
	 *
	 * @param <T>          object type
	 * @param object       object to be asserted
	 * @param variableName name of variable
	 * @param level        level of calls. This is used to adjust the stacktrace.
	 * @return nonnull object
	 * @throws IllegalArgumentException thrown if the object is invalid in any way
	 */
	@Nonnull
	private static <T> T innerAssertNonnull(@Nullable T object, @Nullable String variableName, int level)
		throws IllegalArgumentException {

		// Exit if not null
		if (object != null) return object;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, OBJECT_NULL_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, level);
	}

	/**
	 * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertGreaterThan(int number, long compare) throws IllegalArgumentException {
		return (int) innerAssertGreaterThan(number, compare, null);
	}

	/**
	 * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertGreaterThan(int number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return (int) innerAssertGreaterThan(number, compare, variableName);
	}

	/**
	 * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertGreaterThan(long number, long compare) throws IllegalArgumentException {
		return innerAssertGreaterThan(number, compare, null);
	}

	/**
	 * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertGreaterThan(long number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertGreaterThan(number, compare, variableName);
	}

	/**
	 * Asserts that number is a greater than compared number. If it is not a greater an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	private static long innerAssertGreaterThan(long number, long compare, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if not null
		if (number > compare) return number;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, GREATER_NUMBER_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertLesserThan(int number, long compare) throws IllegalArgumentException {
		return (int) innerAssertLesserThan(number, compare, null);
	}

	/**
	 * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertLesserThan(int number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return (int) innerAssertLesserThan(number, compare, variableName);
	}

	/**
	 * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertLesserThan(long number, long compare) throws IllegalArgumentException {
		return innerAssertLesserThan(number, compare, null);
	}

	/**
	 * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertLesserThan(long number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertLesserThan(number, compare, variableName);
	}


	/**
	 * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertGreaterThanOrEqual(int number, long compare) throws IllegalArgumentException {
		return (int) innerAssertGreaterThanOrEqual(number, compare, null);
	}

	/**
	 * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertGreaterThanOrEqual(int number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return (int) innerAssertGreaterThanOrEqual(number, compare, variableName);
	}

	/**
	 * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
	 *
	 * @param number  number to be asserted
	 * @param compare number being compared
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertGreaterThanOrEqual(long number, long compare) throws IllegalArgumentException {
		return innerAssertGreaterThanOrEqual(number, compare, null);
	}

	/**
	 * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertGreaterThanOrEqual(long number, long compare, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertGreaterThanOrEqual(number, compare, variableName);
	}

	/**
	 * Asserts that number is a greater than or equals to compared number. If it is not greater or equal, an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	private static long innerAssertGreaterThanOrEqual(long number, long compare, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if not null
		if (number > compare) return number;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, GREATER_NUMBER_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Asserts that number is a lesser than compared number. If it is not a lesser an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param compare      number being compared
	 * @param variableName name of variable
	 * @return valid number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	private static long innerAssertLesserThan(long number, long compare, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if not null
		if (number < compare) return number;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, LESSER_NUMBER_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
	 *
	 * @param number number to be asserted
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertNaturalNumber(int number) throws IllegalArgumentException {
		return (int) innerAssertNaturalNumber(number, null);
	}

	/**
	 * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param variableName name of variable
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static int assertNaturalNumber(int number, @Nonnull String variableName) throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return (int) innerAssertNaturalNumber(number, variableName);
	}

	/**
	 * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
	 *
	 * @param number number to be asserted
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertNaturalNumber(long number) throws IllegalArgumentException {
		return innerAssertNaturalNumber(number, null);
	}

	/**
	 * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param variableName name of variable
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertNaturalNumber(long number, @Nonnull String variableName) throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNaturalNumber(number, variableName);
	}

	/**
	 * Asserts that number is a natural number. If it is not a natural number, then an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param variableName name of variable
	 * @return valid natural number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	private static long innerAssertNaturalNumber(long number, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if not null
		if (number > 0) return number;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, NATURAL_NUMBER_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
	 *
	 * @param number number to be asserted
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertNonnegative(long number) throws IllegalArgumentException {
		return innerAssertNonnegative(number, null);
	}

	/**
	 * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param variableName name of variable
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	public static long assertNonnegative(long number, @Nonnull String variableName) throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonnegative(number, variableName);
	}

	/**
	 * Asserts that number is not a negative number. If it is a negative number, then an exception will be thrown.
	 *
	 * @param number       number to be asserted
	 * @param variableName name of variable
	 * @return valid nonnegative number
	 * @throws IllegalArgumentException thrown if the number is invalid in any way
	 */
	private static long innerAssertNonnegative(long number, @Nullable String variableName)
		throws IllegalArgumentException {

		// Exit if not null
		if (number >= 0) return number;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, NONNEGATIVE_NUMBER_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	/**
	 * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
	 * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
	 *
	 * @param string string to be asserted
	 * @return valid nonempty string
	 * @throws IllegalArgumentException thrown if the string is invalid in any way
	 */
	public static String assertNonemptyString(@Nullable String string) throws IllegalArgumentException {
		return innerAssertNonemptyString(string, null, 0);
	}

	/**
	 * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
	 * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
	 *
	 * @param string       string to be asserted
	 * @param variableName name of variable
	 * @return valid nonempty string
	 * @throws IllegalArgumentException thrown if the string is invalid in any way
	 */
	public static String assertNonemptyString(@Nullable String string, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonemptyString(string, variableName, 0);
	}

	/**
	 * Asserts that string is not an empty string. If it is an empty string, then an exception will be thrown.
	 * White space is considered empty space so if string consists of four spaces with nothing else, then it is considered to be empty.
	 *
	 * @param string       string to be asserted
	 * @param variableName name of variable
	 * @param level        level of calls. This is used to adjust the stacktrace.
	 * @return valid nonempty string
	 * @throws IllegalArgumentException thrown if the string is invalid in any way
	 */
	private static String innerAssertNonemptyString(
		@Nullable String string,
		@Nullable String variableName,
		@Nonnegative int level
	) throws IllegalArgumentException {

		// Assert non null
		string = innerAssertNonnull(string, variableName, level + 1);

		// Exit if not empty
		if (!string.trim().equals("")) return string;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, EMPTY_STRING_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, level);
	}

	/**
	 * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>        type of collection
	 * @param collection collection to be asserted
	 * @return valid nonempty collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	public static <T> Collection<T> assertNonempty(@Nullable Collection<T> collection) throws IllegalArgumentException {
		return innerAssertNonempty(collection, null, 0);
	}

	/**
	 * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>          type of collection
	 * @param collection   collection to be asserted
	 * @param variableName name of variable
	 * @return valid nonempty collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	public static <T> Collection<T> assertNonempty(@Nullable Collection<T> collection, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonempty(collection, variableName, 0);
	}

	/**
	 * Asserts that collection is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>          type of collection
	 * @param collection   collection to be asserted
	 * @param variableName name of variable
	 * @param level        level of calls. This is used to adjust the stacktrace.
	 * @return valid nonempty collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	private static <T> Collection<T> innerAssertNonempty(
		@Nullable Collection<T> collection,
		@Nullable String variableName,
		int level
	)
		throws IllegalArgumentException {

		// Assert non null
		innerAssertNonnull(collection, variableName, 1);

		// Pass it forward
		innerAssertNonempty(collection.toArray(new Object[0]), variableName, 1 + level);

		// Return it
		return collection;
	}

	/**
	 * Asserts that array is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>   type of array
	 * @param array array to be asserted
	 * @return valid nonempty array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	public static <T> T[] assertNonempty(@Nullable T[] array) throws IllegalArgumentException {
		return innerAssertNonempty(array, null, 0);
	}

	/**
	 * Asserts that array is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>          type of array
	 * @param array        array to be asserted
	 * @param variableName name of variable
	 * @return valid nonempty array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	public static <T> T[] assertNonempty(@Nullable T[] array, @Nonnull String variableName)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonempty(array, variableName, 0);
	}

	/**
	 * Asserts that array is not empty. If it is empty, then an exception will be thrown.
	 *
	 * @param <T>          type of array
	 * @param array        array to be asserted
	 * @param variableName name of variable
	 * @param level        level of calls. This is used to adjust the stacktrace.
	 * @return valid nonempty array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	private static <T> T[] innerAssertNonempty(@Nullable T[] array, @Nullable String variableName, int level)
		throws IllegalArgumentException {

		// Assert non null
		innerAssertNonnull(array, variableName, level + 1);

		// Exit if not empty
		if (Objects.requireNonNull(array).length != 0) return array;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, EMPTY_ARRAY_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, level);
	}

	/**
	 * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if set is non-empty if requested.
	 *
	 * @param <T>          type of array
	 * @param set          set to be asserted
	 * @param emptyAllowed true to allow an empty set, false to assert a non-empty set
	 * @return valid nonnull set
	 * @throws IllegalArgumentException thrown if the set is invalid in any way
	 */
	public static <T> Set<T> assertNonnullElements(@Nullable Set<T> set, boolean emptyAllowed)
		throws IllegalArgumentException {
		return innerAssertNonnullElements(set, null, emptyAllowed);
	}

	/**
	 * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if set is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param set           set to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty set, false to assert a non-empty set
	 * @return valid nonnull set
	 * @throws IllegalArgumentException thrown if the set is invalid in any way
	 */
	public static <T> Set<T> assertNonnullElements(
		@Nullable Set<T> set,
		@Nonnull String variableName,
		boolean emptyAllowed
	)
		throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonnullElements(set, variableName, emptyAllowed);
	}

	/**
	 * Asserts that set does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if set is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param set           set to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty set, false to assert a non-empty set
	 * @return valid nonnull set
	 * @throws IllegalArgumentException thrown if the set is invalid in any way
	 */
	private static <T> Set<T> innerAssertNonnullElements(
		@Nullable Set<T> set,
		@Nullable String variableName,
		boolean emptyAllowed
	) throws IllegalArgumentException {

		// Assert non null
		innerAssertNonnull(set, variableName, 1);

		// Assert nonempty if needed
		if (!emptyAllowed) innerAssertNonempty(set, variableName, 1);

		// Check for any nulls
		if (!set.contains(null)) return set;

		// Otherwise go ahead and throw exception
		String message = composeMessage(variableName, NULLS_IN_ARRAY_MESSAGE);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, 0);
	}

	// TODO docs
	public static boolean containsNonnullElements(@Nullable Set<?> set, boolean emptyAllowed) {

		// Assert non null
		if (set == null) return false;

		// Assert nonempty if needed
		if (!emptyAllowed && set.isEmpty()) return false;

		// Check for any nulls
		return !set.contains(null);
	}

	// TODO docs
	public static boolean containsNonnullElements(@Nullable Object[] array, boolean emptyAllowed) {

		// Assert non null
		if (array == null) return false;

		// Assert nonempty if needed
		if (!emptyAllowed && array.length == 0) return false;

		// Check for any nulls
		return Arrays.stream(array).noneMatch(Objects::isNull);
	}

	// TODO docs
	public static boolean containsNonnullElements(@Nullable Collection<?> collection, boolean emptyAllowed) {

		// Assert non null
		if (collection == null) return false;

		// Assert nonempty if needed
		if (!emptyAllowed && collection.isEmpty()) return false;

		// Check for any nulls
		return collection.stream().noneMatch(Objects::isNull);
	}

	/**
	 * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if collection is non-empty if requested.
	 *
	 * @param <T>          type of array
	 * @param collection   collection to be asserted
	 * @param emptyAllowed true to allow an empty collection, false to assert a non-empty collection
	 * @return valid nonnull collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	public static <T> Collection<T> assertNonnullElements(@Nullable Collection<T> collection, boolean emptyAllowed)
		throws IllegalArgumentException {
		if (collection instanceof Set) return innerAssertNonnullElements((Set<T>) collection, null, emptyAllowed);
		else return innerAssertNonnullElements(collection, null, emptyAllowed);
	}

	/**
	 * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if collection is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param collection    collection to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty collection, false to assert a non-empty collection
	 * @return valid nonnull collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	public static <T> Collection<T> assertNonnullElements(
		@Nullable Collection<T> collection,
		@Nonnull String variableName,
		boolean emptyAllowed
	) throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		if (collection instanceof Set)
			return innerAssertNonnullElements((Set<T>) collection, variableName, emptyAllowed);
		else return innerAssertNonnullElements(collection, variableName, emptyAllowed);
	}

	/**
	 * Asserts that collection does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if collection is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param collection    collection to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty collection, false to assert a non-empty collection
	 * @return valid nonnull collection
	 * @throws IllegalArgumentException thrown if the collection is invalid in any way
	 */
	private static <T> Collection<T> innerAssertNonnullElements(
		@Nullable Collection<T> collection,
		@Nullable String variableName,
		boolean emptyAllowed
	) throws IllegalArgumentException {

		// Assert non null
		innerAssertNonnull(collection, variableName, 1);

		// Pass it forward
		innerAssertNonnullElements(collection.toArray(new Object[0]), variableName, emptyAllowed, 1);

		// Return it
		return collection;
	}

	/**
	 * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if array is non-empty if requested.
	 *
	 * @param <T>          type of array
	 * @param array        array to be asserted
	 * @param emptyAllowed true to allow an empty array, false to assert a non-empty array
	 * @return valid nonnull array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	public static <T> T[] assertNonnullElements(@Nullable T[] array, boolean emptyAllowed)
		throws IllegalArgumentException {
		return innerAssertNonnullElements(array, null, emptyAllowed, 0);
	}

	/**
	 * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if array is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param array         array to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty array, false to assert a non-empty array
	 * @return valid nonnull array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	public static <T> T[] assertNonnullElements(
		@Nullable T[] array,
		@Nonnull String variableName,
		boolean emptyAllowed
	) throws IllegalArgumentException {
		assertNonnull(variableName, "variableName");
		return innerAssertNonnullElements(array, variableName, emptyAllowed, 0);
	}

	/**
	 * Asserts that array does not contain any null elements. If it does contain null elements, then an exception will be thrown.
	 * Additionally, it can assert if array is non-empty if requested.
	 *
	 * @param <T>           type of array
	 * @param array         array to be asserted
	 * @param variableName  name of variable
	 * @param emptyAllowed true to allow an empty array, false to assert a non-empty array
	 * @param level         level of calls. This is used to adjust the stacktrace.
	 * @return valid nonnull array
	 * @throws IllegalArgumentException thrown if the array is invalid in any way
	 */
	@Nonnull
	private static <T> T[] innerAssertNonnullElements(
		@Nullable T[] array,
		@Nullable String variableName,
		boolean emptyAllowed,
		int level
	) throws IllegalArgumentException {

		// Assert non null
		innerAssertNonnull(array, variableName, level + 1);

		// Assert nonempty if needed
		if (!emptyAllowed) innerAssertNonempty(array, variableName, level + 1);

		// List to collect any nulls
		List<Integer> nulls = new LinkedList<>();

		// Check the list
		for (int i = 0; i < array.length; i++) if (array[i] == null) nulls.add(i);

		// Exit if nothing is null
		if (nulls.isEmpty()) return array;

		// Otherwise go ahead and throw exception
		String message = composeMessageWithArrays(variableName, NULLS_IN_ARRAY_MESSAGE, nulls);

		// Create exception
		IllegalArgumentException iae = new IllegalArgumentException(message);

		// Update stacktrace and throw it
		throw updateStackTrace(iae, level);
	}

	/**
	 * Updates stacktrace in a Throwable to remove or retain some of stacktrace element.
	 *
	 * @param throwable Throwable to be modified
	 * @param level     the default stacktrace removal is 2 elements from the top but this can be adjusted by adding or removing the level
	 * @param <T>       Type that extends Throwable
	 * @return modified Throwable
	 */
	@Nonnull
	private static <T extends Throwable> T updateStackTrace(@Nonnull T throwable, int level) {

		// Get STE
		StackTraceElement[] ste = throwable.getStackTrace();

		// Modify stack trace and remove irrelevant parts
		throwable.setStackTrace(Arrays.copyOfRange(ste, 2 + level, ste.length));

		// Return it
		return throwable;
	}
}
