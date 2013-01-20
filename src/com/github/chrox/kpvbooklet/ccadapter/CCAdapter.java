package com.github.chrox.kpvbooklet.ccadapter;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazon.kindle.restricted.content.catalog.ContentCatalog;
import com.amazon.kindle.restricted.runtime.Framework;
import com.github.chrox.kpvbooklet.util.Log;

public abstract class CCAdapter {
	public static final CCAdapter INSTANCE = getAdapterInstance();
	private static final PrintStream logger = Log.INSTANCE;

	private static CCAdapter getAdapterInstance() {
		String className = "com.github.chrox.kpvbooklet.ccadapter.CCRequest";
		try {
			Class clazz = Class.forName(className);
			return (CCAdapter) clazz.newInstance();
		} catch (Throwable t) {
			throw new IllegalStateException("Unable to instantiate class: "
					+ className);
		}
	}
	
	/**
	 * Perform CC request of type "query" and "change"
	 * @param req_type request type of "query" or "change"
	 * @param req_json request json string
	 * @return return json object
	 */
	public JSONObject perform(String req_type, String req_json) {
		ContentCatalog CC = (ContentCatalog)Framework.getService(ContentCatalog.class);
		try {
			
			Method perform = null;
			
			// decompilation approach
			//Method perform = ContentCatalog.class.getDeclaredMethod(INSTANCE.getPerformName(), new Class[] {String.class, String.class, int.class, int.class});
			
			// enumeration approach 
			Class[] signature = {String.class, String.class, int.class, int.class};
			Method[] methods = ContentCatalog.class.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Class[] params = methods[i].getParameterTypes();
				if ( params.length == signature.length ) {
					int j;
				    for (j = 0; j < signature.length && params[j].isAssignableFrom( signature[j] ); j++ ) {}
				    if ( j == signature.length ) {
				    	perform = methods[i];
				    	break;
				    }
				}
			}
			
			if (perform != null) {
				JSONObject json = (JSONObject) perform.invoke(CC, new Object[] { req_type, req_json, new Integer(200), new Integer(5)});
				return json;
			}
			else {
				return new JSONObject();
			}
			
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	/**
	 * Update lastAccess and displayTag fields in ContentCatlog
	 * @param file path
	 */
	public void updateCC(String path, float percentFinished) {
		long lastAccess = new Date().getTime() / 1000L;
		int dot = path.lastIndexOf('.');
		String tag = (dot == -1) ? "" : path.substring(dot+1).toUpperCase();
		path = JSONObject.escape(path);
		String json_query = "{\"filter\":{\"Equals\":{\"value\":\"" + path + "\",\"path\":\"location\"}},\"type\":\"QueryRequest\",\"maxResults\":1,\"sortOrder\":[{\"order\":\"descending\",\"path\":\"lastAccess\"},{\"order\":\"ascending\",\"path\":\"titles[0].collation\"}],\"startIndex\":0,\"id\":1,\"resultType\":\"fast\"}";
		JSONObject json = perform("query", json_query);
		JSONArray values = (JSONArray) json.get("values");
		JSONObject value =(JSONObject) values.get(0);
		String uuid = (String) value.get("uuid");
		String json_change = "{\"commands\":[{\"update\":{\"uuid\":\"" + uuid + "\",\"lastAccess\":" + lastAccess + ",\"percentFinished\":" + percentFinished + ",\"displayTags\":[\"" + tag + "\"]" + "}}],\"type\":\"ChangeRequest\",\"id\":1}";
		perform("change", json_change);
		log("I: UpdateCC:file:" + path + ",lastAccess:" + lastAccess + ",percentFinished:" + percentFinished);
	}
	
	private void log(String msg) {
		logger.println(msg);
	}
	
	public abstract String getPerformName();
}
