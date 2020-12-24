package com.github.ushiosan23.javafx.dialogs;

import com.github.ushiosan23.javafx.utils.JavaFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Base alert type class.
 */
public abstract class BaseAlert extends Alert {

	/* ---------------------------------------------------------
	 *
	 * Internal properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Alert stage window
	 */
	protected Stage alertStage = null;

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create simple alert.
	 *
	 * @param alertType Alert type.
	 */
	public BaseAlert(AlertType alertType) {
		super(alertType);
		setBasicConfiguration();
	}

	/**
	 * Create custom alert.
	 *
	 * @param alertType   Alert type.
	 * @param contentText Alert content description.
	 * @param buttons     Alert action buttons.
	 */
	public BaseAlert(AlertType alertType, String contentText, ButtonType... buttons) {
		super(alertType, contentText, buttons);
		setBasicConfiguration();
	}

	/* ---------------------------------------------------------
	 *
	 * Custom methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Add action to alert dialog.
	 *
	 * @param action Button action to add.
	 * @return {@link BaseAlert} current instance.
	 */
	public BaseAlert addAction(ButtonType action) {
		getButtonTypes().add(action);
		return this;
	}

	/**
	 * Add action to alert dialog.
	 *
	 * @param name Button text action.
	 * @return {@link BaseAlert} current instance.
	 */
	public BaseAlert addAction(String name) {
		return addAction(new ButtonType(name));
	}

	/**
	 * Get alert stage.
	 *
	 * @return {@link Stage} alert window.
	 */
	public Stage getAlertStage() {
		return alertStage;
	}

	/**
	 * Get alert internal scene.
	 *
	 * @return Get internal {@link Scene} or {@code null} if scene is not initialize.
	 */
	@Nullable
	public Scene getAlertScene() {
		return JavaFXUtils.getDialogScene(this);
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Set basic alert configuration.
	 */
	protected void setBasicConfiguration() {
		// Initialize properties
		alertStage = JavaFXUtils.getDialogStage(this);
		// Apply configurations
		clearDefaultActions();
		// Set events
		if (alertStage != null) alertStage.setOnCloseRequest(this::onWindowCloseRequest);
	}

	/**
	 * Clear default alert actions.
	 */
	protected void clearDefaultActions() {
		// Check if has actions in alert
		if (getButtonTypes().size() == 0) return;
		// Clear all
		getButtonTypes().clear();
	}

	/* ---------------------------------------------------------
	 *
	 * Events
	 *
	 * --------------------------------------------------------- */

	/**
	 * Called when click in window titlebar close button.
	 *
	 * @param windowEvent Source window event.
	 */
	private void onWindowCloseRequest(WindowEvent windowEvent) {
		alertStage.close();
		windowEvent.consume();
	}

}
