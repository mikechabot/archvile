package com.archvile.index;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.archvile.node.nodes.DivNode;
import com.archvile.page.Page;
import com.archvile.utils.StringUtil;

public class IndexService {
	
	private static Logger log = Logger.getLogger(IndexService.class);
	
	private ConcurrentMap<String, List<String>> index = new ConcurrentHashMap<>();
	
	/**
	 * Add the text content of a Page object to the index
	 * @param url
	 * @param page
	 */
	public void addPageToIndex(String url, Page page) {
		for (DivNode div : page.getDivs()) {
			List<String> words = Arrays.asList(div.getText().split("\\s+"));
			for (String word : words) {
				addToIndex(word.toLowerCase(), url);	
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
				index.put(keyword, Arrays.asList(new String[] { url }));
			} else {
				updateIndex(keyword, url);
			}
		}
	}

	private void updateIndex(String keyword, String url) {
		List<String> urls = index.get(keyword);
		if (!urls.contains(url)) {
			urls.add(url);
			index.put(keyword, urls);
		} else {
			
		}
	}

	public void printIndex() {
		log.info("**PRINTING INDEX***");
		for (Map.Entry<String, List<String>> entry : index.entrySet()) {
			log.info("| Keyword: " +  entry.getKey());
			log.info("|     Url: " +  entry.getValue().size());
			for (String each : entry.getValue()) {
				log.info("|      --> url: " +  each);
			}
		}
	}

}
