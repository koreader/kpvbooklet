package com.github.chrox.kpvbooklet.dictadapter;

public class DictQuery534 extends DictAdapter{
	public DictQuery534() {
		try {
			// found at lib/kaf.jar
			Framework = Class.forName("com.amazon.kindle.restricted.runtime.Framework");
			// found at lib/ReaderSDK.jar
			ReaderSDK = Class.forName("com.amazon.ebook.booklet.reader.sdk.ReaderSDK");
			// found at lib/ReaderContentSDK.jar
			DictMgr = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.H");
			Dictionary = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary");
			ResultEntry = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.g");
			// Definition class is deprecated in 5.3.4
			Definition = null;
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	protected String getServiceMethod() {
		return "getService";
	}
	
	protected String getDictMgrMethod() {
		return "cP";
	}
	
	protected String getAllDictsMethod() {
		return "Mic";
	}
	
	protected String getLookupWordMethod() {
		return "Lt";
	}
	protected String getLanguageInMethod() {
		return "TU";
	}
	protected String getLanguageOutMethod() {
		return "Cu";
	}
	protected String getDictNameMethod() {
		return "Ns";
	}
	protected String getDictTitleMethod() {
		return "bT";
	}
	protected String getDictIDMethod() {
		return "Xt";
	}
	protected String getResultsField() {
		return "";
	}
	protected String getResultIndexField() {
		return "";
	}
	protected String getDefinitionField() {
		return "B";
	}
}
