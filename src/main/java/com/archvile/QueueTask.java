package com.archvile;

public interface QueueTask {
	
	void start();
	void stop();
	boolean isRunning();
	String getLastRunDate();
	String getLastRunDuration();
	String getCurrentRunDuration();

}
