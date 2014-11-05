package com.archvile.node.nodes;

import org.jsoup.nodes.Element;

import com.archvile.node.AbstractNode;
import com.archvile.utils.StringUtil;

public class AnchorNode extends AbstractNode implements Validator {

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
	
	public String getAbsUrl() {
		return (isRelativeUrl() ? baseUri() + url : url);
	}

	public boolean isSectionUrl() {
		return url.startsWith("#");
	}
	
	public boolean isRelativeUrl() {
		return !url.startsWith("http");
	}
	
	@Override
	public boolean isValid() {
		return !StringUtil.isEmpty(url);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnchorNode other = (AnchorNode) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
