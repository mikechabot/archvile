package com.archvile.node;

import org.jsoup.nodes.Element;

public abstract class AbstractTextNode extends AbstractNode {

	private String text;
	
	public AbstractTextNode(Element element) {
		super(element);	
		setText(element.text());
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
