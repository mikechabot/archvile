package com.archvile.index;

import java.util.ArrayList;
import java.util.List;

public class IndexEntry {

	private String keyword;
	private List<String> urls;
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
