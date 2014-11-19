package com.archvile.web.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archvile.utils.StringUtil;
import com.google.gson.*;
import org.apache.log4j.Logger;

import com.archvile.Archvile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArchvileService extends JsonService {

	private static final long serialVersionUID = -4208684745628055906L;

	private static Logger log = Logger.getLogger(ArchvileService.class);
	
	private Archvile archvile = Archvile.getInstance();

    private Map<String, Action> getActions = new HashMap<>();
    private Map<String, Action> postActions = new HashMap<>();

    @Override
	public void init() {
        registerAction(new GetAction(HttpMethod.GET));
        registerAction(new PostAction(HttpMethod.POST));
        registerRestActions();
	}

    @Override
    protected void registerRestActions() {
        getActions.put("/statistics", new GetStatisticsAction(HttpMethod.GET));
        postActions.put("/start", new PostStartAction(HttpMethod.POST));
        postActions.put("/stop", new PostStopAction(HttpMethod.POST));
        postActions.put("/status", new PostStatusAction(HttpMethod.POST));
    }

    public class GetAction extends Action {
        public GetAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String action = request.getPathInfo();
            if (getActions.get(action) == null) {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return null;
            } else {
                return getActions.get(action).execute(request, response);
            }
        }
    }

    public class PostAction extends Action {
        public PostAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String action = request.getPathInfo();
            if (postActions.get(action) == null) {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return null;
            } else {
                return postActions.get(action).execute(request, response);
            }
        }
    }

    public class GetStatisticsAction extends Action {
        public GetStatisticsAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) {
            /* Build a JSON object with statistics */
            JsonObject json = new JsonObject();
            json.addProperty("seedUrl", archvile.getSeedUrl());
            json.addProperty("lastRunDate", archvile.getLastRunDate());
            json.addProperty("lastRunDuration", archvile.getLastRunDuration());
            json.addProperty("currentRunDuration", archvile.getCurrentRunDuration());
            json.addProperty("urlsSize", archvile.getUrlsSize());
            JsonArray urlsVisited = new JsonArray();
            for (String each : archvile.getUrlsVisited()) {
                urlsVisited.add(new JsonPrimitive(each));
            }
            json.add("urlsVisited", urlsVisited);
            log.debug(json);
            return json;
        }
    }

    public class PostStartAction extends Action {
        public PostStartAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            JsonObject results = new JsonObject();
            try {
                /* Get parameters from the request */
                String json = getRequestBody(request);
                JsonObject object = new JsonParser().parse(json).getAsJsonObject();

                /* Ensure a seed URL was provided */
                JsonElement seedUrl = object.get("seedUrl");
                if (seedUrl == null || seedUrl.getAsString().isEmpty()) {
                    log.error("Start request cannot be initiated without a seed URL");
                    results.addProperty("success", false);
                    return results;
                }
                /* Get the search term */
                JsonElement searchTerms = object.get("searchTerms");

                /* Set parameters and start the crawler */
                archvile.setSeedUrl("http://" + seedUrl.getAsString());
                archvile.setSearchTerms(searchTerms == null ? null : searchTerms.getAsString());
                archvile.start();

            } catch (IllegalArgumentException e) {
                log.error("Request body cannot be null", e);
                results.addProperty("success", false);
            }

            /* Send back a results object */
            results.addProperty("isRunning", archvile.isRunning());
            return results;
        }
    }

    public class PostStopAction extends Action {
        public PostStopAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) {
            /* Stop archvile */
            archvile.stop();
            /* Send back a results object */
            JsonObject results = new JsonObject();
            results.addProperty("isRunning", archvile.isRunning());
            return results;
        }
    }

    public class PostStatusAction extends Action {
        public PostStatusAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) {
            JsonObject results = new JsonObject();
            results.addProperty("isRunning", archvile.isRunning());
            return results;
        }
    }

}