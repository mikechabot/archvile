package com.archvile.node;

import org.jsoup.nodes.Element;

public abstract class AbstractNode extends Element {

	private String id;
	
	public AbstractNode(Element element) {
		super(element.tag(), element.baseUri(), element.attributes());
		setId(element.id());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
