package com.archvile;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.junit.Test;

public class ArchvileTest {
    
	@Test
    public void testGetDocumentWithGoodUrl() throws IOException {
		assertNotNull(Jsoup.connect("http://www.google.com").get());
		assertNotNull(Jsoup.connect("http://www.yahoo.com").get());
    }
	
	@Test(expected=UnknownHostException.class)
    public void testGetDocumentWithBadUrl() throws IOException {
		assertNull(Jsoup.connect("http://xxx.xxx.xxx.xxx").get());
		assertNull(Jsoup.connect("http://xxx.xxx.xxx.xxx").get());
    }
	
}
