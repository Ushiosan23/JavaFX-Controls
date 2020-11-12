package com.github.ushiosan23.javafx.utils;

import java.awt.*;

public final class ScreenUtils {

	/**
	 * Private constructor
	 */
	private ScreenUtils() {
	}

	/**
	 * Save native toolkit
	 */
	private static final Toolkit toolkit = Toolkit.getDefaultToolkit();

	/**
	 * Get screen insets
	 *
	 * @return {@link Insets} Get screen insets
	 */
	public static Insets getInsets() {
		return toolkit.getScreenInsets(
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
		);
	}

}
