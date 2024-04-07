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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GoNatureConstants;
import client.ClientController;
import client.ClientUI;
import alerts.Alerts;

/**
 * Controller class for the client configuration window.
 * This class is responsible for allowing the user to connect to the server by providing the server's IP address and port number.
 * 
 * @author [Your Name]
 * @version 1.0
 */
public class ClientSettingsController implements Initializable {
    
    @FXML
    private Button ButtonStart;

    @FXML
    private Label headerLabel;

    @FXML
    private Button connect;

    @FXML
    private TextField IpTextField;

    @FXML
    private TextField portTextField;

    private Stage stage;

    /**
     * Initializes the controller after its root element has been completely processed.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }

    /**
     * Initializes the controller's behavior.
     */
    private void init() {
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (ClientUI.chat != null)
                    ClientUI.chat.getClient().quit();
            }
        });
    }

    /**
     * Handles the click event on the connect button.
     * Attempts to establish a connection to the server using the provided IP address and port number.
     * Displays an error message if the input is invalid.
     * 
     * @param event The event representing the mouse click.
     */
    @FXML
    private void ClickToConnect(MouseEvent event) {
        if (!IpTextField.getText().isEmpty() && !portTextField.getText().isEmpty()) {
            try {
                ClientUI.chat = new ClientController(IpTextField.getText(), Integer.parseInt(portTextField.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alerts(AlertType.ERROR, "Input Error", "Bad Input", "Fill all the fields, please.").showAndWait();
        }
    }

    /**
     * Retrieves the stage associated with this controller.
     * 
     * @return The stage associated with this controller.
     */
    private Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage associated with this controller.
     * 
     * @param stage The stage to be associated with this controller.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Loads the main application after successful connection to the server.
     * 
     * @param event The event representing the mouse click.
     */
    @FXML
    private void loadApp(MouseEvent event) {
        if (ClientUI.chat != null) {
            try {
                Stage thisStage = getStage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/HomeScreen.fxml"));
                
                HomeScreenController controller = new HomeScreenController();
                loader.setController(controller);
                Stage newStage = new Stage();
                controller.setStage(newStage);
                loader.load();
                Parent p = loader.getRoot();
                newStage.setTitle("GoNature HomeScreen");
                newStage.setScene(new Scene(p));
                newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
                newStage.setResizable(false);
                newStage.show();
                thisStage.close();
            } catch (Exception e) {
                System.out.println("Failed to load form");
                e.printStackTrace();
            }
        } else {
            new Alerts(AlertType.ERROR, "Error Loading App", "No Server Connection", "Prioritize connecting to the server before launching the application.").showAndWait();
        }
    }
}
