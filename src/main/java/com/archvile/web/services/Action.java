package com.archvile.web.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import java.io.IOException;

public abstract class Action {

	private HttpMethod methodType;

	public Action(HttpMethod methodType) {
		this.methodType = methodType;
	}
	
	public HttpMethod getMethodType() {
		return methodType;
	}

	public abstract JsonObject execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
}
