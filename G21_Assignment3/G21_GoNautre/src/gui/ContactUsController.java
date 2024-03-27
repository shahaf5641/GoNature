package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Messages;
import Controllers.NotificationControl;
import alerts.CustomAlerts;
import javafx.scene.control.Alert.AlertType;

public class ContactUsController implements Initializable {
    @FXML
    private AnchorPane ourConcactsPane;

    @FXML
    private AnchorPane emailPane;

    @FXML
    private TextArea textArea;

    @FXML
    private Button sendButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField subjectLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void sendEmailBtn() {
        if (isValidInput()) {
            CustomAlerts alert = new CustomAlerts(AlertType.INFORMATION, "Email Sent", "Email Sent",
                    "Thank you for reaching out.\n" + "We will be in touch as soon as possible.");
            alert.showAndWait();
            getStage().close();
        }
    }

    private boolean isValidInput() {
        if (emailTextField.getText().isEmpty() || subjectLabel.getText().isEmpty() || textArea.getText().isEmpty()) {
            new CustomAlerts(AlertType.ERROR, "Bad Input", "Bad Input", "Email, Subject, and message are must!")
                    .showAndWait();
            return false;
        }
        return true;
    }

    private Stage getStage() {
        return (Stage) subjectLabel.getScene().getWindow();
    }
}
