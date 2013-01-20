package com.github.chrox.kpvbooklet;

import java.io.*;
import java.net.URI;
import java.lang.reflect.Field;

import com.amazon.ebook.booklet.reader.ReaderBooklet;

import com.github.chrox.kpvbooklet.ccadapter.CCAdapter;
import com.github.chrox.kpvbooklet.dictadapter.DictAdapter;
import com.github.chrox.kpvbooklet.util.Log;

/**
 * A Booklet for starting kpdfviewer. 
 * 
 * Modified by chrox@github.
 * 
 * @author Patric Mueller &lt;bhaak@gmx.net&gt;
 */
public class KPVBooklet extends ReaderBooklet {

	private final String kpdfviewer = "/mnt/us/kindlepdfviewer/kpdf.sh";
	
	private Process kpdfviewerProcess;
	private CCAdapter ccrequest = CCAdapter.INSTANCE;
	private static final PrintStream logger = Log.INSTANCE;
	
	public KPVBooklet() {
		log("I: KPVBooklet");
	}
	
	public void start(URI contentURI) {
		try {
			DictAdapter dictionary = DictAdapter.INSTANCE;
			log("T: test dict query: " + dictionary.query("dictionary"));
		} catch (Exception e) {
			log("E: " + e.toString());
		}
		
		log("I: start()");
		log("I: contentURI " + contentURI.toString());
		String path = contentURI.getPath();
		int dot = path.lastIndexOf('.');
		String ext = (dot == -1) ? "" : path.substring(dot+1).toLowerCase();
		if (ext.equals("pdf")) {
			if ( contentURI.getQuery() != null ) {
				log("I: Opening " + path + " with native reader...");
				super.start(contentURI);
				return;
			}
		}
		log("I: Opening " + path + " with kindlepdfviewer...");
		String[] cmd = new String[] {kpdfviewer, path};
		try {
			kpdfviewerProcess = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			log("E: " + e.toString());
		}
		
		Thread thread = new kpdfviewerWaitThread(path);
		thread.start();
	}

	public void stop() {
		log("I: stop()");
		// Stop kpdfviewer
		if (kpdfviewerProcess != null) {
			try {
				killQuitProcess(kpdfviewerProcess);
				log("I: kpdf.sh process killed with return value " + kpdfviewerProcess.exitValue());
			} catch (Exception e) {
				log("E: " + e.toString());
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
				log("E: " + e.toString());
			}
			
			// update content catlog after kpdfviewer exits
			ccrequest.updateCC(content_path, extractPercentFinished(content_path));
			
			// sent go home lipc event after kpdfviewer exits
			try {
				Runtime.getRuntime().exec("lipc-set-prop com.lab126.appmgrd start app://com.lab126.booklet.home");
			} catch (IOException e) {
				log("E: " + e.toString());
			}
		}
	}
	
	/**
	 * Extract last_percent in document history file
	 * @param file path
	 */
	private float extractPercentFinished(String path) {
		float percent_finished = 0.0f;
		String history_dir = "/mnt/us/kindlepdfviewer/history/";
		int slash = path.lastIndexOf('/');
		String parentname = (slash == -1) ? "" : path.substring(0, slash+1);
		String basename = (slash == -1) ? "" : path.substring(slash+1);
		String histroypath = history_dir + "[" + parentname.replace('/','#') + "] " + basename + ".lua";
		log("I: found history file: " + histroypath);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(histroypath)));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("[\"percent_finished\"]") > -1) {
					log("I: found percent_finished line: " + line);
					int equal = line.lastIndexOf('=');
					int comma = line.lastIndexOf(',');
					if (equal != -1 && comma != -1) {
						String value = line.substring(equal+1, comma).trim();
						percent_finished = Float.parseFloat(value) * 100;
					}
				}
		    }
		    br.close();
		} catch (IOException e) {
			log("E: " + e.toString());
		}
		return percent_finished;
	}
	
	private void log(String msg) {
		logger.println(msg);
	}
}
