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
 		lipc-set-prop -s com.github.koreader.kpvbooklet.dict lookup "kindle"
 		lipc-get-prop -esq com.github.koreader.kpvbooklet.word "kindle"
*/
class DictBackend {
	private static final String dictsource = "com.github.koreader.kpvbooklet.dict";
	private static final String wordsource = "com.github.koreader.kpvbooklet.word";
	private static final PrintStream logger = Log.INSTANCE;
	
	private static class WordProperties extends LipcPropertyAdapter {
		private DictAdapter dictionary = DictAdapter.INSTANCE;
		
		public String getStringProperty(String word) throws LipcException {
	        return dictionary.query(word);
	    }
	}

	private static class DictProperties extends LipcPropertyAdapter {
		private LipcSource wordSource = null;
		private DictAdapter dictionary = DictAdapter.INSTANCE;
		
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
	        // TODO: this show property is not finished yet
	        if(property.equals("show")) {
	        	DictDialog.post("test", dictionary.getDictView(word));
	        }
	    }
	}
	
    public static void addDictBackend() {
		try {
			LipcSource source = LipcService.getInstance().createSource(dictsource);
			LipcPropertyProvider dictproperty = new DictProperties();
			source.exportStringProperty("lookup", dictproperty, 2);
			source.exportStringProperty("show", dictproperty, 2);
		} catch(LipcException e) {
			logger.println("E: " + e.toString());
			e.printStackTrace(logger);
        }
	}
}
