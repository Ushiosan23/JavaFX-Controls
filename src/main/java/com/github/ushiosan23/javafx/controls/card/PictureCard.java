package com.github.ushiosan23.javafx.controls.card;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Create image view like a card.
 * This card can be attach events or set shadows and borders.
 */
public class PictureCard extends ImageView {

	/* ---------------------------------------------------------
	 *
	 * Constants
	 *
	 * --------------------------------------------------------- */

	private static final String DEFAULT_CLASS = "picture-card";

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Picture shape clip.
	 */
	protected ObjectProperty<Shape> pictureShape;

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * Empty picture card.
	 */
	public PictureCard() {
		super();
		initializeConfig();
	}

	/**
	 * Picture with image.
	 *
	 * @param image Target image to set.
	 */
	public PictureCard(Image image) {
		super(image);
		initializeConfig();
	}

	/**
	 * Picture url.
	 *
	 * @param url Target file url.
	 */
	public PictureCard(String url) {
		super(url);
		initializeConfig();
	}

	/* ---------------------------------------------------------
	 *
	 * JavaFX properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * Get Observable shape value.
	 *
	 * @return {@link ObjectProperty} shape object.
	 */
	public ObjectProperty<Shape> getPictureShapeProperty() {
		if (pictureShape == null) {
			pictureShape = new ObjectPropertyBase<>() {
				@Override
				public Object getBean() {
					return PictureCard.this;
				}

				@Override
				public String getName() {
					return "pictureCard";
				}
			};
		}

		return pictureShape;
	}

	/**
	 * Get picture shape value.
	 *
	 * @return {@link Shape} picture shape.
	 */
	public Shape getPictureShape() {
		return getPictureShapeProperty().getValue();
	}

	/**
	 * Set picture shape.
	 *
	 * @param shape {@link Shape} clip.
	 */
	public void setPictureShape(Shape shape) {
		getPictureShapeProperty().set(shape);
	}

	/**
	 * Set card tooltip
	 *
	 * @param tooltip Target tooltip.
	 */
	public void setTooltip(Tooltip tooltip) {
		ObservableMap<Object, Object> properties = getProperties();

		if (properties.containsKey(Tooltip.class.getName())) {
			Tooltip.uninstall(this, (Tooltip) properties.get(Tooltip.class.getName()));
		}

		Tooltip.install(this, tooltip);
	}

	/**
	 * Set tooltip node.
	 *
	 * @param message Target message.
	 */
	public void setTooltip(String message) {
		setTooltip(new Tooltip(message));
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	private void initializeConfig() {
		// Set events
		getPictureShapeProperty().addListener(this::onPictureShapeChanged);
		fitWidthProperty().addListener(this::onPictureSizeChanged);
		fitHeightProperty().addListener(this::onPictureSizeChanged);
		// Clear style class
		if (getStyleClass().size() != 0) getStyleClass().clear();
		// Set style class
		getStyleClass().add(DEFAULT_CLASS);
		// Set ratio and image shape
		Circle localShape = new Circle();
		setPictureShape(localShape);
		setPreserveRatio(true);
		// Set picture drop shadow
		setEffect(new DropShadow());
	}

	/**
	 * Set configuration shape.
	 *
	 * @param shape {@link Shape} Target shape to clip.
	 */
	private void configureShape(Shape shape) {
		double pictureWidth = getFitWidth();
		double pictureHeight = getFitWidth();

		if (shape instanceof Circle) {
			((Circle) shape).setRadius(pictureWidth / 2);
			((Circle) shape).setCenterX(pictureWidth / 2);
			((Circle) shape).setCenterY(pictureHeight / 2);
		} else if (shape instanceof Ellipse) {
			((Ellipse) shape).setRadiusX(pictureWidth / 2);
			((Ellipse) shape).setRadiusY(pictureWidth / 2);
			((Ellipse) shape).setCenterX(pictureWidth / 2);
			((Ellipse) shape).setCenterY(pictureHeight / 2);
		} else if (shape instanceof Rectangle) {
			((Rectangle) shape).setWidth(pictureWidth);
			((Rectangle) shape).setHeight(pictureWidth);
		}
	}


	/* ---------------------------------------------------------
	 *
	 * Events
	 *
	 * --------------------------------------------------------- */

	/**
	 * Called when shape is changed.
	 *
	 * @param observable Object observed.
	 * @param oldVal     Last object value.
	 * @param newVal     Current value.
	 */
	private void onPictureShapeChanged(ObservableValue<? extends Shape> observable, Shape oldVal, Shape newVal) {
		if (newVal != null) configureShape(newVal);
		setClip(newVal);
	}

	/**
	 * Called when picture size is changed.
	 *
	 * @param observable Object observed.
	 * @param oldVal     Last object value.
	 * @param newVal     Current value.
	 */
	private void onPictureSizeChanged(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
		if (getPictureShape() != null) configureShape(getPictureShape());
	}

}
