package com.archvile;

import java.io.IOException;
import java.util.Arrays;
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
import com.archvile.utils.TimeUtil;

/**
 * Let's crawl the interwebs
 * @author mchabot
 *
 */
public class Archvile implements Runnable, QueueTask {

	private static Logger log = Logger.getLogger(Archvile.class);
	
	private Thread thread;
	
	private CopyOnWriteArrayList<String> urls = new CopyOnWriteArrayList<>();
	private PageConsumer consumer;
	
	private ExecutorService executor;
	private ArrayBlockingQueue<Page> queue;
	
	private boolean isRunning;
	private String seedUrl;
	private String[] searchTerms;
	
	private long start;
	private long stop;
	private int pagesViewed;
	
	
	public Archvile() { }
	
	private Archvile(String seedUrl) {
		this.seedUrl = seedUrl;
		urls.add(seedUrl);
	}

	public static void main (String[] args) throws IOException {
		Archvile archvile = new Archvile("http://www.woot.com");
		archvile.start();
	}
	
	@Override
	public void start() {
		if (!isRunning) {
			isRunning = true;
			
			executor = Executors.newFixedThreadPool(10);
			queue = new ArrayBlockingQueue<Page>(50);
			
			urls = new CopyOnWriteArrayList<>();
			urls.add(seedUrl);
			
			consumer = new PageConsumer(queue, searchTerms == null ? null : Arrays.asList(searchTerms));
			consumer.start();
			
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void stop() {
		if (isRunning) {
			isRunning = false;
			consumer.stop();
			log.info("Shutting down executor...");
			executor.shutdown();			
			thread.interrupt();
		}		
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public String getLastRunDate() {
		return start == 0 ? null : TimeUtil.getDateFromLong(start);
	}

	@Override
	public String getLastRunDuration() {
		return stop == 0 ? null : TimeUtil.getDurationFromLongs(start, stop);
	}

	@Override
	public String getCurrentRunDuration() {
		return !isRunning ? null : TimeUtil.getDurationFromLongs(start, System.currentTimeMillis());		
	}

	@Override
	public void run() {
		start = System.currentTimeMillis();
		try {
			while (isRunning) {
				for (int i=0; i < urls.size(); i++) {
					PageProducer producer = new PageProducer(queue, urls.get(i));
					Future<List<AnchorNode>> future = executor.submit(producer);
					/* Get anchor nodes from future */
					List<AnchorNode> anchors = future.get();
					for (AnchorNode each : anchors) {
						urls.add(each.getAbsoluteUrl());
					}
					pagesViewed++;
					Thread.sleep(1000);
				}
			}
		} catch (ExecutionException e) {
			log.warn("Error getting future: ", e);
		} catch (NullPointerException e) {
			log.warn("Error getting future:", e);
		} catch (InterruptedException e) {
			log.warn("archvile interrupted...");
		} finally {
			stop();
			stop = System.currentTimeMillis();
		}
	}
	
	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
		urls.add(seedUrl);
	}
	
	public String getSeedUrl() {
		return seedUrl == null ? null : seedUrl.replace("http://", "");
	}
	
	public int getUrlsSize() {
		return urls.size();
	}
	
	public int getPagesViewed() {
		return pagesViewed;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms.split(" ");
	}
	
	public String[] getSearchTerms() {
		return searchTerms;
	}

}
