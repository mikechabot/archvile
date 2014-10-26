package com.archvile.node.nodes;

import org.jsoup.nodes.Element;

import com.archvile.node.AbstractTextNode;

public class TitleNode extends AbstractTextNode {
	
	public TitleNode(Element element) {
		super(element);
	}

	public String getTitle() {
		return getText();
	}

}
