package com.archvile.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.archvile.index.IndexEntry;
import com.archvile.index.IndexService;
import com.archvile.node.nodes.DivNode;
import com.archvile.page.Page;
import com.archvile.utils.StringUtil;

public class IndexServiceImpl implements IndexService {
	
	private static ConcurrentMap<String, IndexEntry> index = new ConcurrentHashMap<>();
	
	public IndexServiceImpl() {	}

	@Override
	public void addPageToIndex(Page page) {
		addPageToIndex(page, null);
	}

	@Override
	public void addPageToIndex(Page page, List<String> inclusions) {
		if (page == null || page.getUrl() == null) throw new IllegalArgumentException("Page and/or URL cannot be null");
		if (page.getDivs() != null) {
			for (DivNode div : page.getDivs()) {
				List<String> words = Arrays.asList(div.getText().split("\\s+"));
				for (String word : words) {
					if (inclusions == null) {
						addEntryToIndex(word, page.getUrl());
					} else if (containsIgnoreCase(inclusions, word)) {
						addEntryToIndex(word, page.getUrl());
					} 
				}
			}
		}
	}

	@Override
	public void addEntryToIndex(String keyword, String url) {
		keyword = StringUtil.sanitize(keyword);
		if (!StringUtil.isEmpty(keyword)) {
			if (index.get(keyword) == null) {
				index.put(keyword, new IndexEntry(keyword, url));
			} else {
				IndexEntry entry = index.get(keyword);
				entry.setCount(entry.getCount() + 1);
				if (!entry.getUrls().contains(url)) {
					entry.addUrl(url);
				}
			}
		}
	}

	@Override
	public ConcurrentMap<?, ?> getIndex() {
		return index;
	}

	@Override
	public int getIndexSize() {
		return index == null ? 0 : index.size();
	}
	
	@Override
	public IndexEntry get(int i) {
		return index.get(i);
	}

	@Override
	public IndexEntry get(String keyword) {
		return index.get(keyword);
	}
	
	/**
	 * Determine whether a word is contained within a list
	 * @param inclusions
	 * @param word
	 * @return boolean
	 */
	private boolean containsIgnoreCase(List<String> inclusions, String word) {
		for (String each : inclusions) {
			 if (each.equalsIgnoreCase(word)) return true;
		}
		return false;
	}

}
