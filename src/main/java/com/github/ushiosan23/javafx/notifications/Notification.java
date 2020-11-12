package com.github.ushiosan23.javafx.notifications;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

/**
 * Notification control.
 * This control create a notification control to display in screen like notification.
 * Unfortunately is not possible to show notification when owner window is hide.
 */
public class Notification extends Popup implements INotification {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Notification container
	 */
	protected BorderPane notificationContainer = new BorderPane();

	/**
	 * Information container. This node contains title and description nodes.
	 */
	protected VBox informationContainer = new VBox();

	/**
	 * Container icon
	 */
	protected VBox notificationIconContainer = new VBox();

	/**
	 * Notification icon node
	 */
	protected Node notificationIcon = null;

	/**
	 * Notification title label
	 */
	protected Label titleLabel = new Label();

	/**
	 * Notification description label
	 */
	protected Label descriptionLabel = new Label();

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create notification with all elements
	 *
	 * @param title       Notification title
	 * @param description Notification description
	 * @param icon        Notification icon
	 */
	public Notification(
		@Nullable CharSequence title,
		@Nullable CharSequence description,
		@Nullable Image icon) {
		super();
		setNotificationTitle(title);
		setDescription(description);
		setIcon(icon);
		initializeComponents();
	}

	/**
	 * Create notification with all elements
	 *
	 * @param title       Notification title
	 * @param description Notification description
	 * @param icon        Notification node icon
	 */
	public Notification(
		@Nullable CharSequence title,
		@Nullable CharSequence description,
		@Nullable Node icon) {
		this(title, description, (Image) null);
		setIcon(icon);
	}

	/**
	 * Create notification with information
	 *
	 * @param title       Notification title
	 * @param description Notification description
	 */
	public Notification(
		@Nullable CharSequence title,
		@Nullable CharSequence description) {
		this(title, description, (Image) null);
	}

	/* ---------------------------------------------------------
	 *
	 * Implemented methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Set notification title
	 *
	 * @param title Target title to show
	 */
	@Override
	public void setNotificationTitle(@Nullable CharSequence title) {
		if (title == null) {
			titleLabel.setText("");
		} else {
			titleLabel.setText(title.toString());
		}
	}

	/**
	 * Get notification title
	 *
	 * @return {@link String} notification title
	 */
	@Override
	public String getNotificationTitle() {
		return titleLabel.getText();
	}

	/**
	 * Set notification description
	 *
	 * @param description Description message
	 */
	@Override
	public void setDescription(@Nullable CharSequence description) {
		if (description == null)
			descriptionLabel.setText("");
		else
			descriptionLabel.setText(description.toString());
	}

	/**
	 * Get notification description
	 *
	 * @return {@link String} notification description
	 */
	@Override
	public String getDescription() {
		return descriptionLabel.getText();
	}

	/**
	 * Set icon to notification.
	 *
	 * @param icon {@link Image} icon to show
	 */
	@Override
	public void setIcon(@Nullable Image icon) {
		// Check icon object
		if (icon == null) return;

		ImageView imageView = new ImageView(icon);
		imageView.setFitHeight(80);
		imageView.setPreserveRatio(true);

		setIcon(imageView);
	}

	/**
	 * Set notification node icon
	 *
	 * @param icon {@link Node} icon
	 */
	@Override
	public void setIcon(@Nullable Node icon) {
		// Check if node is not valid
		if (icon == null) return;
		// Remove left element
		if (notificationIconContainer.getChildren().size() != 0)
			notificationIconContainer.getChildren().clear();
		// Set icon
		notificationIcon = icon;
		notificationIconContainer.getChildren().add(notificationIcon);
	}

	/**
	 * Get notification node icon
	 *
	 * @return {@link Node} icon result
	 */
	@Nullable
	@Override
	public Node getIcon() {
		return notificationIcon;
	}

	/**
	 * Get notification container
	 *
	 * @return {@link Pane} container
	 */
	@Override
	public Pane getNotificationContent() {
		return notificationContainer;
	}

	/**
	 * Display notification in screen.
	 * You only can show this window if application is already started.
	 */
	@Override
	public void showNotify(Stage owner) {
		Screen current = Screen.getPrimary();
		Rectangle2D bounds = current.getVisualBounds();

		super.show(owner, bounds.getWidth(), bounds.getHeight());

		double width = (bounds.getWidth() - getWidth()) - 10;
		double height = (bounds.getHeight() - getHeight()) - 10;

		setX(width);
		setY(height);
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Configure all nodes
	 */
	protected void initializeComponents() {
		/* Create fonts for information nodes */
		Font titleFont = Font.font("Arial", FontWeight.BOLD, 18);
		Font descriptionFont = Font.font("Arial", 14);
		/* Configure information nodes */
		configureTitle(titleFont);
		configureDescription(descriptionFont);
		configureIconContainer();
		configureWindow();
		/* set scene */

		getContent().add(notificationContainer);
	}

	/**
	 * Configure popup window
	 */
	protected void configureWindow() {
		/* set styles */
		getScene().setFill(Color.WHITE);
		setAutoHide(false);
		/* set notification nodes */
		notificationContainer.setLeft(notificationIconContainer);
		notificationContainer.setCenter(informationContainer);
		/* set events */
		notificationContainer.setOnMouseClicked(this::onNotificationClick);
	}

	/**
	 * Configure notification title
	 *
	 * @param font Target font
	 */
	private void configureTitle(Font font) {
		titleLabel.setFont(font);
		titleLabel.setTextAlignment(TextAlignment.LEFT);
		titleLabel.setPadding(new Insets(10, 10, 5, 20));
		titleLabel.setMinWidth(250);
		titleLabel.setMaxWidth(250);

		informationContainer.getChildren().add(titleLabel);
	}

	/**
	 * Configure notification description
	 *
	 * @param font Target font
	 */
	private void configureDescription(Font font) {
		descriptionLabel.setFont(font);
		descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
		descriptionLabel.setPadding(new Insets(5, 10, 10, 20));

		informationContainer.getChildren().add(descriptionLabel);
	}

	/**
	 * Configure icon container
	 */
	private void configureIconContainer() {
		notificationIconContainer.setAlignment(Pos.CENTER);
		notificationIconContainer.setPadding(new Insets(5));
	}

	/* ---------------------------------------------------------
	 *
	 * Events
	 *
	 * --------------------------------------------------------- */

	/**
	 * Called when notification is clicked
	 *
	 * @param event Source event
	 */
	private void onNotificationClick(MouseEvent event) {
		hide();
		event.consume();
	}

}
