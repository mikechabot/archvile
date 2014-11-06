package com.archvile.index;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndexEntryTest {

	@Test
	public void testIndexEntryConstructor() {
		IndexEntry entry = new IndexEntry("test", "test");
		assertEquals(1, entry.getCount());
	}
	
}
