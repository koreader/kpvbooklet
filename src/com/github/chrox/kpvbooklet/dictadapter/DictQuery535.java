package com.github.chrox.kpvbooklet.dictadapter;

public class DictQuery535 extends DictAdapter{
	public DictQuery535() {
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
		return "whc";
	}
	
	protected String getLookupWordMethod() {
		return "bU";
	}
	protected String getLanguageInMethod() {
		return "jt";
	}
	protected String getLanguageOutMethod() {
		return "Zu";
	}
	protected String getDictNameMethod() {
		return "gS";
	}
	protected String getDictTitleMethod() {
		return "lt";
	}
	protected String getDictIDMethod() {
		return "Xs";
	}
	protected String getResultsField() {
		return "";
	}
	protected String getResultIndexField() {
		return "";
	}
	protected String getDefinitionField() {
		return "H";
	}
}
