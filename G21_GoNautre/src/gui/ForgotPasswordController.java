package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import alerts.Alerts;
import javafx.scene.control.Alert.AlertType;
/**
 * Controls the password recovery process, allowing users to request password recovery by entering their ID.
 */

public class ForgotPasswordController implements Initializable {

    @FXML
    private Button recoverButton;

    @FXML
    private TextField idTextField;
    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     *
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
    /**
     * Handles the action of the "Recover Password" button click. Validates the user's input and initiates
     * the password recovery process. Displays messages according to the validation results.
     */
    @FXML
    private void recoverPasswordBtn() {

        String id = idTextField.getText();
        if (id.isEmpty()) {
            new Alerts(AlertType.ERROR, "Bad Input", "Bad Input", "Please enter your id.").showAndWait();
        } 
        else if (id.length() != 9) {
        		new Alerts(AlertType.ERROR, "Password Recovery", "Input Error", "The length of the ID must be nine characters.").showAndWait();
            }
        
        else
        {
         new Alerts(AlertType.INFORMATION, "Password Recovery", "Password Recovery",
                "Please check your email. We've sent your password to the provided email address.").showAndWait();
                getStage().close();
        }

    }
    /**
     * Retrieves the stage (window) in which this scene is displayed. This is used for closing the window after successful operation.
     *
     * @return The current Stage where this scene is displayed.
     */
    private Stage getStage() {
        return (Stage) recoverButton.getScene().getWindow();
    }

}
