package com.github.ushiosan23.javafx.system;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * System tray icon used to display icon in system bar.
 * This class add support to tray icon in JavaFX
 */
public class TrayIconFX {

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Native system tray icon
	 */
	private TrayIcon nativeTray;

	/**
	 * Default system tray instance
	 */
	private SystemTray defaultSystemTray;

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create system tray icon
	 *
	 * @param image   Target image to display
	 * @param tooltip Tooltip message (when tray icon is hovered)
	 * @param menu    Tray menu
	 */
	public TrayIconFX(@NotNull Image image, @Nullable String tooltip, @Nullable PopupMenu menu) {
		// Check platform support
		if (!isPlatformSupport())
			throw new UnsupportedOperationException("The system tray is not supported on the current platform.");
		// Don't close application if last window is closed
		Platform.setImplicitExit(false);
		// Configure tray image
		java.awt.Image trayImg = convertImage(image);
		trayImg = trayImageScaled(trayImg);
		// Configure properties
		defaultSystemTray = SystemTray.getSystemTray();
		nativeTray = new TrayIcon(trayImg);
		// Configure tray icon
		if (tooltip != null)
			nativeTray.setToolTip(tooltip);
		if (menu != null)
			nativeTray.setPopupMenu(menu);
	}

	/**
	 * Create system tray icon
	 *
	 * @param image   Target image to display
	 * @param tooltip Tooltip message (when tray icon is hovered)
	 */
	public TrayIconFX(Image image, String tooltip) {
		this(image, tooltip, null);
	}

	/**
	 * Create system tray icon
	 *
	 * @param image Target image to display
	 */
	public TrayIconFX(Image image) {
		this(image, null, null);
	}

	/**
	 * Add popup menu action listener
	 *
	 * @param listener Target object listener
	 */
	public void addPopupItemSelectionListener(ActionListener listener) {
		PopupMenu popupMenu = nativeTray.getPopupMenu();
		// Check if popup is null
		if (popupMenu == null) return;
		// Attach event
		popupMenu.addActionListener(listener);
	}

	/**
	 * Remove popup menu action listener
	 *
	 * @param listener Target object listener
	 */
	public void removePopupItemSelectionListener(ActionListener listener) {
		PopupMenu popupMenu = nativeTray.getPopupMenu();
		// Check if popup is null
		if (popupMenu == null) return;
		// Remove listener
		popupMenu.removeActionListener(listener);
	}

	/**
	 * Set tray image
	 *
	 * @param image Javafx image
	 */
	public void setImage(Image image) {
		java.awt.Image awtImg = convertImage(image);
		awtImg = trayImageScaled(awtImg);
		nativeTray.setImage(awtImg);
	}

	/**
	 * Get tray image
	 *
	 * @return {@link Image} Target javafx image
	 */
	public Image getImage() {
		java.awt.Image awtImg = nativeTray.getImage();

		return SwingFXUtils.toFXImage(getBufferedImage(awtImg), null);
	}

	/**
	 * Set tray popup menu
	 *
	 * @param popup Target popup menu
	 */
	public void setPopupMenu(PopupMenu popup) {
		nativeTray.setPopupMenu(popup);
	}

	/**
	 * Get current popup menu
	 *
	 * @return Current popup menu or {@code null} if not exists
	 */
	@Nullable
	public PopupMenu getPopupMenu() {
		return nativeTray.getPopupMenu();
	}

	/**
	 * Set tray tooltip
	 *
	 * @param tooltip Target message tooltip
	 */
	public void setToolTip(String tooltip) {
		nativeTray.setToolTip(tooltip);
	}

	/**
	 * Get tooltip
	 *
	 * @return {@link String} tooltip message or {@code null} if not exists
	 */
	@Nullable
	public String getToolTip() {
		return nativeTray.getToolTip();
	}

	/**
	 * Add mouse listener
	 *
	 * @param listener Target object listener
	 */
	public void addMouseListener(MouseListener listener) {
		nativeTray.addMouseListener(listener);
	}

	/**
	 * Remove mouse listener
	 *
	 * @param listener Target object listener
	 */
	public void removeMouseListener(MouseListener listener) {
		nativeTray.removeMouseListener(listener);
	}

	/**
	 * Remove mouse motion listener
	 *
	 * @param listener Target object listener
	 */
	public void removeMouseMotionListener(MouseMotionListener listener) {
		nativeTray.removeMouseMotionListener(listener);
	}

	/**
	 * Add action mouse listener
	 *
	 * @param listener Target object listener
	 */
	public void addActionListener(ActionListener listener) {
		nativeTray.addActionListener(listener);
	}

	/**
	 * Remove action mouse listener
	 *
	 * @param listener Target object listener
	 */
	public void removeActionListener(ActionListener listener) {
		nativeTray.removeActionListener(listener);
	}

	/**
	 * Display system messages.
	 *
	 * @param caption     Message title
	 * @param text        Message body
	 * @param messageType Message type
	 */
	public void displayMessage(String caption, String text, TrayIcon.MessageType messageType) {
		nativeTray.displayMessage(caption, text, messageType);
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Attach tray icon to system
	 */
	public void attachToSystem() {
		// Attach tray to system tray (In Java Swing Thread)
		runLater(() -> {
			try {
				defaultSystemTray.add(nativeTray);
			} catch (AWTException ignored) {
			}
		});
	}

	/**
	 * Remove tray icon from system
	 */
	public void detachToSystem() {
		defaultSystemTray.remove(nativeTray);
	}

	/* ---------------------------------------------------------
	 *
	 * Static methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Check if current platform, supports system tray
	 *
	 * @return {@link Boolean} support result
	 */
	public static boolean isPlatformSupport() {
		return SystemTray.isSupported();
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Convert javafx image to awt image
	 *
	 * @param image Target image to convert
	 * @return {@link java.awt.Image} Converted image
	 */
	private static java.awt.Image convertImage(Image image) {
		return SwingFXUtils.fromFXImage(image, null);
	}

	/**
	 * Get scaled image to use in system tray
	 *
	 * @param image Target image to scale
	 * @return {@link java.awt.Image} Scaled image
	 */
	private static java.awt.Image trayImageScaled(java.awt.Image image) {
		return image.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
	}

	/**
	 * Get buffered image instance
	 *
	 * @param image Target image to transform
	 * @return {@link BufferedImage} instance
	 */
	private static BufferedImage getBufferedImage(java.awt.Image image) {
		if (image instanceof BufferedImage)
			return (BufferedImage) image;

		BufferedImage resultImage = new BufferedImage(
			image.getWidth(null),
			image.getHeight(null),
			BufferedImage.TYPE_INT_ARGB
		);

		Graphics2D graphics2D = resultImage.createGraphics();
		graphics2D.drawImage(image, 0, 0, null);
		graphics2D.dispose();

		return resultImage;
	}

	/**
	 * Run in swing thread
	 *
	 * @param runnable Target action to run
	 */
	private void runLater(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}

}
