package com.archvile.web.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archvile.web.Action;
import com.archvile.web.HttpMethod;
import com.archvile.web.JsonRestService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.apache.log4j.Logger;

import com.archvile.Archvile;

import java.io.IOException;

public class ArchvileRestService extends JsonRestService {

	private static Logger log = Logger.getLogger(ArchvileRestService.class);
	
	private Archvile archvile = Archvile.getInstance();

    @Override
	public void init() {
        registerRequestActions();
        registerMappings();
	}

    @Override
    public void registerRequestActions() {
        registerRequestAction(new GetAction(HttpMethod.GET));
        registerRequestAction(new PostAction(HttpMethod.POST));
    }

    @Override
    public void registerMappings() {
        /* Register GET urls */
        registerGetMapping("/statistics", new GetStatisticsAction(HttpMethod.GET));
        /* Register POST urls */
        registerPostMapping("/start", new PostStartAction(HttpMethod.POST));
        registerPostMapping("/stop", new PostStopAction(HttpMethod.POST));
        registerPostMapping("/status", new PostStatusAction(HttpMethod.POST));
    }

    public class GetAction extends Action {
        public GetAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String action = request.getPathInfo();
            if (getMappings.get(action) == null) {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return null;
            } else {
                return getMappings.get(action).execute(request, response);
            }
        }
    }

    public class PostAction extends Action {
        public PostAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonElement execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String action = request.getPathInfo();
            if (postMappings.get(action) == null) {
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                return null;
            } else {
                return postMappings.get(action).execute(request, response);
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

                /* Get the seed URL */
                JsonElement seedUrl = object.get("seedUrl");
                if (seedUrl == null || seedUrl.getAsString().isEmpty()) {
                    log.info("Start request cannot be initiated without a seed URL");
                    results.addProperty("isRunning", archvile.isRunning());
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
                results.addProperty("isRunning", archvile.isRunning());
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