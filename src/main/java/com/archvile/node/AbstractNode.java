package com.archvile.node;

import org.jsoup.nodes.Element;

public abstract class AbstractNode extends Element {
	
	public AbstractNode(Element element) {
		super(element.tag(), element.baseUri(), element.attributes());		
	}
	
}
