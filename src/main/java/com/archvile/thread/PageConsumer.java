package com.archvile.thread;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.archvile.index.Index;
import org.apache.log4j.Logger;

import com.archvile.page.Page;

public class PageConsumer implements Runnable {

	private static final Logger log = Logger.getLogger(PageConsumer.class);
	
	private Thread thread;
	private Index indexService = new Index();
	private ArrayBlockingQueue<Page> queue;
	
	private List<String> searchTerms;
	private boolean isRunning;
	
	public PageConsumer(ArrayBlockingQueue<Page> queue, List<String> searchTerms) {
		this.queue = queue;
		this.searchTerms = searchTerms;
	}

    /**
     * Start the consumer
     */
	public void start() {
		if (!isRunning) {
			if (thread == null) {
				thread = new Thread(this);
			}
			log.info("Starting consumer...");
			thread.start();
		}		
	}

    /**
     * Stop the consumer
     */
	public void stop() {
		if (isRunning) {
			log.info("Stopping consumer...");
			isRunning = false;
			thread.interrupt();
		}
	}

    /**
     * Add Page objects to the index
     */
	@Override
	public void run() {
		isRunning = true;
		while (isRunning && thread != null) {
			/* Poll for work */
			Page page = queue.poll();
			if (page != null) {
				log.info("Adding '" + page.getUrl() + "' to the index...");
				indexService.addPageToIndex(page, searchTerms);
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