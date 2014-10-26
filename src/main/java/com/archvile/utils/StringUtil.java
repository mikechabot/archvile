package com.archvile.utils;

public class StringUtil {

	/**
	 * Return true of the String is empty
	 * @param value
	 * @return boolean
	 */
	public static boolean isEmpty(String value) {
	    return value == null || value.trim().length() == 0;
	}
	
}
