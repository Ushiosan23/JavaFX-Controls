package com.github.ushiosan23.javafx.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public final class ExceptionDialog extends Alert {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Target exception to show
	 */
	private final Throwable exception;

	/**
	 * Pane information container
	 */
	private final BorderPane containerPane = new BorderPane();

	/**
	 * Label information before show error
	 */
	private final Label labelInfo = new Label("The exception stacktrace was:");

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create exception with custom throwable
	 *
	 * @param throwable Target exception
	 */
	public ExceptionDialog(Throwable throwable) {
		super(AlertType.ERROR);
		exception = throwable;

		setTitle("Exception Dialog");
		if (exception.getMessage() != null) setHeaderText(exception.getMessage());
	}


}
