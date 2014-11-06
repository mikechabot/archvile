package com.archvile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.archvile.node.nodes.AnchorNode;
import com.archvile.page.Page;
import com.archvile.thread.PageConsumer;
import com.archvile.thread.PageProducer;

/**
 * Let's crawl the interwebs
 * @author mchabot
 *
 */
public class Archvile {

	private static Logger log = Logger.getLogger(Archvile.class);
	
	private ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<Page>(10);
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	public Archvile() { }
	
	public void crawl(String seed) throws IOException {
		log.info("Seed url: " + seed);
		PageConsumer consumer = new PageConsumer(queue);
		consumer.start();
		
		CopyOnWriteArrayList<String> urls = new CopyOnWriteArrayList<>();
		urls.add(seed);
		
		for (int i=0; i < urls.size(); i++) {
			try {
				PageProducer producer = new PageProducer(queue, urls.get(i));
				Future<List<AnchorNode>> future = executor.submit(producer);
				List<AnchorNode> anchors = future.get();
				for (AnchorNode each : anchors) {
					if (each.isValid()) {
						urls.add(each.getAbsUrl());
					}
				}
				log.info("# of urls: " + urls.size());
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				log.error("InterruptedException", e);
			} catch (ExecutionException e) {
				log.error("Error getting future: ", e);
			}
		}
		log.info("Done");
	}	

	public static void main (String[] args) throws IOException {
		String startingUrl = "http://www.woot.com";
		Archvile archvile = new Archvile();
		archvile.crawl(startingUrl);
	}

}
