package com.github.chrox.kpvbooklet.dictadapter;

public class DictQuery531 extends DictAdapter{
	public DictQuery531() {
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
		return "RQ";
	}
	
	protected String getAllDictsMethod() {
		return "ehc";
	}
	
	protected String getLookupWordMethod() {
		return "Ou";
	}
	protected String getLanguageInMethod() {
		return "AT";
	}
	protected String getLanguageOutMethod() {
		return "ns";
	}
	protected String getDictNameMethod() {
		return "MT";
	}
	protected String getDictTitleMethod() {
		return "Vs";
	}
	protected String getDictIDMethod() {
		return "WU";
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
