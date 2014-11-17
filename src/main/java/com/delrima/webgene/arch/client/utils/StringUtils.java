package com.delrima.webgene.arch.client.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <code><B>GWTStringUtils<code><B>
 * <p/>
 * String Utilities for the String
 * <p/>
 * 
 * @author Michael.Wang@ihg.com
 * @since Sep 25, 2008
 */

public abstract class StringUtils {

	public static final String PROPERTY_NAME_DELIMITER = ".";
	public static final String NULL = "null";
	public static final String TRUE_VALUE = "true";
	public static final String YES_TRUE_VALUE = "yes";
	public static final String SPACE = " ";

	/**
	 * <p>
	 * Return "" if parameter = null
	 * </p>
	 * 
	 * @param pValue
	 * @return
	 */
	public static String getBlankIfNull(Object pValue) {
		if (pValue == null || pValue.equals("null")) {
			return "";
		}
		return pValue.toString().trim();
	}

	/**
	 * Returns false if the value is null or empty ("") or "null" value.
	 * 
	 * @param pValue
	 *            - the value to check.
	 * @return false if the value is null or empty "" or "null" value.
	 */
	public static boolean isSet(final String pValue) {
		if (pValue == null || pValue.equalsIgnoreCase("") || pValue.trim().equalsIgnoreCase(NULL)) {
			return false;
		}

		return true;
	}

	/**
	 * isSet
	 * <p>
	 * Return false if list is null or empty
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isSet(final List<String> value) {
		if (value == null || value.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the string value passed equals "true" or "yes".
	 * 
	 * @param pValue
	 *            - the value.
	 * @return true if the string passed is true.
	 */
	public static boolean isTrue(final String pValue) {
		if (!isSet(pValue)) {
			return false;
		}

		final String newValue = pValue.toLowerCase().trim();

		if (newValue.equals(TRUE_VALUE)) {
			return true;
		}

		if (newValue.equals(YES_TRUE_VALUE)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if the string is contained within the array of strings passed.
	 * 
	 * @param pArray
	 *            - the array of strings.
	 * @param pValue
	 *            - the string to check for.
	 * @param pIgnoreCase
	 *            - set to true to ignore case.
	 */
	public static boolean containsString(final String[] pArray, final String pValue, final boolean pIgnoreCase) {
		if (pValue == null) {
			return false;
		}

		if (pArray == null) {
			return false;
		}

		for (final String value : pArray) {

			if (value != null) {

				if (pIgnoreCase) {
					if (pValue.equalsIgnoreCase(value.trim())) {
						return true;
					}
				} else {
					if (pValue.equals(value.trim())) {
						return true;
					}
				}
			}

		}

		return false;
	}

	/**
	 * Returns true if the string arrays are equal. If either string is null then false is returned.
	 * 
	 * @param pArray1
	 *            - array 1.
	 * @param pArray2
	 *            - array 2.
	 * @param pIgnoreCase
	 *            - the ignore case flag.
	 * @return true if the string arrays are equal. If either string is null then false is returned.
	 */
	public static boolean equals(final String[] pArray1, final String[] pArray2, final boolean pIgnoreCase) {

		// We're not dealing with nulls.
		if (pArray1 == null || pArray2 == null) {
			return false;
		}

		if (pArray1.length != pArray2.length) {
			return false;
		}

		for (final String element : pArray1) {
			if (!containsString(pArray2, element, pIgnoreCase)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if the strings are equal. If either string is null then false is returned. This is a case sensitive check.
	 * 
	 * @param pValue1
	 *            - value 1.
	 * @param pValue2
	 *            - value 2.
	 * @return true if the strings are equal. If either string is null then false is returned.
	 */
	public static boolean equals(final String pValue1, final String pValue2) {
		return equals(pValue1, pValue2, false);
	}

	/**
	 * Returns true if the strings are equal. If either string is null then false is returned.
	 * 
	 * @param pValue1
	 *            - value 1.
	 * @param pValue2
	 *            - value 2.
	 * @param pIgnoreCase
	 *            - the ignore case flag.
	 * @return true if the strings are equal. If either string is null then false is returned.
	 */
	public static boolean equals(final String pValue1, final String pValue2, final boolean pIgnoreCase) {
		// We're not dealing with nulls.
		if (pValue1 == null || pValue2 == null) {
			return false;
		}

		if (pIgnoreCase) {
			// Ignore the case.
			if (pValue1.equalsIgnoreCase(pValue2)) {
				return true;
			}

			return false;
		}

		if (pValue1.equals(pValue2)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if the strings are equal. If either string is null then false is returned. This ignores case.
	 * 
	 * @param pValue1
	 *            - value 1.
	 * @param pValue2
	 *            - value 2.
	 * @return true if the strings are equal. If either string is null then false is returned.
	 */
	public static boolean equalsIgnoreCase(final String pValue1, final String pValue2) {
		return equals(pValue1, pValue2, true);
	}

	/**
	 * Replaces a token in a string with a value.
	 * 
	 * @param pInString
	 *            - the original string value.
	 * @param pToken
	 *            - the string value.
	 * @param pValue
	 *            - the string value to be replace where token exists.
	 * @return the new string with replaced value
	 */
	public static String replace(final String pInString, final String pToken, final String pValue) {
		if (pInString == null || pToken == null || pValue == null) {
			return null;
		}

		final int index = pInString.indexOf(pToken);

		// If token doesn't exist in pInString, then no replace is needed
		if (index == -1) {
			return pInString;
		}

		final StringBuilder stringBuf = new StringBuilder();
		stringBuf.append(pInString.substring(0, index)); // Get everything
		// before token
		stringBuf.append(pValue); // insert new value
		// Recurse for multiple instaces of token
		stringBuf.append(replace(pInString.substring(index + pToken.length()), pToken, pValue));

		return stringBuf.toString();
	}

	/**
	 * Returns boolean indicating if String has ONLY digits.
	 * 
	 * @param pValue
	 *            - the value.
	 * @return boolean indicating if String has ONLY digits.
	 */
	public static boolean hasOnlyDigits(String pValue) {

		if (pValue == null) {
			return true;
		}

		char chars[] = pValue.toCharArray();

		for (int idx = 0; idx < chars.length; idx++) {
			if (!Character.isDigit(chars[idx])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns boolean indicating if String has no digits.
	 * 
	 * @param pValue
	 *            - the value.
	 * @return boolean indicating if String has no digits.
	 */
	public static boolean hasNoDigits(final String pValue) {
		if (pValue == null) {
			return true;
		}

		final char chars[] = pValue.toCharArray();

		for (final char element : chars) {
			if (Character.isDigit(element)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns string with non digit characters removed.
	 * 
	 * @param pValue
	 *            - the value.
	 * @return string with characters removed.
	 */
	public static String removeNonDigits(final String pValue) {
		final StringBuilder returnValue = new StringBuilder();
		final char chars[] = pValue.toCharArray();

		for (final char element : chars) {
			if (Character.isDigit(element)) {
				returnValue.append(element);
			}
		}

		return returnValue.toString();
	}

	/**
	 * This method formats a (given) input value to mask out part of the value to hide sensitive data.
	 * 
	 * @param value
	 *            - value which need to be mask
	 * @return the mask string.
	 */
	public static String mask(String value) {
		String maskChar = "X";
		int numOfCharToDisplay = 4;
		return mask(value, maskChar, numOfCharToDisplay);
	}

	/**
	 * This method formats a (given) input value to mask out part of the value to hide sensitive data.
	 * 
	 * @param value
	 *            - value which need to be mask
	 * @param maskChar
	 *            - Mask Character
	 * @param numOfCharToDisplay
	 *            - Number of char which are not required to mask
	 * @return the mask string.
	 */
	public static String mask(String value, String maskChar, int numOfCharToDisplay) {
		if (!isSet(maskChar)) {
			maskChar = "X";
		}
		if (numOfCharToDisplay <= 0) {
			numOfCharToDisplay = 4;
		}

		// calculate number of chars to mask
		int sizeOfString = value.length();
		int numCharsToMask = sizeOfString - numOfCharToDisplay;
		if (numCharsToMask < 0) {
			numCharsToMask = sizeOfString;
		}

		StringBuffer valueStringBuffer = new StringBuffer(value);
		// mask the chars
		for (int i = 0; i < numCharsToMask; i++) {
			valueStringBuffer.replace(i, i + 1, maskChar);
		}
		return valueStringBuffer.toString();
	}

	/**
	 * This methods check the (given) input value is AlphaNumeric or not
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isAlphaNumeric(final String value) {
		final char[] chars = value.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if ((c >= 'a') && (c <= 'z')) {
				continue; // lowercase
			}
			if ((c >= 'A') && (c <= 'Z')) {
				continue; // uppercase
			}
			if ((c >= '0') && (c <= '9')) {
				continue; // numeric
			}
			return false;
		}
		return true;
	}

	/**
	 * Returns string with whitespace removed.
	 * 
	 * @param pValue
	 *            - the value.
	 * @return string with whitespace removed.
	 */
	public static String removeWhiteSpace(final String pValue) {
		if (isSet(pValue)) {

			final StringBuilder returnValue = new StringBuilder();
			final char chars[] = pValue.toCharArray();

			for (final char element : chars) {
				if (element != ' ') {
					returnValue.append(element);
				}
			}

			return returnValue.toString();
		}

		return null;
	}

	// ------------------------------------
	/**
	 * Returns boolean indicating if String has no whitespace.
	 * 
	 * @param pValue
	 *            - the value.
	 * @return boolean indicating if String has no whitespace.
	 */
	public static boolean hasNoWhiteSpace(final String pValue) {
		if (pValue == null) {
			return true;
		}

		if (pValue.indexOf(' ') != -1) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * Converts the first letter of a String to Capital letter
	 */

	public static String firstLetterCapital(String pString) {

		if (!isSet(pString)) {

			return pString;
		}

		String formattedString = "";
		// get the first letter
		String firstLetter = pString.substring(0, 1);
		// set the first letter to upper case
		firstLetter = firstLetter.toUpperCase();
		if (pString.length() > 1) {
			// set the rest of the city to lower case
			String remainderOfString = pString.substring(1, pString.length());
			if (!((remainderOfString.substring(0, 1)).equals(".")))
				remainderOfString = remainderOfString.toLowerCase();
			formattedString = firstLetter + remainderOfString;
		} else {
			formattedString = firstLetter;
		}
		return formattedString;

	}

	/**
	 * 
	 * Returns the string converted to title case or null if the value passed
	 * 
	 * is null.
	 * 
	 * @param pValue
	 *            - the value.
	 * 
	 * @return the string converted to title case.
	 */

	public static String toTitleCase(String pValue) {

		if (isSet(pValue)) {
			StringBuffer titleBuffer = new StringBuffer(pValue.toLowerCase());
			int idx = -1;
			String temp;
			do {
				temp = String.valueOf(titleBuffer.charAt(idx + 1)).toUpperCase();
				titleBuffer.setCharAt(idx + 1, temp.charAt(0));
				idx = titleBuffer.indexOf(SPACE, idx + 1);
			} while (idx != -1);
			return titleBuffer.toString();
		}
		return null;
	}

	public static <T> List<T> getListWithItem(T value, List<T> list) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		list.add(value);
		return list;
	}

}
