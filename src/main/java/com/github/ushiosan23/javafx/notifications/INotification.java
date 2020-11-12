package com.github.ushiosan23.javafx.notifications;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Notification base structure
 */
public interface INotification {

	/**
	 * Set notification title
	 *
	 * @param title Target title to show
	 */
	void setNotificationTitle(CharSequence title);

	/**
	 * Get notification title
	 *
	 * @return Notification title
	 */
	String getNotificationTitle();

	/* ---------------------------------------------------------
	 *
	 * Description
	 *
	 * --------------------------------------------------------- */

	/**
	 * Set notification description message
	 *
	 * @param description Description message
	 */
	void setDescription(CharSequence description);

	/**
	 * Get notification description
	 *
	 * @return Notification description
	 */
	String getDescription();

	/* ---------------------------------------------------------
	 *
	 * Icon
	 *
	 * --------------------------------------------------------- */

	/**
	 * Set notification icon
	 *
	 * @param icon {@link Image} icon to show
	 */
	void setIcon(Image icon);

	/**
	 * Set icon from node
	 *
	 * @param icon {@link Node} icon
	 */
	void setIcon(Node icon);

	/**
	 * Get notification icon
	 *
	 * @return {@link Node} icon representation
	 */
	Node getIcon();

	/* ---------------------------------------------------------
	 *
	 * Notification node
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get notification node
	 *
	 * @return {@link Pane} notification container
	 */
	Pane getNotificationContent();

	/* ---------------------------------------------------------
	 *
	 * Actions
	 *
	 * --------------------------------------------------------- */

	/**
	 * Show popup window
	 *
	 * @param owner Target window owner
	 */
	void showNotify(Stage owner);

	/**
	 * Hide popup window
	 */
	void hide();

}
