package com.archvile.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class ConsumerFactory implements ThreadFactory {
	
	private static Logger log = Logger.getLogger(ConsumerFactory.class);
	private static final AtomicInteger poolNumber = new AtomicInteger(1);

	private AtomicInteger threadNumber = new AtomicInteger(1);
	private ThreadGroup group;
	private String prefix;

	public ConsumerFactory() {
		log.info("Initializing consumer factory...");
		SecurityManager securityManager = System.getSecurityManager();
		group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
		prefix ="consumer-" + poolNumber.getAndIncrement() + "-thread-";
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(group, runnable, prefix + threadNumber.getAndIncrement(), 0);
		return thread;
	}
}