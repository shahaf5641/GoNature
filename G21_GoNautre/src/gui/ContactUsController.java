package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Message;
import Controllers.CommunicationControl;
import alerts.Alerts;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class for the Contact Us GUI.
 */
public class ContactUsController implements Initializable {
    
    /** AnchorPane for the Contact Us view. */
    @FXML
    private AnchorPane ConcactUsPane;
    
    /** TextField for the subject of the email. */
    @FXML
    private TextField subjectLabel;
    
    /** TextArea for entering the message content. */
    @FXML
    private TextArea textArea;
    
    /** Label for displaying the email address. */
    @FXML
    private Label emailLabel;
    
    /** TextField for entering the email address. */
    @FXML
    private TextField emailTF;
    
    /** AnchorPane for the email section. */
    @FXML
    private AnchorPane emailPane;
    
    /** TextField for entering the phone number. */
    @FXML
    private TextField phoneTF;
    
    /** Button for sending the email. */
    @FXML
    private Button send;
    
    /** TextField for entering the name. */
    @FXML
    private TextField nameTextField; 

    /**
     * Initializes the controller.
     * 
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code goes here
    }

    /**
     * Handles the action when the send email button is clicked.
     */
    @FXML
    private void sendEmailBtn() {
        if (checkInput()) {
            Alerts alert = new Alerts(AlertType.INFORMATION, "Email Sent", "Email Sent",
                    "Thanks for getting in touch. We'll contact you as soon as we can.");
            alert.showAndWait();
            getStage().close();
        }
    }
    
    /**
     * Retrieves the stage.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return (Stage) subjectLabel.getScene().getWindow();
    }

    /**
     * Validates the input fields.
     * 
     * @return True if the input is valid, otherwise false.
     */
    private boolean checkInput() {
        if (emailTF.getText().isEmpty() || subjectLabel.getText().isEmpty() || textArea.getText().isEmpty()) {
            new Alerts(AlertType.ERROR, "Bad Input", "Bad Input", "Email, subject, and message are required fields.")
                    .showAndWait();
            return false;
        }
        return true;
    }
}