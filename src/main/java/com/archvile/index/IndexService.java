package com.archvile.index;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.archvile.page.Page;

public interface IndexService {
	
	/**
	 * Return the index
	 * @return ConcurrentMap<?,?>
	 */
	ConcurrentMap<?,?> getIndex();
	
	/**
	 * Return the size of the index
	 * @return int
	 */
	int getIndexSize();
	
	/**
	 * Add a page to the index; no inclusions
	 * @param page
	 */
	void addPageToIndex(Page page);
	
	/**
	 * Add page to the index using inclusions
	 * @param page
	 * @param inclusions
	 */
	void addPageToIndex(Page page, List<String> inclusions);
	
	/**
	 * Add individual entry to the index
	 * @param keyword
	 * @param url
	 */
	void addEntryToIndex(String keyword, String url);
	
	/**
	 * Get an entry from the index
	 * @param index
	 * @return IndexEntry
	 */
	IndexEntry get(int i);
	
	/**
	 * Get and entry from the index
	 * @param keyword
	 * @return IndexEntry
	 */
	IndexEntry get(String keyword);

}
