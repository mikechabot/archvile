package com.archvile.service;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.archvile.utils.StringUtil;

public class DocumentService {

	/**
	 * Retrieve a document object given a URL
	 * @param url
	 * @return Document
	 * @throws IOException
	 */
	public Document getDocumentFromUrl(String url) throws IOException  {
		if (StringUtil.isEmpty(url)) throw new IllegalArgumentException("URL cannot be null");
		if (url.startsWith("http")) {
			return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get();
		} else {
			return Jsoup.parse(new File(url), "UTF-8");
		}
	}	
	
}
