package com.archvile.page;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.archvile.node.AbstractTextNode;
import com.archvile.node.NodeFactoryImpl;
import com.archvile.node.nodes.*;
import com.archvile.utils.StringUtil;

public class PageGenerator {

	private NodeFactoryImpl nodeFactory = new NodeFactoryImpl();	

	/**
	 * Generate a page from a document
	 * @param document
	 * @return
	 */
	public Page generatePageFromDocument(Document document) {
		List<Element> pageElements = getElements(document.children()); 
		return generatePage(pageElements);
	}
	
	/**
	 * Process a list of Element objects
	 * @param elements
	 */
	private List<Element> getElements(Elements elements) {
		List<Element> children = new ArrayList<>();
		for (Element each : elements) {
			/* Process child elements first */
			if (each.children() != null && each.children().size() > 0) {
				children.addAll(getElements(each.children()));
			}
			Element element = nodeFactory.createElement(each);
			if (element != null) {
				children.add(element);
			}
		}
		return children;
	}
	
	private Page generatePage(List<Element> elements) {
		Page page = new Page();
		for (Element element : elements) {
			if (element instanceof AbstractTextNode && StringUtil.isEmpty(((AbstractTextNode) element).getText())) {
				continue;
			} else if(element instanceof AnchorNode) {
				page.addAnchor((AnchorNode) element);
			} else if (element instanceof DivNode) {
				page.addDiv((DivNode) element);
			} else if (element instanceof MetaNode) {
				page.addMeta((MetaNode) element);
			} else if (element instanceof TitleNode) {
				page.setTitle((TitleNode) element);
			} else if (element instanceof H1Node) {
				page.addH1((H1Node) element);
			} else if (element instanceof H2Node) {
				page.addH2((H2Node) element);
			} else if (element instanceof BodyNode) {
				page.setBody((BodyNode) element);
			}
		}
		return page;
	}
	
}
