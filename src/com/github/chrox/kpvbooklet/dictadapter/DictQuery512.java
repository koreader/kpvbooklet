package com.github.chrox.kpvbooklet.dictadapter;

public class DictQuery512 extends DictAdapter{
	public DictQuery512() {
		try {
			// found at lib/kaf.jar
			Framework = Class.forName("com.amazon.kindle.restricted.runtime.Framework");
			// found at lib/ReaderSDK.jar
			ReaderSDK = Class.forName("com.amazon.ebook.booklet.reader.sdk.ReaderSDK");
			// found at lib/ReaderContentSDK.jar
			DictMgr = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.q");
			Dictionary = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.Dictionary");
			ResultEntry = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.e");
			Definition = Class.forName("com.amazon.ebook.booklet.reader.sdk.content.dictionary.g");
		} catch (Throwable t) {
			throw new RuntimeException(t.toString());
		}
	}
	
	protected String getServiceMethod() {
		return "getService";
	}
	
	protected String getDictMgrMethod() {
		return "TA";
	}
	
	protected String getAllDictsMethod() {
		return "B";
	}
	
	protected String getLookupWordMethod() {
		return "D";
	}
	protected String getLanguageInMethod() {
		return "A";
	}
	protected String getLanguageOutMethod() {
		return "H";
	}
	protected String getDictNameMethod() {
		return "b";
	}
	protected String getDictTitleMethod() {
		return "K";
	}
	protected String getDictIDMethod() {
		return "B";
	}
	protected String getResultIndexField() {
		return "K";
	}
	protected String getResultsField() {
		return "f";
	}
	protected String getDefinitionField() {
		return "K";
	}
}
