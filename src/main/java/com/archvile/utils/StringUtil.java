package com.archvile.utils;

import org.apache.commons.lang.StringEscapeUtils;

public class StringUtil {

	/**
	 * Return true of the String is empty
	 * @param value
	 * @return boolean
	 */
	public static boolean isEmpty(String value) {
		if (value == null) {
			return true;
		} else {
			return removeWhitespaces(value) == null;
		}
	}

	private static String removeWhitespaces(String value) {
		value = value.trim().replace("\\s", "");
		if (value.isEmpty()) {
			return null;
		} else if (StringEscapeUtils.escapeHtml(value).equals("&nbsp;")) {
			return null;
		}
		return value;
	}
	
}
