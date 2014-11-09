package com.archvile.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeUtilTest {

	@Test
	public void testGetDurationFromLongs() throws InterruptedException {
		
		long start = System.currentTimeMillis();
		long secs = start + 2000;
		long mins = start + 120000;
		long hours = start + 7200000;
		long duration = (secs + mins + hours) - (start * 2);
	
		assertEquals("2s", TimeUtil.getDurationFromLongs(start, secs));
		assertEquals("2m", TimeUtil.getDurationFromLongs(start, mins));
		assertEquals("2h", TimeUtil.getDurationFromLongs(start, hours));
		assertEquals("2h 2m 2s", TimeUtil.getDurationFromLongs(start, duration));
		
	}
	
}
