package com.github.chrox.kpvbooklet.dictadapter;

public class DictQuery520 extends DictAdapter{
	public DictQuery520() {
		try {
			// found at lib/kaf.jar
			Framework = Class.forName("com.amazon.kindle.restricted.runtime.Framework");
			// found at lib/ReaderSDK.jar
			ReaderSDK = Class.forName("com.amazon.ebook.booklet.reader.sdk.ReaderSDK");
			// found at lib/ReaderContentSDK.jar
			DictMgr = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.K");
			Dictionary = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary");
			ResultEntry = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.g");
			Definition = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.H");
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	protected String getServiceMethod() {
		return "getService";
	}
	
	protected String getDictMgrMethod() {
		return "lR";
	}
	
	protected String getAllDictsMethod() {
		return "Zdc";
	}
	
	protected String getLookupWordMethod() {
		return "BQ";
	}
	protected String getLanguageInMethod() {
		return "PP";
	}
	protected String getLanguageOutMethod() {
		return "Eq";
	}
	protected String getDictNameMethod() {
		return "YQ";
	}
	protected String getDictTitleMethod() {
		return "cR";
	}
	protected String getDictIDMethod() {
		return "TR";
	}
	protected String getResultsField() {
		return "H";
	}
	protected String getResultIndexField() {
		return "B";
	}
	protected String getDefinitionField() {
		return "B";
	}
}
