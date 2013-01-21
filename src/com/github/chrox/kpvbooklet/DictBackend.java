package com.github.chrox.kpvbooklet;

import java.io.PrintStream;

import com.amazon.kindle.util.lipc.LipcException;
import com.amazon.kindle.util.lipc.LipcPropertyAdapter;
import com.amazon.kindle.util.lipc.LipcPropertyProvider;
import com.amazon.kindle.util.lipc.LipcService;
import com.amazon.kindle.util.lipc.LipcSource;
import com.github.chrox.kpvbooklet.dictadapter.DictAdapter;
import com.github.chrox.kpvbooklet.util.Log;

/* Usage example:
 		lipc-set-prop -s com.lab126.booklet.kpvbooklet.dict lookup "kindle"
 		lipc-get-prop -esq com.lab126.booklet.kpvbooklet.word "kindle"
*/
class DictBackend {
	private static final String dictsource = "com.lab126.booklet.kpvbooklet.dict";
	private static final String wordsource = "com.lab126.booklet.kpvbooklet.word";
	private static final PrintStream logger = Log.INSTANCE;
	
	private static class WordProperties extends LipcPropertyAdapter {
		private DictAdapter dictionary = DictAdapter.INSTANCE;
		
		public String getStringProperty(String word) throws LipcException {
	        return dictionary.query(word);
	    }
	}

	private static class DictProperties extends LipcPropertyAdapter {
		private LipcSource wordSource = null;
		
		public DictProperties () throws LipcException {
			wordSource = LipcService.getInstance().createSource(wordsource);
		}
		
	    public void setProperty(String property, String word) throws LipcException {
	        if(property.equals("lookup")) {
	        	if (wordSource != null) {
		        	LipcPropertyProvider wordproperty = new WordProperties();
		        	wordSource.exportStringProperty(word, wordproperty, 1);
	        	}
	        }
	    }
	}
	
    public static void addDictBackend() {
		try {
			LipcSource source = LipcService.getInstance().createSource(dictsource);
			LipcPropertyProvider dictproperty = new DictProperties();
			source.exportStringProperty("lookup", dictproperty, 2);
		} catch(LipcException e) {
			logger.println("E: " + e.toString());
        }
	}
}
