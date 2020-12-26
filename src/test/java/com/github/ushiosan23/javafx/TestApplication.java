package com.github.ushiosan23.javafx;

import com.github.ushiosan23.javafx.controls.card.PictureCard;
import com.github.ushiosan23.javafx.dialogs.ExceptionAlert;
import com.github.ushiosan23.javafx.notifications.Notification;
import com.github.ushiosan23.javafx.system.TrayIconFX;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;

public class TestApplication extends Application {

	Image image;
	Stage primary;
	PictureCard card;

	TrayIconFX trayIconFX = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		image = new Image("file:///C:/Users/ushio/OneDrive/Pictures/Saved Pictures/Avatars/947692.png");
		primary = primaryStage;

		if (TrayIconFX.isPlatformSupport()) {
			trayIconFX = new TrayIconFX(image, "Tooltip");
			trayIconFX.attachToSystem();

			trayIconFX.addActionListener(e -> primaryStage.show());
		}

		try {
			configurePicture();

			VBox box = new VBox();
			box.getChildren().add(card);

			Scene scene = new Scene(box, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue) primaryStage.hide();
			});

			// Force error
			showNotification();
			throw new Exception();
		} catch (Exception e) {
			showExceptionDialog(e);
		}
	}

	private void configurePicture() {
		card = new PictureCard(image);
		card.setFitWidth(100);
		card.setTooltip("Hello, World!");
	}

	private void showExceptionDialog(Throwable throwable) {
		ExceptionAlert alert = new ExceptionAlert(throwable);
		alert.showAndWait();
	}

	private void showNotification() {
		Notification notification = new Notification("Title", "Description", image);
		notification.showNotify(primary);
	}

	@Test
	public void main() {
		launch();
	}

}
