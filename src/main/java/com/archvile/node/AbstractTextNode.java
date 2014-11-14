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
	
	@Override
	public boolean isValid() {
		return !StringUtil.isEmpty(text);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		AbstractTextNode other = (AbstractTextNode) obj;
		if (getText() == null) {
			if (other.getText() != null)
				return false;
		} else if (!getText().equals(other.getText()))
			return false;
		return true;
	}
	
}
