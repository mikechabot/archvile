package com.archvile.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testIsEmptyWithString() {
		String val1 = "This is some text";
		assertFalse(StringUtil.isEmpty(val1));
	}
	
	@Test
	public void testIsEmptyWithNullAndEmptyValues() {
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty(""));
		assertTrue(StringUtil.isEmpty(" "));
		assertTrue(StringUtil.isEmpty("  "));
	}
	
	@Test
	public void testIsEmptyWithRegex() {
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty(""));
		assertTrue(StringUtil.isEmpty(" "));
		assertTrue(StringUtil.isEmpty("  "));
	}
	
	@Test
	public void testIsEmptyWithUnicode() {
		/* test non-breaking space (&nbsp) */
		assertTrue(StringUtil.isEmpty("\u00A0"));
	}
	
}
