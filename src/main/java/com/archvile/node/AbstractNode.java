package com.archvile.node;

import org.jsoup.nodes.Element;

import com.archvile.node.nodes.Validator;
import com.archvile.utils.ExcludedUrls;

public abstract class AbstractNode extends Element implements Validator {
	
	public AbstractNode(Element element) {
		super(element.tag(), element.baseUri(), element.attributes());		
	}
	
	@Override
	public boolean isDomainExcluded() {
		return ExcludedUrls.isExcluded(baseUri());
	}
	
}
