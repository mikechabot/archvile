package com.archvile.web.services;

import com.archvile.index.Index;
import com.archvile.index.IndexEntry;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mike on 11/13/2014.
 */
public class IndexService extends JsonService {

    private Index index = new Index();

    @Override
    public void init() {
        registerAction(new GetAction(HttpMethod.GET));
    }

    public class GetAction extends Action {
        public GetAction(HttpMethod methodType) { super(methodType); }
        @Override
        public JsonObject execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
            JsonObject json = new JsonObject();
            for (int i=0; i < index.getIndex().size() - 1; i++) {
                System.out.println("i->" + i);
                IndexEntry entry = index.get(i);
                if (entry != null) {
                    System.out.println("entry->"+entry.getKeyword());
                    JsonObject object = new JsonObject();
                    object.addProperty("keyword", entry.getKeyword());
                    object.addProperty("count", entry.getCount());
                    JsonArray urls = new JsonArray();
                    for (String url : entry.getUrls()) {
                        JsonObject temp = new JsonObject();
                        temp.addProperty("url", url);
                        urls.add(temp);
                    }
                    object.add("urls", urls);
                    json.add(String.valueOf(i), object);
                }
            }
            Gson gson = new Gson();
            json.addProperty("index", gson.toJson(index.getIndex()));
            return json;
        }
    }

    @Override
    protected void registerRestActions() {
        //
    }
}
