package com.archvile.page;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.archvile.node.AbstractTextNode;
import com.archvile.node.NodeFactoryImpl;
import com.archvile.node.nodes.*;

public class PageGenerator {

	private NodeFactoryImpl nodeFactory = new NodeFactoryImpl();	

	/**
	 * Generate a page from a document
	 * @param document
	 * @return
	 */
	public Page generatePageFromDocument(Document document) {
		if (document == null) throw new IllegalArgumentException("Document cannot be null");
		List<Element> pageElements = getElements(document.children());
		return generatePage(pageElements, document.baseUri());
	}
	
	/**
	 * Process a list of Element objects
	 * @param elements
	 */
	private List<Element> getElements(Elements elements) {
		List<Element> children = new ArrayList<>();
		for (Element each : elements) {
			/* Process any children first */
			if (each.children() != null && !each.children().isEmpty()) {
				children.addAll(getElements(each.children()));
			}
			children.add(nodeFactory.createElement(each));
		}
		return children;
	}
	
	/**
	 * Generate a Page from a list of Element objects
	 * @param elements
	 * @return
	 */
	private Page generatePage(List<Element> elements, String url) {
		Page page = new Page();
		page.setUrl(url);
		for (Element element : elements) {
			if (element instanceof AbstractTextNode && !((AbstractTextNode) element).hasText()) {
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
