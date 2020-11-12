package com.github.ushiosan23.javafx.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception utilities.
 */
public final class ExceptionUtils {

	/* ---------------------------------------------------------
	 *
	 * Constructor
	 *
	 * --------------------------------------------------------- */

	/**
	 * Private constructor
	 */
	private ExceptionUtils() {
	}

	/* ---------------------------------------------------------
	 *
	 * Public methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get exception stacktrace string
	 *
	 * @param throwable Target exception to get data
	 * @return {@link String} with all information
	 */
	public static String getStackTraceString(Throwable throwable) {
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);

		throwable.printStackTrace(printWriter);
		String stacktraceString = writer.toString();

		// Close resources
		try {
			writer.close();
			printWriter.close();
		} catch (IOException ignored) {
		}

		return stacktraceString;
	}


	/* ---------------------------------------------------------
	 *
	 * Internal properties
	 *
	 * --------------------------------------------------------- */


}
