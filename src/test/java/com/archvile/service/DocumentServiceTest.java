package com.archvile.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

public class DocumentServiceTest {
    
	private DocumentService service;
	
	@Before
	public void beforeTest() {
		service = new DocumentService();
	}
	
	@Test
    public void testGetDocumentWithGoodUrl() throws IOException {
		assertNotNull(service.getDocumentFromUrl("http://www.google.com"));
		assertNotNull(service.getDocumentFromUrl("http://www.yahoo.com"));
    }

	@Test(expected=UnknownHostException.class)
    public void testGetDocumentWithBadUrl() throws IOException {
		assertNull(service.getDocumentFromUrl("http://xxx.xxx.xxx.xxx"));
		assertNull(service.getDocumentFromUrl("http://xxx.xxx.xxx.xxx"));
    }
	
	@Test
    public void testGetDocumentWithGoodLocalUrl() throws IOException {
		Document document = service.getDocumentFromUrl("src/test/resources/test.html");
		assertNotNull(document);
		assertEquals("This is a title", document.title());
    }
	
}
