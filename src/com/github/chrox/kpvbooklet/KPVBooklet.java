package com.github.chrox.kpvbooklet;

import java.io.*;
import java.net.URI;
import java.lang.reflect.Field;
import java.io.PrintStream;
import java.util.Properties;

import org.kxml2.io.KXmlParser;

import com.github.chrox.kpvbooklet.ccadapter.CCAdapter;
import com.github.chrox.kpvbooklet.util.Log;

import com.amazon.ebook.booklet.reader.ReaderBooklet;

import com.amazon.kindle.util.lipc.LipcException;
import com.amazon.kindle.util.lipc.LipcPropertyAdapter;
import com.amazon.kindle.util.lipc.LipcPropertyProvider;
import com.amazon.kindle.util.lipc.LipcService;
import com.amazon.kindle.util.lipc.LipcSource;

/* Usage example:
 		lipc-get-prop -eiq com.github.koreader.kpvbooklet.timer count
 		lipc-set-prop -i com.github.koreader.kpvbooklet.timer add 1
 		lipc-set-prop -i com.github.koreader.kpvbooklet.timer set 1
 		lipc-set-prop -i com.github.koreader.kpvbooklet.timer reset 0
*/
class BookletTimer {
	private static final String timersource = "com.github.koreader.kpvbooklet.timer";
	private static final PrintStream logger = Log.INSTANCE;
	private static int counter = 0;

	private static class TimerProperties extends LipcPropertyAdapter {
		public int getIntProperty(String property) throws LipcException {
			if(property.equals("count")) {
				return counter;
			} else {
				return 0;
			}
		}
		public void setProperty(String property, int val) throws LipcException {
			if (property.equals("set")) {
				counter = val;
			} else if (property.equals("add")) {
				counter += val;
			} else if(property.equals("reset")) {
				counter = 0;
			}
		}
	}

	public static int getCounter() {
		return counter;
	}

	public static void addCounter(int count) {
		counter += count;
	}

	public static void addBookletCounter() {
		try {
			LipcSource source = LipcService.getInstance().createSource(timersource);
			LipcPropertyProvider timerproperty = new TimerProperties();
			source.exportIntProperty("count", timerproperty, 1);
			source.exportIntProperty("add", timerproperty, 2);
			source.exportIntProperty("set", timerproperty, 2);
			source.exportIntProperty("reset", timerproperty, 2);

		} catch(LipcException e) {
			logger.println("E: " + e.toString());
			e.printStackTrace(logger);
		}
	}
}

/**
 * A booklet for launching koreader directly from Kindle home screen.
 *
 * Modified by chrox@github.
 * Modified by cytown@github
 *
 * @author Patric Mueller &lt;bhaak@gmx.net&gt;
 */
public class KPVBooklet extends ReaderBooklet {

	private final String BOOKLET_CONFIG = "/mnt/us/extensions/kpvbooklet/bin/booklet.ini";
	private final String HACKUP_READER = "/mnt/us/hackedupreader/bin/cr3";
	private final String HACKUP_READER_HISTORY = "/mnt/us/hackedupreader/.settings/cr3hist.bmk";
	private final String KOREADER = "/mnt/us/koreader/koreader.sh";
	private final String KOR_HISTORY = "/mnt/us/koreader/history/";
	private final String KPDFVIEW = "/mnt/us/kindlepdfviewer/kpdf.sh";
	private final String KPV_HISTORY = "/mnt/us/kindlepdfviewer/history/";
	private final String GANDALF = "/var/local/mkk/gandalf";
	private final String SU = "/var/local/mkk/su";
	private static String PRIVILEGE_HINT_PREFIX = "?";

	private Process readerProcess;
	private String history_dir;
	private CCAdapter ccrequest = CCAdapter.INSTANCE;
	private static final PrintStream LOGGER = Log.INSTANCE;
	private boolean isHackupExist;

	public KPVBooklet() {
		// Check current privileges...
		String currentUsername = System.getProperty("user.name");
		if ("root".equals(currentUsername)) {
			PRIVILEGE_HINT_PREFIX = "#";
		} else {
			if (new File(GANDALF).exists()) {
				PRIVILEGE_HINT_PREFIX = "$";
			} else {
				PRIVILEGE_HINT_PREFIX = "%";
			}
		}
		log("I: KPVBooklet");
		isHackupExist = new File(HACKUP_READER).exists();

		BookletTimer.addBookletCounter();
	}

	private String getOpenType(String type) {
		Properties prop = new Properties();
		InputStream input = null;
		String openType = "1";

		try {

			input = new FileInputStream(BOOKLET_CONFIG);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			openType = (prop.getProperty(type, "1"));
		} catch (Exception ex) {
			log("W:" + ex.toString());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					log("W:" + e.toString());
				}
			}
		}
		return openType;
	}

	public void start(URI contentURI) {
		log("I: start()");
		log("I: kpvbooklet launching times " + BookletTimer.getCounter());
		log("I: contentURI " + contentURI.toString());
		String path = contentURI.getPath();
		int dot = path.lastIndexOf('.');
		String ext = (dot == -1) ? "" : path.substring(dot+1).toLowerCase();
		String openType = getOpenType(ext);
		if (openType.equals("0")) {
			log("I: Opening " + path + " with native reader...");
			super.start(contentURI);
			return;
		}
		String reader = KOREADER;
		if (isHackupExist && openType.equals("2")) {
			reader = HACKUP_READER;
		}
		log("I: Opening " + path + " with " + reader + "...");
		String[] cmd;
		if ("$".equals(PRIVILEGE_HINT_PREFIX)) {
			log("I: Call Gandalf for help...");
			cmd = new String[] {SU, "-s", "/bin/ash", "-c", reader + " \"" + path + "\""};
		} else {
			cmd = new String[] {"/bin/sh", reader, path};
		}
		try {
			readerProcess = Runtime.getRuntime().exec(cmd);
			history_dir = KOR_HISTORY;
		} catch (IOException e) {
			readerProcess = null;
			log("W: koreader not found, will check legacy kindlepdfviewer instead.");
		}
		// fallback to kpdf.sh if we cannot exec koreader.sh
		if (readerProcess == null && !openType.equals("2")) {
			log("I: Opening " + path + " with kindlepdfviewer...");
			cmd[cmd.length - 2] = KPDFVIEW;
			history_dir = KPV_HISTORY;
			try {
				readerProcess = Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
				log("E: " + e.toString());
			}
		}

		Thread thread = new ReaderWaitThread(history_dir, path, openType);
		thread.start();
	}

	public void stop() {
		log("I: stop()");
		log("I: add kpvbooklet launching times");
		BookletTimer.addCounter(1);
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
		private String openType = "";

		public ReaderWaitThread(String dir, String path, String type) {
			history_dir = dir;
			content_path = path;
			openType = type;
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
				float last_percent = 0f;
				if (openType.equals("2")) {
					last_percent = extractPercentFinished2(content_path);
				} else {
					last_percent = extractPercentFinished(history_dir, content_path);
				}
				log("I: update percent: " + last_percent);
				if (last_percent >= 0f) {
					ccrequest.updateCC(content_path, last_percent);
				}
			} catch (Exception e) {
				log("E: " + e.toString());
			}

			// send backward lipc event after reader exits
			try {
				Runtime.getRuntime().exec("lipc-set-prop com.lab126.appmgrd backward 0");
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
		int lastSlash = path.lastIndexOf('/');
		String parentname = (lastSlash == -1) ? "" : path.substring(0, lastSlash+1);
		String filename = (lastSlash == -1) ? "" : path.substring(lastSlash+1);
		int lastDot = filename.lastIndexOf('.');
		String basename = (lastDot == -1) ? "" : filename.substring(0, lastDot);
		String extname = (lastDot == -1) ? "" : filename.substring(lastDot+1);
		String histroyPath = history_dir + "[" + parentname.replace('/','#') + "] " + filename + ".lua";
		String metadataPath = parentname + basename + ".sdr/metadata." + extname + ".lua";
		File settingsFile = new File(metadataPath);
		if (!settingsFile.exists()) {
			settingsFile = new File(histroyPath);
		}

		log("I: found document settings file: " + settingsFile.getPath());
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(settingsFile.getPath())));
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

	/**
	 * Extract last_percent in document history file
	 * @param file path
	 */
	private float extractPercentFinished2(String path) {
		float percent_finished = -1.0f;
		int lastSlash = path.lastIndexOf('/');
		String parentname = (lastSlash == -1) ? "" : path.substring(0, lastSlash+1);
		String filename = (lastSlash == -1) ? "" : path.substring(lastSlash+1);
		File settingsFile = new File(HACKUP_READER_HISTORY);
		if (!settingsFile.exists()) {
			return -1f;
		}

		log("I: find hackedupreader settings for: " + path);
		FileInputStream stream = null;
		try {
			KXmlParser parser = new KXmlParser();
			parser.setFeature(KXmlParser.FEATURE_PROCESS_NAMESPACES, true);
			stream = new FileInputStream(settingsFile);
			// skip it because the file header has <feff>
			stream.skip(3);
			parser.setInput(stream, "UTF-8");
			String currName = null;
			String currPath = null;
			while(parser.next() != KXmlParser.END_DOCUMENT){
				if (parser.getEventType() == KXmlParser.START_TAG) {

					String n = parser.getName();
					// log("name: " + n);
					if ("file".equals(n)) {
						currName = null;
						currPath = null;
					}
					if ("doc-filename".equals(n)) {
						currName = parser.nextText();
						log("filename === " + currName);
					}
					if ("doc-filepath".equals(n)) {
						currPath = parser.nextText();
						log("path === " + currPath);
					}
					if ("bookmark".equals(n) && filename.equals(currName) && parentname.equals(currPath)) {
						String type = parser.getAttributeValue(null, "type");
						if ("lastpos".equals(type)) {
							String percent = parser.getAttributeValue(null, "percent");
							percent = percent.substring(0, percent.length() - 1);
							float fff = Float.parseFloat(percent);
							log("found === " + type + " : " + percent + " : " + fff);
							percent_finished = fff;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("E: " + e.toString());
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {}
			}
		}
		return percent_finished;
	}

	private void log(String msg) {
		LOGGER.println(msg);
	}
}
