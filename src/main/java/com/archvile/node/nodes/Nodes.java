package com.archvile.node.nodes;

import com.archvile.utils.StringUtil;

/**
 * Nodes to be created from Element objects
 */
public class Nodes {

	public enum NodeName {
		A,
		DIV,
		META,
		TITLE,
		H1
	}
	
	/**
	 * Get NodeName from String
	 * @param value
	 * @return NodeName
	 */
	public static NodeName getNode(String nodeName) throws NodeNotImplementedException {
		if (StringUtil.isEmpty(nodeName)) return null;
		try {
			return NodeName.valueOf(nodeName.toUpperCase());	
		} catch (IllegalArgumentException ex) {
			throw new NodeNotImplementedException(nodeName);
		}
	}
	
	public static class NodeNotImplementedException extends Exception {
		private static final long serialVersionUID = -4483944685096937455L;
		public NodeNotImplementedException(String nodeName) {
	        super("Node not implemeneted: " + nodeName);
	    }
	}

}