package com.archvile.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Before;
import org.junit.Test;

import com.archvile.node.NodeFactoryImpl;
import com.archvile.node.Nodes.NodeName;
import com.archvile.node.nodes.DivNode;
import com.archvile.node.nodes.MetaNode;

public class NodeFactoryImplTest {
	
	private NodeFactoryImpl nodeFactory;
	
	@Before
	public void beforeTest() {
		nodeFactory = new NodeFactoryImpl();
	}

	/* Just test (null) here, can't pass a null Element 
	 * object since the constructor requires a 
	 * valid, non-null Tag object
	 */
	@Test
	public void testCreateElementWithNull() {
		assertNull(nodeFactory.createElement(null));
	}
	
	@Test 
	public void testCreateElementWithKnownNodename() {
		/* Generate div element */
		Tag tag = Tag.valueOf(String.valueOf(NodeName.DIV));
		Element element = nodeFactory.createElement(new Element(tag, "/baseuri"));
		assertNotNull(element);
		assertEquals(true, element instanceof DivNode);
		
		/* Generate meta element */
		tag = Tag.valueOf(String.valueOf(NodeName.META));
		element = nodeFactory.createElement(new Element(tag, "/baseuri"));
		assertNotNull(element);
		assertEquals(true, element instanceof MetaNode);
	}
	
	@Test 
	public void testCreateElementWithUnknownNodename() {
		Tag tag = Tag.valueOf(String.valueOf("<h5>"));
		assertNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
		
		tag = Tag.valueOf(String.valueOf("<foo>"));
		assertNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
		
		tag = Tag.valueOf(String.valueOf("bar"));
		assertNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
		
		tag = Tag.valueOf(String.valueOf("<bar>"));
		assertNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
	}
	
}
