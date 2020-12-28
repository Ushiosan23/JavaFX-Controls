package com.github.ushiosan23.javafx.utils;

import com.github.ushiosan23.javafx.system.TrayIconFX;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * AWT image utilities
 */
public final class AWTImageUtils {

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * This class cannot be instantiated
	 */
	private AWTImageUtils() {
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Scales the given image to a custom resolution.
	 *
	 * @param image     Target image to transform
	 * @param newWidth  New image width
	 * @param newHeight New image height
	 * @param filter    Target image filter
	 * @return {@link Image} Image with changes applied
	 */
	public static Image scaleImage(Image image, int newWidth, int newHeight, int filter) {
		return image.getScaledInstance(newWidth, newHeight, filter);
	}

	/**
	 * Scales the given image to a custom resolution (this method applied a smooth filter)
	 *
	 * @param image     Target image to transform
	 * @param newWidth  New image width
	 * @param newHeight New image height
	 * @return {@link Image} Image with changes applied
	 */
	public static Image scaleImage(Image image, int newWidth, int newHeight) {
		return scaleImage(image, newWidth, newHeight, Image.SCALE_SMOOTH);
	}

	/**
	 * Scales the given image to a resolution supported by the tray system
	 *
	 * @param image Target image to transform
	 * @return {@link Image} Image with changes applied
	 */
	public static Image scaleImageToTray(Image image) {
		if (!TrayIconFX.isPlatformSupport())
			throw new UnsupportedOperationException("The system tray is not supported on the current platform.");

		Dimension trayDimension = SystemTray.getSystemTray().getTrayIconSize();
		return scaleImage(image, trayDimension.width, trayDimension.height);
	}

	/**
	 * Get image buffered image
	 *
	 * @param image Target image to transform
	 * @return {@link BufferedImage} Editable image object
	 */
	public static BufferedImage getBufferedImage(Image image) {
		// Check if image is a buffered image instance
		if (image instanceof BufferedImage)
			return (BufferedImage) image;

		// Create buffered image
		// This image accept opacity
		BufferedImage resultImage = new BufferedImage(
			image.getWidth(null),
			image.getHeight(null),
			BufferedImage.TYPE_INT_ARGB
		);

		// Create graphics
		Graphics2D g2 = resultImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return resultImage;
	}

}
