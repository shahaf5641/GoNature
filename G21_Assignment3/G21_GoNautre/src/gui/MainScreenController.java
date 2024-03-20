
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GoNatureFinals;

/**
 * This Class is the GUI controller of MainScreen.fxml
 * It handles all the JavaFx nodes events.
 * 
 * This is the main screen of GoNature system
 *
 */
public class MainScreenController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private AnchorPane rootAnchorPane;

	@FXML
	private ImageView firstParkImage;

	@FXML
	private ImageView secondParkImage;

	@FXML
	private ImageView thirdParkImage;

	@FXML
	private ImageView goNatureLogo;

	@FXML
	private Label menuLabel;

	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}

	private void init() {
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				ClientUI.chat.getClient().quit();
			}
		});

	}

	private Stage getStage() {
		return stage;
	}

	/**
	 * Setter for the class variable mainScreenStage
	 * 
	 * @param stage The current stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}