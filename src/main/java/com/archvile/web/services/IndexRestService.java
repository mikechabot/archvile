package com.archvile.web.services;

import com.archvile.index.Index;
import com.archvile.index.IndexEntry;

import com.archvile.web.Action;
import com.archvile.web.HttpMethod;
import com.archvile.web.JsonRestService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexRestService extends JsonRestService {

    private Index index = new Index();

    @Override
    public void init() {
        registerRequestActions();
    }

    @Override
    protected void registerRequestActions() {
        registerRequestAction(new GetAction(HttpMethod.GET));
        registerRequestAction(new DeleteAction(HttpMethod.DELETE));
    }

    @Override
    protected void registerMappings() {
        // No mappings to define
    }

    public class GetAction extends Action {
        public GetAction(HttpMethod methodType) {
            super(methodType);
        }

        @Override
        public JsonArray execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            JsonArray json = new JsonArray();
            for (Object obj : index.getIndex().keySet()) {
                String keyword = (String) obj;
                IndexEntry entry =  index.get(keyword);
                if (entry != null) {
                    JsonObject object = new JsonObject();
                    object.addProperty("keyword", entry.getKeyword());
                    object.addProperty("count", entry.getCount());
                    /* Add urls */
                    JsonArray urls = new JsonArray();
                    for (String each : entry.getUrls()) {
                        urls.add(new JsonPrimitive(each));
                    }
                    object.add("urls", urls);
                    json.add(object);
                }
            }
            return json;
        }
    }

    public class DeleteAction extends Action {
        public DeleteAction(HttpMethod methodType) {
            super(methodType);
        }

        @Override
        public JsonObject execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            index.deleteIndex();
            JsonObject results = new JsonObject();
            if (index.getIndex().size() == 0) {
                results.addProperty("success", true);
            } else {
                results.addProperty("success", false);
            }
            return results;
        }
    }
}
