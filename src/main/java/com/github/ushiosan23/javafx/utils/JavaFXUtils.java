package com.github.ushiosan23.javafx.utils;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities for javafx elements
 */
public final class JavaFXUtils {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */


	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Private constructor
	 */
	private JavaFXUtils() {
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get dialog window object
	 *
	 * @param dialog Target dialog to check
	 * @return {@link Stage dialog} or {@code null} if this method called after dialog initialization.
	 */
	@Nullable
	public static Stage getDialogStage(Dialog<?> dialog) {
		// Save scene dialog
		Scene dialogScene = getDialogScene(dialog);
		// Prevent NullPointerException if scene not exists
		if (dialogScene == null) return null;
		// Get Window dialog
		return (Stage) dialogScene.getWindow();
	}

	/**
	 * Get dialog scene.
	 *
	 * @param dialog Target dialog to search.
	 * @return {@link Scene} dialog scene or {@code null} if dialog not initialize scene.
	 */
	@Nullable
	public static Scene getDialogScene(Dialog<?> dialog) {
		return dialog.getDialogPane().getScene();
	}

}
