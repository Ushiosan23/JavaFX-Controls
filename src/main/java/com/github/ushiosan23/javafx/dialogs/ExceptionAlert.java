package com.github.ushiosan23.javafx.dialogs;

import com.github.ushiosan23.javafx.utils.ExceptionUtils;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

/**
 * Display window modal dialog with exception information.
 * This dialog can be used to display application errors or capture error to send to github (create a issue).
 */
public final class ExceptionAlert extends BaseAlert {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Target exception to show.
	 */
	private final Throwable exception;

	/**
	 * Pane information container.
	 */
	private final GridPane containerPane = new GridPane();

	/**
	 * Label information before show error.
	 */
	private final Label labelInfo = new Label("The exception stacktrace was:");

	/**
	 * TextArea information. This node contains all exception info.
	 */
	private final TextArea textAreaInfo = new TextArea();

	/* ---------------------------------------------------------
	 *
	 * Custom button types
	 *
	 * --------------------------------------------------------- */

	/**
	 * Action to send information.
	 */
	public static ButtonType SEND = new ButtonType("Send", ButtonBar.ButtonData.YES);

	/**
	 * Cancel action.
	 */
	public static ButtonType CANCEL = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create exception with custom throwable.
	 *
	 * @param throwable Target exception.
	 */
	public ExceptionAlert(@NotNull Throwable throwable) {
		super(AlertType.ERROR);
		exception = throwable;
		// Simple configuration
		setTitle("Exception Dialog");
		if (exception.getMessage() != null) setHeaderText(exception.getMessage());
		// More configurations
		configureDialog();
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Configure dialog elements.
	 */
	private void configureDialog() {
		// Add actions
		addAction(SEND);
		addAction(CANCEL);

		// Create fonts
		Font messageFont = Font.font("Arial", 16);

		// Configure label info
		labelInfo.setFont(messageFont);

		// Text area configuration
		textAreaInfo.setText(ExceptionUtils.getStackTraceString(exception));
		textAreaInfo.setEditable(false);
		textAreaInfo.setWrapText(false);

		textAreaInfo.setMaxWidth(Double.MAX_VALUE);
		textAreaInfo.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textAreaInfo, Priority.ALWAYS);
		GridPane.setHgrow(textAreaInfo, Priority.ALWAYS);

		// Configure gridPane
		containerPane.setMaxWidth(Double.MAX_VALUE);
		containerPane.add(labelInfo, 0, 0);
		containerPane.add(textAreaInfo, 0, 1);

		// Configure dialog
		getDialogPane().setExpandableContent(containerPane);
	}

}
