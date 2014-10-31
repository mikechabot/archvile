package com.archvile.service;

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
		return Jsoup.connect(url).get();
	}
	
}
