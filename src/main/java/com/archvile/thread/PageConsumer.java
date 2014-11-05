package com.archvile.thread;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.archvile.page.Page;
import com.archvile.service.IndexService;

public class PageConsumer implements Runnable {

	private static final Logger log = Logger.getLogger(PageConsumer.class);
	
	private IndexService indexService = new IndexService();
	private ArrayBlockingQueue<Page> queue;
	private boolean isRunning;
	
	public PageConsumer(ArrayBlockingQueue<Page> queue) {
		this.queue = queue;
	}

	private void stop() {
		log.info("Stopping consumer...");
		isRunning = false;
	}
	
	@Override
	public void run() {
		log.info("Starting consumer...");
		isRunning = true;
		while (isRunning) {
			/* Poll for work */
			Page page = queue.poll();
			if (page != null) {
				log.info("Adding '" + page.getUrl() + "' to the index...");
				indexService.addPageToIndex(page.getUrl(), page);
				indexService.printIndex();
			}
			/* Wait until notification */
	        try {
	    		synchronized (queue) {
					queue.wait();
	            }
	        } catch (InterruptedException e) {
	        	stop();
	        }
		}
	}
}