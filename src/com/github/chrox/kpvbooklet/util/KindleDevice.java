package com.github.chrox.kpvbooklet.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class KindleDevice {
	private static final String PRETTY_VERSION_FILENAME = "/etc/prettyversion.txt";
	public static final String VERSION = getVersion();
	
	private static String getVersion() {
		BufferedReader reader = null;
		String version = "";
		try {
			reader = new BufferedReader(new FileReader(PRETTY_VERSION_FILENAME));
			String line = reader.readLine();
			if (line != null) {
				// It's a pity that cvm does not provide a split method on String class.
				int start = line.indexOf(' ') + 1;
				int end = line.indexOf(' ', start);
				version = line.substring(start, end);
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {};
			}
		}
		return version;
	}
}