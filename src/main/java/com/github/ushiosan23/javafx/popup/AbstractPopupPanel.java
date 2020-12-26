package com.github.ushiosan23.javafx.popup;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This panel is a special panel to display some information in top of content
 */
public abstract class AbstractPopupPanel extends Pane {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Default panel background
	 */
	private static final Color DEFAULT_COLOR = new Color(0, 0, 0, 0.4);

	/**
	 * Default background fill
	 */
	private static final BackgroundFill DEFAULT_BACKGROUND_FILL = new BackgroundFill(DEFAULT_COLOR, null, null);

	/**
	 * Default background
	 */
	private static final Background DEFAULT_BACKGROUND = new Background(DEFAULT_BACKGROUND_FILL);

	/**
	 * Panel property background
	 */
	private ObjectProperty<Background> backgroundPanelProperty;

	/**
	 * Content panel
	 */
	private final BorderPane contentPane;

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Primary constructor
	 */
	public AbstractPopupPanel() {
		super();
		// Initialize properties
		contentPane = new BorderPane();
		// Call methods
		initializeContent(contentPane);
		configurePanel();
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Display panel in scene
	 *
	 * @param stage Target stage
	 */
	public final void show(Stage stage) {
		// Get root node
		Node parent = stage.getScene().getRoot();
		// Check if parent is valid node
		if (parent instanceof Pane) {
			configurePanelBeforeShow((Pane) parent);
			((Pane) parent).getChildren().add(this);
		} else {
			throw new IllegalArgumentException("Stage has not valid group node.");
		}
	}

	/**
	 * Display panel in scene
	 *
	 * @param node Target node children
	 */
	public final void show(Node node) {
		show((Stage) node.getScene().getWindow());
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Initialize panel content
	 *
	 * @param container Target container panel
	 */
	protected abstract void initializeContent(BorderPane container);

	/**
	 * Configure panel styles
	 */
	protected void configurePanel() {
		// Bind background
		backgroundProperty().bind(getPanelColorProperty());
		// Add container
		getChildren().add(contentPane);
	}

	/**
	 * Configure panel before show
	 *
	 * @param node Target parent node
	 */
	protected void configurePanelBeforeShow(Pane node) {
		// Set pref width
		prefWidthProperty().bind(node.prefWidthProperty());
		prefHeightProperty().bind(node.prefHeightProperty());
	}

	/* ---------------------------------------------------------
	 *
	 * JavaFX Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get panel background property
	 *
	 * @return {@link ObjectProperty} Property object
	 */
	public ObjectProperty<Background> getPanelColorProperty() {
		if (backgroundPanelProperty == null)
			backgroundPanelProperty = new SimpleObjectProperty<>(this, "panelColor", DEFAULT_BACKGROUND);

		return backgroundPanelProperty;
	}

	/**
	 * Get panel background
	 *
	 * @return {@link Background} panel result
	 */
	public Background getBackgroundPanelProperty() {
		return getPanelColorProperty().get();
	}

	/**
	 * Change panel background
	 *
	 * @param background Target background
	 */
	public void setBackgroundPanelProperty(Background background) {
		getPanelColorProperty().set(background);
	}

}
