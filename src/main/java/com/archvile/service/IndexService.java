package com.archvile.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.archvile.index.IndexEntry;
import com.archvile.node.nodes.DivNode;
import com.archvile.page.Page;
import com.archvile.utils.StringUtil;

public class IndexService {
	
	private static Logger log = Logger.getLogger(IndexService.class);
	
	private static ConcurrentMap<String, IndexEntry> index = new ConcurrentHashMap<>();
	
	/**
	 * Add the text content of a Page object to the index
	 * @param url
	 * @param page
	 */
	public void addPageToIndex(String url, Page page) {
		if (url == null || page == null) throw new IllegalArgumentException("Page and/or URL cannot be null");
		if (page.getDivs() != null) {
			for (DivNode div : page.getDivs()) {
				List<String> words = Arrays.asList(div.getText().split("\\s+"));
				for (String word : words) {
					addToIndex(word.toLowerCase(), url);
				}
			}
		}
	}

	/**
	 * Add a new keyword with its associated URL
	 * or update an existing keyword with a new URL 
	 * @param word
	 * @param url
	 */
	private void addToIndex(String keyword, String url) {
		keyword = StringUtil.sanitize(keyword);
		if (!StringUtil.isEmpty(keyword)) {
			if (index.get(keyword) == null) {
				index.put(keyword, new IndexEntry(url));
			} else {
				IndexEntry entry = index.get(keyword);
				entry.setCount(entry.getCount() + 1);
				if (!entry.getUrls().contains(url)) {
					entry.addUrl(url);
				}
			}
		}
	}	

	public List<String> getUrls(String keyword) {
		return index.get(keyword).getUrls();
	}
	
	public int getIndexSize() {
		return index != null ? index.size() : 0;
	}
	
	public void printIndex() {
		log.info("Index size: " + index.size());
		for (Map.Entry<String, IndexEntry> entry : index.entrySet()) {
			log.info(entry.getKey()+"\t"+entry.getValue().getCount()+"\t"+entry.getValue().getUrls().size());
		}
	}

}
