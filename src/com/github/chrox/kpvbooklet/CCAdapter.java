package com.github.chrox.kpvbooklet;

import java.lang.reflect.Method;
import org.json.simple.JSONObject;

import com.amazon.kindle.restricted.content.catalog.ContentCatalog;
import com.amazon.kindle.restricted.runtime.Framework;

public abstract class CCAdapter {
	public static final CCAdapter INSTANCE = getAdapterInstance();

	private static CCAdapter getAdapterInstance() {
		String className = "com.github.chrox.kpvbooklet.CCRequest";
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
	
	public abstract String getPerformName();
}
