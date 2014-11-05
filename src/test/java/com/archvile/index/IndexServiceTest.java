package com.archvile.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Before;
import org.junit.Test;

import com.archvile.node.Nodes;
import com.archvile.node.nodes.DivNode;
import com.archvile.page.Page;
import com.archvile.service.IndexService;

public class IndexServiceTest {

	private IndexService service;
	
	@Before
	public void beforeTest() {
		service = new IndexService();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddNullUrlToIndex() {
		service.addPageToIndex(null, new Page());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNullPageToIndex() {
		service.addPageToIndex("http://localhost", null);
	}	
	
	@Test
	public void testAddEmptyPage() {
		service.addPageToIndex("http://localhost", new Page());
		assertEquals(0, service.getIndexSize());
	}
	
	@Test
	public void testAddPageToIndex() {
		/* Build page */
		Tag tag = Tag.valueOf(String.valueOf(Nodes.NodeName.DIV));
		
		Element ele1 = new Element(tag, "/baseuri");
		ele1.text("this is the same text");
		DivNode div1 = new DivNode(ele1);
		
		Element ele2 = new Element(tag, "/baseuri");
		ele2.text("this is the same text");
		DivNode div2 = new DivNode(ele2);
		
		Element ele3 = new Element(tag, "/baseuri");
		ele3.text("this is different text");
		DivNode div3 = new DivNode(ele3);
		
		Page page1 = new Page();
		page1.setUrl("http://localhost");
		page1.addDiv(div1);
		page1.addDiv(div2);
		
		Page page2 = new Page();
		page2.setUrl("http://www.google.com");
		page2.addDiv(div1);
		page2.addDiv(div2);
		page2.addDiv(div3);
		
		List<Page> pages = new ArrayList<>();
		pages.add(page1);
		pages.add(page2);
		
		for (Page page : pages) {
			service.addPageToIndex(page.getUrl(), page);
		}

		assertTrue(service.getIndexSize() > 0);
		assertEquals(2, service.getUrls("same").size());
		assertEquals(2, service.getUrls("text").size());
		assertEquals(1, service.getUrls("different").size());
	}
	
}
