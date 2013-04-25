package com.github.chrox.kpvbooklet.dictadapter;

import java.io.*;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import org.json.simple.JSONValue;

import com.github.chrox.kpvbooklet.util.KindleDevice;
import com.github.chrox.kpvbooklet.util.Log;

public abstract class DictAdapter {
	protected static Class Framework = null;
	protected static Class ReaderSDK = null;
	protected static Class DictMgr = null;
	protected static Class Dictionary = null;
	protected static Class ResultEntry = null;
	protected static Class Definition = null;
	
	public static final DictAdapter INSTANCE = getAdapterInstance();
	protected static final PrintStream logger = Log.INSTANCE;

	private static DictAdapter getAdapterInstance() {
		String version = KindleDevice.VERSION;
		String className = "";
		if ("5.3.5".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery535";
		} else if ("5.3.4".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery534";
		} else if ("5.3.3".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery533";
		} else if ("5.3.2".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery532";
		} else if ("5.3.1".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery531";
		} else if ("5.3.0".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery530";
		} else if ("5.2.0".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery520";
		} else if ("5.1.2".equals(version)) {
			className = "com.github.chrox.kpvbooklet.dictadapter.DictQuery512";
		} 
		
		try {
			Class clazz = Class.forName(className);
			return (DictAdapter) clazz.newInstance();
		} catch (Throwable t) {
			throw new IllegalStateException("Unable to instantiate class: "
					+ className);
		}
	}
	
	public void log(String msg) {
		logger.println(msg);
	}
	
	/**
	 * Perform dictionary query on Amazon's native dictionaries.
	 * @param  word or phrase as a String
	 * @return JSONString of query results including dict name, dict locale,
	 * 		   dict filename and word definition.
	 */
	public String query(String word) {
		try {
			Object[] dicts = getDicts();
			List results_list = new LinkedList();
			
			for (int i = 0; i < dicts.length; i++) {
				Object dict = (Object)dicts[i];
				Object resultset = getResultSet(dict, word);
				if (resultset != null) {
					Map query_result = new LinkedHashMap();
					query_result.put("dict", getDictName(dict));
					query_result.put("lang", getDictLanguage(dict));
					query_result.put("ID", getDictID(dict));
					query_result.put("definition", getDictDefinition(resultset));
					results_list.add(query_result);
				}
			}
			
			return JSONValue.toJSONString(results_list);
			
		} catch (Throwable t) {
			log("E: " + t.toString());
			return "";
		}
	}
	
	private Object[] getDicts() {
		try {
			// invoke Framework's static method 'getService' with ReaderSDK class
			// This will return a instance of ReaderSDK.
			Object readersdk = Framework.getDeclaredMethod(getServiceMethod(), new Class[] {Class.class}).invoke(null, new Object[] {ReaderSDK});
			// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards.SystemsCardProvider (grep with "No default dictionary set")
			Object dictmgr = ReaderSDK.getDeclaredMethod(getDictMgrMethod(), new Class[] {}).invoke(readersdk, null);
			// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards.SystemsCardProvider (grep with "No default dictionary set")
			List dicts = (List) DictMgr.getDeclaredMethod(getAllDictsMethod(), new Class[] {}).invoke(dictmgr, null);
			return dicts.toArray();
		} catch (Throwable t) {
			t.printStackTrace(logger);
			throw new RuntimeException(t.toString());
		}
	}
	
	private Object getResultSet(Object dict, String word) {
		try {
			// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards (grep with "DictionaryCard_do_lookup")
			return Dictionary.getDeclaredMethod(getLookupWordMethod(), new Class[] {String.class, String.class}).
					invoke(dict, new Object[] {word, ""});
		} catch (Throwable t) {
			t.printStackTrace(logger);
			throw new RuntimeException(t.toString());
		}
	}
	
	private String getDictName(Object dict) {
		try {
			// found at com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary
			return (String) Dictionary.getDeclaredMethod(getDictNameMethod(), new Class[] {}).invoke(dict, null);
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	private String getDictLanguage(Object dict) {
		try {
			// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards (grep "DictionaryCard_set_dict_locale")
			return (String) Dictionary.getDeclaredMethod(getLanguageOutMethod(), new Class[] {}).invoke(dict, new Object[] {});
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	private String getDictID(Object dict) {
		try {
			// found at com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary
			return (String) Dictionary.getDeclaredMethod(getDictIDMethod(), new Class[] {}).invoke(dict, new Object[] {});
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	private String getDictDefinition(Object result) {
		try {
			if (getResultsField().length() == 0) {
				// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards (grep with "DictionaryCard_getCard")
				// new in 5.3.4
				return (String) ResultEntry.getField(getDefinitionField()).get(result);
			} else {
				// usage found at com.amazon.ebook.booklet.reader.plugin.systemcards (grep with "DictionaryCard_do_lookup")
				int index = ((Integer) ResultEntry.getField(getResultIndexField()).get(result)).intValue();
				Object[] definitions = (Object[]) ResultEntry.getField(getResultsField()).get(result);
				return (String) Definition.getField(getDefinitionField()).get(definitions[index]);
			}
		} catch (Throwable t) {
			t.printStackTrace(logger);
			throw new RuntimeException(t.toString());
		}
	}
	// declared in com.amazon.kindle.restricted.runtime.Framework
	protected abstract String getServiceMethod();
	// declared in com.amazon.ebook.booklet.reader.sdk.ReaderSDK
	protected abstract String getDictMgrMethod();
	// declared in com.amazon.ebook.booklet.reader.sdk.content.dictionary.K
	protected abstract String getAllDictsMethod();
	// declared in com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary
	protected abstract String getLookupWordMethod();
	protected abstract String getLanguageInMethod();
	protected abstract String getLanguageOutMethod();
	protected abstract String getDictNameMethod();
	protected abstract String getDictTitleMethod();
	protected abstract String getDictIDMethod();
	protected abstract String getResultsField();
	protected abstract String getResultIndexField();
	protected abstract String getDefinitionField();
}
