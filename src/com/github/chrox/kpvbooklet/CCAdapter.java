package com.github.chrox.kpvbooklet;

import java.lang.reflect.*;
import org.json.simple.JSONObject;

import com.amazon.kindle.restricted.content.catalog.ContentCatalog;
import com.amazon.kindle.restricted.runtime.Framework;

public abstract class CCAdapter {
	public static final CCAdapter INSTANCE = getAdapterInstance();

	private static CCAdapter getAdapterInstance() {
		String className = "com.github.chrox.kpvbooklet.CCRequest531";
		//String className = "com.github.chrox.kpvbooklet.CCRequest512";
		try {
			// this class exists in FW 5.3.1, but not in FW 5.2.0.
			Class.forName("com.amazon.ebook.util.text.P");
		} catch (Throwable t) {
			className = "com.github.chrox.kpvbooklet.CCRequest520";
		}
		try {
			// this class exists in FW 5.1.2, but thanks to obfuscation, not in
			// PW firmwares.
			Class.forName("com.amazon.ebook.util.text.LanguageTag");
			className = "com.github.chrox.kpvbooklet.CCRequest512";
		} catch (Throwable t) {}
		
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
			Method perform = ContentCatalog.class.getDeclaredMethod(INSTANCE.getPerformName(), new Class[] {String.class, String.class, int.class, int.class});
			JSONObject json = (JSONObject) perform.invoke(CC, new Object[] { req_type, req_json, new Integer(200), new Integer(5)});
			return json;
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	public abstract String getPerformName();
}
