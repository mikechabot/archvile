package com.archvile.node;

import org.jsoup.nodes.Element;

import com.archvile.utils.StringUtil;

public abstract class AbstractTextNode extends AbstractNode {

	private String text;
	
	public AbstractTextNode(Element element) {
		super(element);
		setText(element.text());
	}

	private void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public boolean hasText() {
		return !StringUtil.isEmpty(text);
	}

}
