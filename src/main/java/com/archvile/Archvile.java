package com.archvile;

import java.io.IOException;

import org.apache.log4j.Logger;

import org.jsoup.nodes.Document;

import com.archvile.index.IndexService;
import com.archvile.page.Page;
import com.archvile.page.PageGenerator;
import com.archvile.service.DocumentService;

/**
 * Let's crawl the interwebs
 * @author mchabot
 *
 */
public class Archvile {

	private static Logger log = Logger.getLogger(Archvile.class);

	private PageGenerator pageGenerator = new PageGenerator();
	private DocumentService documentService = new DocumentService();
	private IndexService indexService = new IndexService();
	
	private Archvile() { }
	
	public void crawl(String url) throws IOException {
		log.info("Parsing " + url + "...");
		Document document = documentService.getDocumentFromUrl(url);
		Page page = pageGenerator.generatePageFromDocument(document);
		indexService.addPageToIndex(url, page);
		indexService.printIndex();
		log.info("Done");
	}	

	public static void main (String[] args) throws IOException {
		String startingUrl = "http://www.google.com";
		Archvile archvile = new Archvile();
		archvile.crawl(startingUrl);
	}
	
}
