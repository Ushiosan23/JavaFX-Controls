package com.github.ushiosan23.javafx;


import com.github.ushiosan23.javafx.xml.MenuLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLMenuUtilsTest extends Application {

	private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	private Document loadDocument(String location) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		return builder.parse(classLoader.getResourceAsStream(location));
	}

	private Menu loadMenu() throws ParserConfigurationException, IOException, SAXException {
		Document document = loadDocument("menus/ExampleMenu.xml");
		return MenuLoader.load(document);
	}

	private MenuBar loadMenuBar() throws ParserConfigurationException, IOException, SAXException {
		Document document = loadDocument("menus/ExampleMenuBar.xml");
		return MenuLoader.loadMenuBar(document);
	}

	private ContextMenu loadPopup() throws ParserConfigurationException, IOException, SAXException {
		Document document = loadDocument("menus/ExamplePopupMenu.xml");
		return MenuLoader.loadContextMenu(document);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();

		ContextMenu contextMenu = loadPopup();
		MenuBar menuBar = loadMenuBar();
		menuBar.getMenus().add(loadMenu());
		Button button = new Button("Context button");

		pane.setTop(menuBar);
		pane.setCenter(button);

		button.setContextMenu(contextMenu);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	@Test
	public void runTest() throws Exception {
		launch();
	}
}
