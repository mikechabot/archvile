package com.archvile.node;

import static org.junit.Assert.*;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Test;
import com.archvile.node.Nodes.NodeName;
import com.archvile.node.nodes.DivNode;
import com.archvile.node.nodes.H2Node;

public class AbstractTextNodeTest {
	
	@Test
	public void testEqualsWithIdenticalNodes() {
		/* Tag needed to create Element object */
		Tag tag = Tag.valueOf(String.valueOf(NodeName.DIV));
		
		/* Build elements */
		Element ele1 = new Element(tag, "/baseuri");
		Element ele2 = new Element(tag, "/baseuri");
		ele1.text("this is the same text");
		ele2.text("this is the same text");
		
		/* Build divs */
		DivNode div1 = new DivNode(ele1);
		DivNode div2 = new DivNode(ele2);
		
		assertTrue(div1.getText().equals(div2.getText()));
		assertTrue(div1.equals(div2));
	}
	
	@Test
	public void testEqualsWithDifferentNodesButSameText() {
		/* Tag needed to create Element object */
		Tag tag1 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Tag tag2 = Tag.valueOf(String.valueOf(NodeName.H2));
		
		/* Build elements */
		Element ele1 = new Element(tag1, "/baseuri");
		Element ele2 = new Element(tag2, "/baseuri");
		ele1.text("this is the same text");
		ele2.text("this is the same text");
		
		/* Build divs */
		DivNode div1 = new DivNode(ele1);
		H2Node h2 = new H2Node(ele2);
		
		assertTrue(div1.getText().equals(h2.getText()));
		assertFalse(div1.equals(h2));
	}
	
	@Test
	public void testEqualsWithDifferentNodesAndDifferentText() {
		/* Tag needed to create Element object */
		Tag tag1 = Tag.valueOf(String.valueOf(NodeName.DIV));
		Tag tag2 = Tag.valueOf(String.valueOf(NodeName.H2));
		
		/* Build elements */
		Element ele1 = new Element(tag1, "/baseuri");
		Element ele2 = new Element(tag2, "/baseuri");
		ele1.text("this is the same text");
		ele2.text("this is different text");
		
		/* Build divs */
		DivNode div1 = new DivNode(ele1);
		H2Node h2 = new H2Node(ele2);
		
		assertFalse(div1.getText().equals(h2));
		assertFalse(div1.equals(h2));
	}
	
}
