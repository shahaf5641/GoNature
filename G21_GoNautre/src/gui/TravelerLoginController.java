package gui;

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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Traveler;
import Controllers.VerificationControl;
import alerts.Alerts;
import client.ChatClient;

/**
 * Constructs a TravelerLoginController with a specified parent stage.
 * 
 * @param parentStage The parent stage of the login controller.
 */ 
public class TravelerLoginController implements Initializable {

    @FXML
    private AnchorPane loginContainer;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Label recoverPasswordLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private Button loginButton;
    public static Traveler traveler = null;
    private Stage parentStage;
    /**
     * Constructs a TravelerLoginController with a specified parent stage.
     * 
     * @param parentStage The parent stage of the login controller.
     */

    public TravelerLoginController(Stage parent) {
        this.parentStage = parent;
    }

    /**
     * Initializes the controller class. This method is automatically invoked
     * after the FXML file has been loaded. It sets up the initial configuration for the login form.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }
    /**
     * Initializes the login button with its action handler to process login requests.
     */
    private void init() {
        traveler = null;
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginButton();
            }
        });
    }
    /**
     * Processes the login action when the login button is clicked.
     * It checks if the ID field is filled and attempts to authenticate the traveler.
     * Based on the authentication result, it provides feedback to the user
     * or switches to the traveler screen upon successful login.
     */
    private void loginButton() {
        String id = idTextField.getText();
        
        if (id.isEmpty())
            new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill the ID field")
                    .showAndWait();
        else {
            int res = VerificationControl.travelerLoginById(id);
            if (res == 0) {
                traveler = (Traveler) ChatClient.responseFromServer.getSuccessSet().get(0);
                switchScene();
            } else if (res == 2)
                new Alerts(AlertType.ERROR, "Login Error", "Login Error",
                        "No orders have been placed with this ID yet.\n Please proceed to make an order before logging in.").showAndWait();
            else
                new Alerts(AlertType.ERROR, "Login Error", "Login Error", "You are already connected")
                        .showAndWait();
        }
    }
    
    /**
     * Retrieves the stage associated with this controller.
     * 
     * @return The current stage.
     */
    private Stage getStage() {
        return (Stage) loginButton.getScene().getWindow();
    }

    /**
     * Switches the scene to the traveler screen after a successful login.
     * It sets up a new stage for the traveler screen and closes the current login stage.
     */
    private void switchScene() {
        try {
            Stage thisStage = getStage();
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TravelerScreen.fxml"));
            TravelerScreenController controller = new TravelerScreenController();
            loader.setController(controller);
            controller.setStage(newStage);
            controller.setMainScreenStage(parentStage);
            loader.load();
            Parent p = loader.getRoot();
            newStage.setTitle("Traveler Screen");
            newStage.setScene(new Scene(p));
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setResizable(false);
            newStage.show();
            thisStage.close();
            parentStage.hide();
        } catch (Exception e) {
            System.out.println("faild to load form");
            e.printStackTrace();
        }
    }

    
}