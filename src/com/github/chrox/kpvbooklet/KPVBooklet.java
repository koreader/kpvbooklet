package com.github.chrox.kpvbooklet;

import java.io.*;
import java.net.URI;
import java.lang.reflect.Field;

import com.amazon.ebook.booklet.reader.ReaderBooklet;

import com.github.chrox.kpvbooklet.ccadapter.CCAdapter;
import com.github.chrox.kpvbooklet.util.Log;

/**
 * A booklet for launching koreader directly from Kindle home screen.
 * 
 * Modified by chrox@github.
 * 
 * @author Patric Mueller &lt;bhaak@gmx.net&gt;
 */
public class KPVBooklet extends ReaderBooklet {

	private final String koreader = "/mnt/us/koreader/koreader.sh";
	private final String kor_history = "/mnt/us/koreader/history/";
	private final String kpdfview = "/mnt/us/kindlepdfviewer/kpdf.sh";
	private final String kpv_history = "/mnt/us/kindlepdfviewer/history/";
	
	private Process readerProcess;
	private String history_dir;
	private CCAdapter ccrequest = CCAdapter.INSTANCE;
	private static final PrintStream logger = Log.INSTANCE;
	
	public KPVBooklet() {
		log("I: KPVBooklet");
		DictBackend.addDictBackend();
	}
	
	public void start(URI contentURI) {	
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
		log("I: Opening " + path + " with koreader...");
		String[] cmd = new String[] {koreader, path};
		try {
			readerProcess = Runtime.getRuntime().exec(cmd);
			history_dir = kor_history;
		} catch (IOException e) {
			readerProcess = null;
			log("W: koreader not found, will check legacy kindlepdfviewer instead.");
		}
		// fallback to kpdf.sh if we cannot exec koreader.sh
		if (readerProcess == null) {
			log("I: Opening " + path + " with kindlepdfviewer...");
			cmd[0] = kpdfview;
			history_dir = kpv_history;
			try {
				readerProcess = Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				log("E: " + e.toString());
			}
		}
		
		Thread thread = new ReaderWaitThread(history_dir, path);
		thread.start();
		
		// wait 3 seconds for pillow disable in koreader.sh
		try {
		    Thread.sleep(3000);
		} catch(InterruptedException e) {
			log("E: " + e.toString());
		}
		// enable pillow for activity indicator
		try {
			Runtime.getRuntime().exec("lipc-set-prop com.lab126.pillow disableEnablePillow enable");
		} catch (IOException e) {
			log("E: " + e.toString());
		}
	}

	public void stop() {
		log("I: stop()");
		if (readerProcess != null) {
			try {
				killQuitProcess(readerProcess);
				log("I: reader process killed with return value " + readerProcess.exitValue());
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
	
	/** This thread waits for reader process to finish and then update content catalog  
	 */
	class ReaderWaitThread extends Thread {
		private String history_dir = "";
		private String content_path = "";
		
		public ReaderWaitThread(String dir, String path) {
			history_dir = dir;
			content_path = path;
		}
		public void run() {
			try {
				// wait for reader process to finish
				readerProcess.waitFor();
			} catch (InterruptedException e) {
				log("E: " + e.toString());
			}
			
			try {
				// update content catlog after reader exits
				ccrequest.updateCC(content_path, extractPercentFinished(history_dir, content_path));
			} catch (Exception e) {
				log("E: " + e.toString());
			}
			
			// sent go home lipc event after reader exits
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
	private float extractPercentFinished(String history_dir, String path) {
		float percent_finished = 0.0f;
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
						// decimal separator of number is comma in some locales
						value = value.replace(',', '.');
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
