package com.archvile.page;

import static org.junit.Assert.assertEquals;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Before;
import org.junit.Test;

import com.archvile.node.Nodes.NodeName;
import com.archvile.node.nodes.DivNode;

public class PageTest {

	private Page page;
	
	@Before
	public void beforeTest() {
		page = new Page();
	}
	
	@Test
	public void testAddNewDivToEmptyDivs() {
		/* Build DivNode */
		Tag tag = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element element = new Element(tag, "/baseuri");
		element.text("This is some text");
		DivNode div = new DivNode(element);
		
		assertEquals(null, page.getDivs());
		page.addDiv(div);
		assertEquals(1, page.getDivs().size());
	}
	
	@Test
	public void testAddNewDivToDivs() {
		/* Build DivNode 1 */
		Tag tag1 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element ele1 = new Element(tag1, "/baseuri");
		ele1.text("this is the same text");
		DivNode div1 = new DivNode(ele1);
		
		/* Build DivNode 2 */
		Tag tag2 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element ele2 = new Element(tag2, "/baseuri");
		ele2.text("This is different text");
		DivNode div2 = new DivNode(ele2);
		
		assertEquals(null, page.getDivs());
		page.addDiv(div1);
		assertEquals(1, page.getDivs().size());
		page.addDiv(div2);
		assertEquals(2, page.getDivs().size());
	}
	
	
	@Test
	public void testAddExistingDivToDivs() {
		/* Build DivNode 1 */
		Tag tag1 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element ele1 = new Element(tag1, "/baseuri");
		ele1.text("this is the same text");
		DivNode div1 = new DivNode(ele1);
		
		/* Build DivNode 2 */
		Tag tag2 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element ele2 = new Element(tag2, "/baseuri");
		ele2.text("this is the same text");
		DivNode div2 = new DivNode(ele2);
		
		assertEquals(null, page.getDivs());
		page.addDiv(div1);
		assertEquals(1, page.getDivs().size());
		page.addDiv(div2);
		assertEquals(1, page.getDivs().size());
	}
	
}
