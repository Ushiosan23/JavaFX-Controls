package com.github.ushiosan23.javafx.utils;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Image utilities.
 * This class is used to modify create or edit images more easy.
 */
public final class ImageUtils {

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * This class cannot be instantiated
	 */
	private ImageUtils() {
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create node snapshot.
	 *
	 * @param target Target node to take snapshot.
	 * @return {@link WritableImage} instance result.
	 */
	public static WritableImage getSnapShot(Node target) {
		return target.snapshot(createSnapshotParams(), null);
	}

	/**
	 * Create snapshot of scene. The trick is take snapshot to root scene node.
	 *
	 * @param target Target scene to take snapshot.
	 * @return {@link WritableImage} instance result.
	 */
	public static WritableImage getSnapShot(Scene target) {
		return getSnapShot(target.getRoot());
	}

	/**
	 * Create snapshot parameters.
	 *
	 * @return {@link SnapshotParameters} Configuration to take a snapshot.
	 */
	private static SnapshotParameters createSnapshotParams() {
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);

		return parameters;
	}

}
