package com.archvile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.archvile.node.NodeFactoryImpl;
import com.archvile.node.nodes.AnchorNode;
import com.archvile.node.nodes.DivNode;
import com.archvile.node.nodes.H1Node;
import com.archvile.node.nodes.H2Node;
import com.archvile.node.nodes.MetaNode;
import com.archvile.node.nodes.TitleNode;
import com.archvile.utils.StringUtil;

/**
 * Let's crawl the interwebs
 * @author mchabot
 *
 */
public class Archvile {

	private static Logger log = Logger.getLogger(Archvile.class);
	
	NodeFactoryImpl factory = new NodeFactoryImpl();
	
	private Archvile() { }
	
	public void crawl(String url) throws IOException {
		log.info("Parsing " + url + "...");
		Document document = getDocumentFromUrl(url);
		log.info(document.toString());
		List<Element> elements = processChildren(document.children());
		Page page = getPage(elements);
		
		for (DivNode div : page.getDivs()) {
			if (div.hasText()) {
				if (div.getText().length() == 1){
					char character = div.getText().charAt(0);
					int ascii = (int) character;
					String chars = String.valueOf(character);
					log.info("Div-->" + ascii);
					log.info("Div escaped-->" + StringEscapeUtils.escapeHtml(chars));
					log.info("DIV: " + div.getText() + " (" + div.getText().length() + ") | (" + div.getText().trim().length() + ")");	
				}
			}
		}
		
		log.info("Done");
	}

	/**
	 * Retrieve a document object given a URL
	 * @param url
	 * @return Document
	 * @throws IOException
	 */
	private Document getDocumentFromUrl(String url) throws IOException  {
		if (StringUtil.isEmpty(url)) throw new IllegalArgumentException("URL cannot be null");
		return Jsoup.connect(url).get();
	}

	/**
	 * Process a list of Element objects
	 * @param elements
	 */
	private List<Element> processChildren(Elements elements) {
		List<Element> children = new ArrayList<>();
		for (Element each : elements) {
			/* Process child elements first */
			if (each.children() != null && each.children().size() > 0) {
				children.addAll(processChildren(each.children()));
			}
			Element element = factory.createElement(each);
			if (element != null) {
				children.add(element);
			}
		}
		return children;
	}
	
	private Page getPage(List<Element> elements) {
		Page page = new Page();
		for (Element element : elements) {
			if (element instanceof AnchorNode) {
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
			}
		}
		return page;
	}

	public static void main (String[] args) throws IOException {
		String startingUrl = "http://www.espn.com";
		Archvile archvile = new Archvile();
		archvile.crawl(startingUrl);
	}
}
