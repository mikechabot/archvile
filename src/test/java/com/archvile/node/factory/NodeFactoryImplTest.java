package com.archvile.node.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.Before;
import org.junit.Test;

import com.archvile.node.NodeFactoryImpl;
import com.archvile.node.nodes.Nodes.NodeName;

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
		NodeName nodeName = NodeName.DIV;
		Tag tag = Tag.valueOf(String.valueOf(nodeName));
		assertNotNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
		
		nodeName = NodeName.META;
		tag = Tag.valueOf(String.valueOf(nodeName));
		assertNotNull(nodeFactory.createElement(new Element(tag, "/baseuri")));
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
