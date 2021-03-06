package com.archvile.node.nodes;

import org.jsoup.nodes.Element;

import com.archvile.node.AbstractNode;

public class MetaNode extends AbstractNode {

	String name;
	String content;
	
	public MetaNode(Element element) {
		super(element);
		setName(element.attr("name"));
		setContent(element.attr("content"));
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	private void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
