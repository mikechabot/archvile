package com.archvile.web.services;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

public abstract class JsonService extends HttpServlet {

	private static final long serialVersionUID = -4363034303978130926L;

	private static Logger log = Logger.getLogger(JsonService.class);
	
	private Map<HttpMethod, Action> actions = new HashMap<>();

    public abstract void init();
    protected abstract void registerRestActions();

	public void registerAction(Action action) {
        actions.put(action.getMethodType(), action);
	}

	public boolean isActionImplemented(HttpMethod method) {
		if (actions.get(method) == null) {
			log.info(method + " not implemented");
			return false;
		}
		return true;
	}
	
	public void doExecute(HttpMethod method, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isActionImplemented(method)) {
			JsonElement json = actions.get(method).execute(request, response);
			respond(response, json);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		}
	}

	public void respond(HttpServletResponse response, JsonElement json) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter printout = response.getWriter();
		printout.print(json);
		printout.flush();
	}

    /**
     * Handle GET requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doExecute(HttpMethod.GET, request, response);
	}

    /**
     * Handle POST requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doExecute(HttpMethod.POST, request, response);
	}

    /**
     * Handle PUT requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doExecute(HttpMethod.PUT, request, response);
	}

    /**
     * Handle DELETE requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doExecute(HttpMethod.GET, request, response);
	}

    /**
     * Parse a string from the request body
     * @param request
     * @return
     * @throws IOException
     */
    protected String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                reader =  new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = reader.read(charBuffer)) > 0) {
                    sb.append(charBuffer, 0, bytesRead);
                }
            } else {
                sb.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return sb.toString();
    }
	
}
