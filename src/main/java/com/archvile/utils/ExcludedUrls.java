package com.archvile.utils;

import java.util.Arrays;

public class ExcludedUrls {

	private static final String[] exclusions = {"doubleclick.net"};
	
	public static boolean isExcluded(String url) {
		url = url.toLowerCase();
		for (String each : Arrays.asList(exclusions)) {
			if (url.contains(each)) return true;
		}
		return false;
	}

}
