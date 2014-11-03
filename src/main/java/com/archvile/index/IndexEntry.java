package com.archvile.index;

import java.util.ArrayList;
import java.util.List;

public class IndexEntry {

	private String keyword;
	private int count;
	private List<String> urls;
	
	public IndexEntry(String url) {
		addUrl(url);
		count++;
	}

	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getUrls() {
		return urls;
	}
	
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
	public void addUrl(String url) {
		if (urls == null) urls = new ArrayList<>();
		urls.add(url);
	}

}
