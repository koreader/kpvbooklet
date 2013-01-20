package com.github.chrox.kpvbooklet.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public final class Log {
	
	private Log() {}
	public static final String LOGFILE = "/tmp/kpvbooklet.log";
	
	public static final PrintStream INSTANCE = instantiate();

	private static PrintStream instantiate() {
		try {
			return new PrintStream(new FileOutputStream(LOGFILE));
		} catch (IOException e) {
			return System.err;
		}
	}
}
