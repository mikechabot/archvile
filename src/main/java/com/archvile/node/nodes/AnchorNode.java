package com.archvile.node.nodes;

import org.jsoup.nodes.Element;

import com.archvile.node.AbstractNode;
import com.archvile.utils.ExcludedUrls;
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
	
	public String getAbsoluteUrl() {
		if (baseUri().endsWith("/") && url.startsWith("/")) url = url.replaceFirst("/", "");
		return (isRelativeUrl() ? baseUri() + url : url);
	}
	
	public boolean isSectionUrl() {
		return url.contains("#");
	}
	
	public boolean isRelativeUrl() {
		return !url.startsWith("http");
	}
	
	public boolean isSSL() {
		return url.startsWith("https");
	}
	
	private boolean isJavaScript() {
		return url.contains("javascript:");
	}
	
	@Override
	public boolean isValid() {
		return (!StringUtil.isEmpty(url) 
				&& !isSSL() 
				&& !isExcluded()
				&& !isJavaScript());
	}

	@Override
	public boolean isExcluded() {
		return ExcludedUrls.isExcluded(url);
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
		if (getClass() != obj.getClass())
			return false;
		AnchorNode other = (AnchorNode) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url.replaceAll("\\/$", ""))) {
			return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			System.out.println("1");
//			return true;
//		} else 	if (getClass() != obj.getClass()) {
//			System.out.println("2");
//			return false;
//		}	
//		AnchorNode other = (AnchorNode) obj;
//		if (url == null) {
//			if (other.url != null) {
//				System.out.println("2");
//				return false;
//			}	
//		} else if (!url.equals(other.url.replaceAll("\\/$", ""))) {
//			System.out.println("3");
//			return false;
//		} else if (!url.equals(other.url)) {
//			System.out.println("4");
//			return false;
//		}
//		System.out.println("5");
//		return true;
//	}
	
}
