package com.archvile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archvile.Archvile;
import com.archvile.service.IndexServiceImpl;

public class IndexController extends Controller {
	
	private static final long serialVersionUID = 1L;
	
	private Archvile archvile = new Archvile();
	private IndexServiceImpl indexService = new IndexServiceImpl();
	
	@Override
	protected String basePath() { return ""; }	
	
	@Override
	protected void initActions() {
		addAction("/get", new GetAction());
		addAction("/start", new StartAction());
		addAction("/stop", new StopAction());
	}

	@Override
	protected Action defaultAction() {
		return new GetAction();
	}
	
	public class GetAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        request.setAttribute("index", indexService.getIndex());
	        request.setAttribute("isRunning", archvile.isRunning());
	        request.setAttribute("lastRunDate", archvile.getLastRunDate());
	        request.setAttribute("lastRunDuration", archvile.getLastRunDuration());
	        request.setAttribute("currentRunDuration", archvile.getCurrentRunDuration());
	        request.setAttribute("urls", archvile.getUrlsSize());
	        request.setAttribute("seedUrl", archvile.getSeedUrl());
	        request.setAttribute("pagesViewed", archvile.getPagesViewed());
			return basePath() + "/index.jsp";		
		}
	}
	
	public class StartAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String seedUrl = request.getParameter("seedUrl");
			String searchTerms = request.getParameter("searchTerms");
			if (seedUrl == null || searchTerms == null || seedUrl.isEmpty() || searchTerms.isEmpty()) {
				return null;
			}
			archvile.setSeedUrl("http://" + seedUrl);
			archvile.setSearchTerms(searchTerms);
			archvile.start();
	        request.setAttribute("index", indexService.getIndex());
	        request.setAttribute("isRunning", archvile.isRunning());
	        request.setAttribute("lastRunDate", archvile.getLastRunDate());
	        request.setAttribute("lastRunDuration", archvile.getLastRunDuration());
	        request.setAttribute("currentRunDuration", archvile.getCurrentRunDuration());
	        request.setAttribute("urls", archvile.getUrlsSize());
	        request.setAttribute("seedUrl", archvile.getSeedUrl());
	        request.setAttribute("pagesViewed", archvile.getPagesViewed());
	        response.sendRedirect(request.getContextPath() + "/" + basePath());
	        return null;
		}
	}
	
	public class StopAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			archvile.stop();			
	        request.setAttribute("index", indexService.getIndex());
	        request.setAttribute("isRunning", archvile.isRunning());
	        request.setAttribute("lastRunDate", archvile.getLastRunDate());
	        request.setAttribute("lastRunDuration", archvile.getLastRunDuration());
	        request.setAttribute("currentRunDuration", archvile.getCurrentRunDuration());
	        request.setAttribute("urls", archvile.getUrlsSize());
	        request.setAttribute("pagesViewed", archvile.getPagesViewed());
	        response.sendRedirect(request.getContextPath() + "/" + basePath());
	        return null;
		}
	}
	
}
