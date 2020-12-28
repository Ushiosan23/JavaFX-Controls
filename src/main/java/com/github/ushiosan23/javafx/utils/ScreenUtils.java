package com.github.ushiosan23.javafx.utils;

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	/**
	 * Get work area size
	 *
	 * @param screen Target screen to check
	 * @return {@link Rectangle2D} Rectangle with all sizes
	 */
	@NotNull
	public static Rectangle2D getScreenSize(@Nullable Screen screen) {
		if (screen == null) return getScreenSize(Screen.getPrimary());

		return screen.getVisualBounds();
	}

	/**
	 * Get percentage of screen size
	 *
	 * @param screen     Target screen to check
	 * @param percentage Percentage of dimension
	 * @return {@link Rectangle2D} Rectangle result
	 */
	public static Rectangle2D getPercentScreenSize(@Nullable Screen screen, @NotNull Dimension2D percentage) {
		Rectangle2D screenDimension = getScreenSize(screen);
		return new Rectangle2D(
			0,
			0,
			percentage.getWidth() * screenDimension.getWidth() / 100,
			percentage.getHeight() * screenDimension.getHeight() / 100
		);
	}

	/**
	 * Get percentage of screen size
	 *
	 * @param screen        Target screen to check
	 * @param percentWidth  Percentage of Width
	 * @param percentHeight Percentage of Height
	 * @return {@link Rectangle2D} Rectangle result
	 */
	public static Rectangle2D getPercentScreenSize(
		@Nullable Screen screen,
		double percentWidth,
		double percentHeight) {
		return getPercentScreenSize(screen, new Dimension2D(percentWidth, percentHeight));
	}

}
