package com.github.ushiosan23.javafx.dialogs;

import com.github.ushiosan23.javafx.utils.JavaFXUtils;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Base dialog behaviour class.
 *
 * @param <T> Dialog generic return type.
 */
public abstract class BaseDialog<T> extends Dialog<T> {

	/* ---------------------------------------------------------
	 *
	 * Internal properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Dialog window stage.
	 */
	protected Stage dialogStage = null;

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create basic dialog.
	 */
	public BaseDialog() {
		super();
		setBasicConfiguration();
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Set basic configuration dialog.
	 */
	protected void setBasicConfiguration() {
		// Initialize properties
		dialogStage = JavaFXUtils.getDialogStage(this);
		// Set events
		if (dialogStage != null) dialogStage.setOnCloseRequest(this::onWindowCloseRequest);
	}

	/* ---------------------------------------------------------
	 *
	 * Events
	 *
	 * --------------------------------------------------------- */

	/**
	 * Called when click in window titlebar close button.
	 *
	 * @param event Source window event.
	 */
	private void onWindowCloseRequest(WindowEvent event) {
		dialogStage.close();
		event.consume();
	}

}
