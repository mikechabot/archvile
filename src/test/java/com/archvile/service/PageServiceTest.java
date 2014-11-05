package com.archvile.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.archvile.page.Page;
import com.archvile.service.PageService;

public class PageServiceTest {

	private PageService pageGenerator;
	
	@Before
	public void beforeTest() {
		pageGenerator = new PageService();
	}
	
	@Test
	public void testGeneratePageFromDocument() throws IOException {
		/* Get test file */
		File file = new File("src/test/resources/test.html");
		Document document = Jsoup.parse(file, "UTF-8");
		Page page = pageGenerator.generatePageFromDocument(document);

		assertNotNull(page);
		assertNotNull(page.getDivs());
		assertNotNull(page.getH1s());
		assertEquals("This is a title", page.getTitle());
		assertEquals("Hello World!", page.getH1s().get(0).getText());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGeneratePageFromNull() throws IOException {
		pageGenerator.generatePageFromDocument(null);
	}
	
}
