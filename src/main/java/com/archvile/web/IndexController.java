package com.archvile.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.archvile.Archvile;
import com.archvile.index.IndexEntry;
import com.archvile.service.IndexService;

public class IndexController extends Controller {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(IndexController.class);
	
	private Archvile archvile = new Archvile();
	private IndexService indexService = new IndexService();
	
	@Override
	protected String basePath() { return ""; }	
	
	@Override
	protected void initActions() {
		addAction("/get", new GetAction());
		addAction("/start", new StartAction());	
	}

	@Override
	protected Action defaultAction() {
		return new GetAction();
	}
	
	public class GetAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map<String, IndexEntry> index = indexService.getIndex();
	        request.setAttribute("index", index);
//			Map<String, IndexEntry> index = new HashMap<String, IndexEntry>();
//			index.put("test", new IndexEntry("test", "http://localhost"));
//			request.setAttribute("index", index);
			return basePath() + "/index.jsp";		
		}
	}
	
	public class StartAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			log.info("Crawling...");
			archvile.crawl("http://www.woot.com");
			return basePath() + "/index.jsp";		
		}
	}
	
}
