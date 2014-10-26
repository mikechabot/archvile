package com.archvile.node;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import com.archvile.node.nodes.AnchorNode;
import com.archvile.node.nodes.DivNode;
import com.archvile.node.nodes.H1Node;
import com.archvile.node.nodes.MetaNode;
import com.archvile.node.nodes.Nodes;
import com.archvile.node.nodes.Nodes.NodeNotImplementedException;
import com.archvile.node.nodes.TitleNode;

/**
 * Create subclass from Element object
 * 
 */
public class NodeFactoryImpl implements NodeFactory {

	private static Logger log = Logger.getLogger(NodeFactoryImpl.class);
	
	/** 
	 * Return null if the subclass corresponding to the element
	 * type hasn't been implemented (e.g. <b>, <br>, etc)
	 */
	@Override
	public Element createElement(Element element) {
		if (element == null) return null;
		try {
			switch (Nodes.getNode(element.tagName())) {
			case A: 
				return new AnchorNode(element);
			case DIV: 
				return new DivNode(element);
			case META:
				return new MetaNode(element);
			case TITLE:
				return new TitleNode(element);
			case H1:
				return new H1Node(element);
			default:
				return null;
			}
		} catch (NodeNotImplementedException ex) {
			log.debug(ex.getMessage());
			return null;
		} 
	}

}
