package com.archvile.node.nodes;

import org.jsoup.nodes.Element;

import com.archvile.node.AbstractNode;

public class AnchorNode extends AbstractNode {

	private String url;
	
	public AnchorNode(Element element) {
		super(element);
		setUrl(element.attr("href"));
	}

	private void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public boolean isSectionUrl() {
		return getUrl().startsWith("#");
	}
	
	public boolean isRelativeUrl() {
		return !getUrl().startsWith("http");
	}
	
}
