package com.archvile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	public static Archvile archvile;
	
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
	private List<String> urlsVisited = new ArrayList<>();
	
	
	private Archvile() { }
	
	public static Archvile getInstance() {
		if (archvile == null) {
			archvile = new Archvile();
		}
		return archvile;
	}

	private Archvile(String seedUrl) {
		this.seedUrl = seedUrl;
		urls.add(seedUrl);
	}

	public static void main (String[] args) throws IOException {
		Archvile archvile = new Archvile("htt://localhost");
		archvile.start();
	}

    /**
     * Start archvile
     */
	@Override
	public void start() {
		if (!isRunning) {
			isRunning = true;

            /* Set up the thread pool and blocking queue */
			executor = Executors.newFixedThreadPool(40);
			queue = new ArrayBlockingQueue<>(200);

            /* Setup the thread-safe list */
			urls = new CopyOnWriteArrayList<>();
			urls.add(seedUrl);

            /* Start up the consumer */
			consumer = new PageConsumer(queue, searchTerms == null ? null : Arrays.asList(searchTerms));
			consumer.start();

            /* Start archvile */
			thread = new Thread(this);
			thread.start();
		}
	}

    /**
     * Stop archvile
     */
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

    /**
     * Return whether archvile is running
     * @return
     */
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Get last run date
     * @return
     */
    @Override
    public String getLastRunDate() {
        return start == 0 ? null : TimeUtil.getDateFromLong(start);
    }

    /**
     * Get duration of las run
     * @return
     */
    @Override
    public String getLastRunDuration() {
        return stop == 0 ? null : TimeUtil.getDurationFromLongs(start, stop);
    }

    /**
     * If running, return the current running duration
     * @return
     */
    @Override
    public String getCurrentRunDuration() {
        return !isRunning ? null : TimeUtil.getDurationFromLongs(start, System.currentTimeMillis());
    }

    /**
     * Generate Page objects with PageProducers, and consume
     * them with the PageConsumer, which adds them to an index
     */
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
                    if (anchors != null) {
                        for (AnchorNode each : anchors) {
                            urls.add(each.getAbsoluteUrl());
                        }
                        urlsVisited.add(urls.get(i));
                    }

                    urls.remove(i);
					Thread.sleep(100);
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

	public List<String> getUrlsVisited() {
        Collections.sort(urlsVisited);
        return urlsVisited;
	}

	public void setSearchTerms(String searchTerms) {
		if (searchTerms != null) {
			this.searchTerms = searchTerms.split(" ");
		}
	}

}
