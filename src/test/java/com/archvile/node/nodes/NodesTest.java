package com.archvile.node.nodes;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.archvile.node.Nodes;
import com.archvile.node.Nodes.NodeName;
import com.archvile.node.Nodes.NodeNotImplementedException;

public class NodesTest {

	@Test
	public void testGetNodeWithNull() throws NodeNotImplementedException {
		assertNull(Nodes.getNode(null));
		assertNull(Nodes.getNode(new String()));
		assertNull(Nodes.getNode(""));
	}
	
	@Test
	public void testGetNodeWithNodeNames() throws NodeNotImplementedException {
		assertNotNull(Nodes.getNode(String.valueOf(NodeName.A)));
		assertNotNull(Nodes.getNode(String.valueOf(NodeName.DIV)));
		assertNotNull(Nodes.getNode(String.valueOf(NodeName.META)));
	}
	
	@Test
	public void testGetNodeWithStrings() throws NodeNotImplementedException {
		/* uppercase */
		assertNotNull(Nodes.getNode("A"));
		assertNotNull(Nodes.getNode("DIV"));
		assertNotNull(Nodes.getNode("META"));
		/* lowercase */
		assertNotNull(Nodes.getNode("a"));
		assertNotNull(Nodes.getNode("div"));
		assertNotNull(Nodes.getNode("meta"));
	}
	
	@Test(expected=NodeNotImplementedException.class)
	public void testGetNodeWithBadNodeName() throws NodeNotImplementedException {
		Nodes.getNode("foo");
		Nodes.getNode("bar");
		Nodes.getNode("$$");
		Nodes.getNode(" div ");
		Nodes.getNode(" meta ");
	}
	
}
