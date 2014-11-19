package com.archvile.thread;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.archvile.node.nodes.AnchorNode;
import com.archvile.page.Page;
import com.archvile.service.DocumentService;
import com.archvile.service.PageService;

public class PageProducer implements Callable<List<AnchorNode>> {

	private static Logger log = Logger.getLogger(PageProducer.class);

	private DocumentService documentService = new DocumentService();
	private PageService pageService = new PageService();
	
	private ArrayBlockingQueue<Page> queue;
	private String url;
	
	public PageProducer(ArrayBlockingQueue<Page> queue, String url) {
		this.queue = queue;
		this.url = url;
	}

	/**
	 * Retrieve a document given a URL, put it in the queue,
	 * and return a list of anchor nodes
	 */
	@Override
	public List<AnchorNode> call() throws Exception {
		log.info("Starting a producer with url '" + url + "'");
		List<AnchorNode> urls = new ArrayList<>(0);
		try {
			Document document = documentService.getDocumentFromUrl(url);
			Page page = pageService.generatePageFromDocument(document);
			if (page != null) {
				urls = page.getAnchors();
				queue.put(page);
				synchronized (queue) {
					queue.notify();
				}	
			}
		} catch (SocketTimeoutException e) {
            log.error("Socket timeout ", e);
        } catch (IOException e) {
			log.error("Error retrieving document from '" + url + "': ", e);
		} catch (InterruptedException e) {
			log.error("PageProducer interrupted", e);
		}
		log.info("Returning " + urls.size() + " urls from '" + url + "'");
		return urls;
	}
}