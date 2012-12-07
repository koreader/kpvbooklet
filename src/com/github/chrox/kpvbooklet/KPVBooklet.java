package com.github.chrox.kpvbooklet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.amazon.ebook.util.log.d;
import com.amazon.kindle.booklet.AbstractBooklet;
import com.amazon.kindle.restricted.runtime.Framework;
import com.amazon.kindle.restricted.content.catalog.ContentCatalog;

/**
 * A Booklet for starting kpdfviewer. 
 * 
 * Modified by chrox@github.
 * 
 * @author Patric Mueller &lt;bhaak@gmx.net&gt;
 */
public class KPVBooklet extends AbstractBooklet {

	private final d logger = d.CBb("KPVBooklet");
	private final String kpdfviewer = "/mnt/us/kindlepdfviewer/kpdf.sh";
	
	private Process kpdfviewerProcess;
	
	public KPVBooklet() {
		logger.hmA("KPVBooklet");
	}
	
	public void start(URI contentURI) {
		String path = contentURI.getPath();
		logger.hmA("Opening " + path + " with kindlepdfviewer...");
		String[] cmd = new String[] {kpdfviewer, path};
		try {
			kpdfviewerProcess = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			logger.xIb(e.toString(), e);
		}
		
		Thread thread = new kpdfviewerWaitThread(path);
		thread.start();
	}

	public void stop() {
		logger.hmA("stop()");
		// Stop kpdfviewer
		if (kpdfviewerProcess != null) {
			try {
				killQuitProcess(kpdfviewerProcess);
			} catch (Exception e) {
				logger.xIb(e.toString(), e);
			}
		}
		super.stop();
	}
	
	/** 
	 * Send a QUIT signal to a process.
	 * 
	 * See http://stackoverflow.com/questions/2950338/how-can-i-kill-a-linux-process-in-java-with-sigkill-process-destroy-does-sigte#answer-2951193
	 */
	private void killQuitProcess(Process process)
		throws InterruptedException, IOException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			Class cl = process.getClass();
			Field field = cl.getDeclaredField("pid");
			field.setAccessible(true);
			Object pidObject = field.get(process);

			Runtime.getRuntime().exec("kill -QUIT " + pidObject).waitFor();
		} else {
			throw new IllegalArgumentException("Needs to be a UNIXProcess");
		}
	}
	
	/** This thread waits for kpdfviewer to finish and then sends
	 * a BACKWARD lipc event. 
	 */
	class kpdfviewerWaitThread extends Thread {
		private String content_path = "";
		
		public kpdfviewerWaitThread(String path) {
			content_path = path;
		}
		public void run() {
			try {
				// wait for kpdfviewer to finish
				kpdfviewerProcess.waitFor();
			} catch (InterruptedException e) {
				logger.xIb(e.toString(), e);
			}
			// update content catlog after kpdfviewer finished
			updateCC(content_path, 0.0f);
			// sent go home lipc event after kpdfviewer finished
			try {
				Runtime.getRuntime().exec("lipc-set-prop com.lab126.appmgrd start app://com.lab126.booklet.home");
			} catch (IOException e) {
				logger.xIb(e.toString(), e);
			}
		}
	}
	
	/**
	 * Update lastAccess and displayTag fields in ContentCatlog
	 * @param file path
	 */
	private void updateCC(String path, float percentFinished) {
		long lastAccess = new Date().getTime() / 1000L;
		int dot = path.lastIndexOf('.');
		String tag = (dot == -1) ? "" : path.substring(dot+1).toUpperCase();
		path = JSONObject.escape(path);
		String json_query = "{\"filter\":{\"Equals\":{\"value\":\"" + path + "\",\"path\":\"location\"}},\"type\":\"QueryRequest\",\"maxResults\":1,\"sortOrder\":[{\"order\":\"descending\",\"path\":\"lastAccess\"},{\"order\":\"ascending\",\"path\":\"titles[0].collation\"}],\"startIndex\":0,\"id\":1,\"resultType\":\"fast\"}";
		JSONObject json = CCRequest("query", json_query);
		JSONArray values = (JSONArray) json.get("values");
		JSONObject value =(JSONObject) values.get(0);
		String uuid = (String) value.get("uuid");
		String json_change = "{\"commands\":[{\"update\":{\"uuid\":\"" + uuid + "\",\"lastAccess\":" + lastAccess + ",\"percentFinished\":" + percentFinished + ",\"displayTags\":[\"" + tag + "\"]" + "}}],\"type\":\"ChangeRequest\",\"id\":1}";
		CCRequest("change", json_change);
		logger.hmA("UpdateCC:file:" + path + ",lastAccess:" + lastAccess + ",percentFinished:" + percentFinished);
	}
	
	/**
	 * Perform CC request of type "query" and "change"
	 * @param req_type request type of "query" or "change"
	 * @param req_json request json string
	 * @return return json object
	 */
	private JSONObject CCRequest(String req_type, String req_json) {
		ContentCatalog CC = (ContentCatalog)Framework.getService(ContentCatalog.class);
		JSONObject json = CC.eL(req_type, req_json, 200, 5);
		return json;
	}
}
