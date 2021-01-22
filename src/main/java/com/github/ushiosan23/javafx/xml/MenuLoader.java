package com.github.ushiosan23.javafx.xml;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.net.URL;
import java.util.Objects;

/**
 * Class to create menu structure from xml files
 */
public final class MenuLoader {

	/* ---------------------------------------------------------
	 *
	 * Properties
	 *
	 * --------------------------------------------------------- */

	/**
	 * XML item node name
	 */
	private static final String ITEM_NAME = "item";

	/**
	 * XML menu node name
	 */
	private static final String MENU_NAME = "menu";

	/**
	 * XML separator node name
	 */
	private static final String SEPARATOR_NAME = "separator";

	/* ---------------------------------------------------------
	 *
	 * Constructors
	 *
	 * --------------------------------------------------------- */

	/**
	 * This class cannot be instantiated
	 */
	private MenuLoader() {
	}

	/* ---------------------------------------------------------
	 *
	 * Methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Load document and create menu
	 *
	 * @param document Target xml document
	 * @return {@link Menu} Menu result
	 */
	public static Menu load(@NotNull Document document) {
		Element root = document.getDocumentElement();

		if (!root.getNodeName().equals("menu-root"))
			return new Menu();

		return createMenu(root);
	}

	/**
	 * Create context menu from XML document
	 *
	 * @param document Target xml document
	 * @return {@link ContextMenu} Context menu result
	 */
	public static ContextMenu loadContextMenu(@NotNull Document document) {
		Element root = document.getDocumentElement();
		ContextMenu resultMenu = new ContextMenu();

		if (!root.getNodeName().equals("menu-context"))
			return resultMenu;

		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node current = nodeList.item(i);

			if (current.getNodeName().equals(MENU_NAME)) resultMenu.getItems().add(createMenu(current));
			if (current.getNodeName().equals(ITEM_NAME)) resultMenu.getItems().add(createItem(current));
			if (current.getNodeName().equals(SEPARATOR_NAME)) resultMenu.getItems().add(new SeparatorMenuItem());
		}

		return resultMenu;
	}

	/**
	 * Create menu bar from XML document
	 *
	 * @param document Target xml document
	 * @return {@link ContextMenu} Context menu result
	 */
	public static MenuBar loadMenuBar(@NotNull Document document) {
		Element root = document.getDocumentElement();
		MenuBar resultMenu = new MenuBar();

		if (!root.getNodeName().equals("menu-bar"))
			return resultMenu;

		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node current = nodeList.item(i);

			if (current.getNodeName().equals(MENU_NAME)) resultMenu.getMenus().add(createMenu(current));
		}

		return resultMenu;
	}

	/* ---------------------------------------------------------
	 *
	 * Internal methods
	 *
	 * --------------------------------------------------------- */

	/**
	 * Create menu item from xml node element
	 *
	 * @param node Target node structure
	 * @return {@link MenuItem} Menu item result
	 */
	private static MenuItem createItem(@NotNull Node node) {
		Element nodeEl = (Element) node;
		String nodeText = nodeEl.getAttribute("text");
		MenuItem resultItem = new MenuItem(nodeText);
		boolean resizeIcon = true;

		if (nodeEl.hasAttribute("id"))
			resultItem.setId(nodeEl.getAttribute("id"));
		if (nodeEl.hasAttribute("disabled"))
			resultItem.setDisable(parseText(nodeEl.getAttribute("disabled")));
		if (nodeEl.hasAttribute("resize-icon"))
			resizeIcon = parseText(nodeEl.getAttribute("resize-icon"));
		if (nodeEl.hasAttribute("icon")) {
			ImageView imageView = loadImageView(nodeEl.getAttribute("icon"), resizeIcon);
			if (imageView != null) resultItem.setGraphic(imageView);
		}

		return resultItem;
	}

	/**
	 * Create menu structure from node
	 *
	 * @param node Target node
	 * @return {@link Menu} Menu result
	 */
	private static Menu createMenu(@NotNull Node node) {
		Element nodeEl = (Element) node;
		String mName = nodeEl.getAttribute("text");
		Menu resultMenu = new Menu(mName);
		boolean resizeIcon = true;

		if (nodeEl.hasAttribute("id"))
			resultMenu.setId(nodeEl.getAttribute("id"));
		if (nodeEl.hasAttribute("disabled"))
			resultMenu.setDisable(parseText(nodeEl.getAttribute("disabled")));
		if (nodeEl.hasAttribute("resize-icon"))
			resizeIcon = parseText(nodeEl.getAttribute("resize-icon"));
		if (nodeEl.hasAttribute("icon")) {
			ImageView imageView = loadImageView(nodeEl.getAttribute("icon"), resizeIcon);
			if (imageView != null) resultMenu.setGraphic(imageView);
		}

		NodeList children = nodeEl.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node current = children.item(i);

			if (current.getNodeName().equals(MENU_NAME)) resultMenu.getItems().add(createMenu(current));
			if (current.getNodeName().equals(ITEM_NAME)) resultMenu.getItems().add(createItem(current));
			if (current.getNodeName().equals(SEPARATOR_NAME)) resultMenu.getItems().add(new SeparatorMenuItem());
		}

		return resultMenu;
	}

	/**
	 * Parse text to boolean
	 *
	 * @param text Text to parse
	 * @return Parsed result
	 */
	private static boolean parseText(@NotNull String text) {
		return text.trim().equalsIgnoreCase("true");
	}

	/**
	 * Load image from location
	 *
	 * @param location Target image location
	 * @param resize   Resize image
	 * @return {@link ImageView} image view result or {@code null} if image not exists
	 */
	@Nullable
	private static ImageView loadImageView(@NotNull String location, boolean resize) {
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			ImageView imageView = new ImageView();
			boolean isLocal;
			if (location.startsWith("@")) {
				isLocal = true;
				location = location.substring(1);
			} else {
				isLocal = false;
				location = location.replace(" ", "%20");
			}

			URI loadURI = URI.create(isLocal ?
				Objects.requireNonNull(classLoader.getResource(location)).toExternalForm() :
				location
			);
			URL locationURL = loadURI.toURL();
			Image image = resize ?
				new Image(locationURL.openStream(), 15, 15, true, true) :
				new Image(locationURL.openStream());

			imageView.setImage(image);
			return imageView;
		} catch (Exception err) {
			err.printStackTrace();
		}

		return null;
	}

}
