package com.archvile.thread;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.archvile.page.Page;
import com.archvile.service.IndexService;

public class PageConsumer implements Runnable {

	private static final Logger log = Logger.getLogger(PageConsumer.class);
	
	private Thread thread;
	private IndexService indexService = new IndexService();
	private ArrayBlockingQueue<Page> queue;
	private boolean isRunning;
	
	public PageConsumer(ArrayBlockingQueue<Page> queue) {
		this.queue = queue;
	}

	public void start() {
		if (!isRunning) {
			if (thread == null) {
				thread = new Thread(this);
			}
			log.info("Starting consumer...");
			thread.start();
		}		
	}
	
	public void stop() {
		if (isRunning) {
			log.info("Stopping consumer...");
			isRunning = false;
			thread.interrupt();
		}
	}
	
	@Override
	public void run() {
		isRunning = true;
		while (isRunning && thread != null) {
			/* Poll for work */
			Page page = queue.poll();
			if (page != null) {
				log.info("Adding '" + page.getUrl() + "' to the index...");
				indexService.addPageToIndex(page.getUrl(), page);
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