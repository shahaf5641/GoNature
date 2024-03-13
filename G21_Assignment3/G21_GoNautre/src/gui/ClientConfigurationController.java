package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GoNatureFinals;
import client.ClientController;
import client.ClientUI;
import alerts.CustomAlerts;

/**
 * This Class is the GUI controller of ClientConfiguration.fxml
 * It handles all the JavaFx nodes events.
 * 
 * In this screen we setup the client configuration to the server
 *
 */
public class ClientConfigurationController implements Initializable {

    @FXML
    private Label headerLabel;

    @FXML
    private Button connectButton;

    @FXML
    private Button startAppBtn;

    @FXML
    private Circle circleStatus;

    @FXML
    private Label ipLabel;

    @FXML
    private Label portLabel;

    private Stage stage;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }

	private void init() {
		/* When the user press close(X) */
		getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if (ClientUI.chat != null)
					ClientUI.chat.getClient().quit();
			}
		});

	}

    @FXML
    private void connectButtonClick(ActionEvent event) {
        if (!ipLabel.getText().isEmpty() && !portLabel.getText().isEmpty()) {
            try {
                ClientUI.chat = new ClientController(ipLabel.getText(), Integer.parseInt(portLabel.getText()));
                circleStatus.setFill(Color.GREEN);
            } catch (NumberFormatException e) {
                circleStatus.setFill(Color.RED);
                e.printStackTrace();
            } catch (IOException e) {
                circleStatus.setFill(Color.RED);
                e.printStackTrace();
            }
        } else {
            new CustomAlerts(AlertType.ERROR, "Input Error", "Bad Input", "Please fill all the fields first").showAndWait();
        }
    }

    @FXML
    private void loadApp(ActionEvent event) {
        if (ClientUI.chat != null) {
            try {
                Stage thisStage = getStage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainScreen.fxml"));
                MainScreenController controller = new MainScreenController();
                loader.setController(controller);
                Stage newStage = new Stage();
                controller.setStage(newStage);
                loader.load();
                Parent p = loader.getRoot();
                newStage.setTitle("GoNature System");
                newStage.setScene(new Scene(p));
                newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
                newStage.setResizable(false);
                newStage.show();
                thisStage.close();
            } catch (Exception e) {
                System.out.println("failed to load form");
                e.printStackTrace();
            }
        } else {
            new CustomAlerts(AlertType.ERROR, "Error Loading App", "No Server Connection",
                    "Please connect first to the server before loading the app").showAndWait();
        }
    }

    private Stage getStage() {
        return stage;
    }

    /**
     * Setter for the class variable stage
     * 
     * @param stage The screen's stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
