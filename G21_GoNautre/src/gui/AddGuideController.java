/**
 * This class represents the controller for adding a guide in the GUI.
 * It implements the Initializable interface to initialize the GUI components.
 */
package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import alerts.Alerts;
import Controllers.VerificationControl;
import Controllers.TravelerControl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddGuideController implements Initializable {

    @FXML
    private AnchorPane registerRootPane;

    @FXML
    private Label headerLabel;

    @FXML
    private TextField emailInputRegister;

    @FXML
    private TextField fullNameInputRegister;

    @FXML
    private TextField phoneNumberInputRegister;

    @FXML
    private TextField idInputRegister;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Accordion accordion;

    @FXML
    private Label requiredFieldsLabel1;

    @FXML
    private javafx.scene.control.Button AddAcountBTN;

    @FXML
    private Label requiredFieldsLabel11;

    /**
     * Initializes the controller.
     * 
     * @param arg0 The URL of the FXML file to initialize.
     * @param arg1 The ResourceBundle object containing localized resources.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initTextFields();
    }

    /**
     * Initializes the text fields by adding listeners to them.
     */
    private void initTextFields() {
        idInputRegister.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                idInputRegister.setText(arg2.replaceAll("[^\\d]", ""));
            }
        });

        phoneNumberInputRegister.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                phoneNumberInputRegister.setText(arg2.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Handles the action when the Add Account button is clicked.
     */
    @FXML
    private void AddAcountBTN() {
        String fullName = fullNameInputRegister.getText();
        String[] temp = fullName.split(" ", 2);
        String firstName = temp[0];
        String lastName = temp.length == 1 ? "" : temp[1];
        String phoneNumber = phoneNumberInputRegister.getText();
        String id = idInputRegister.getText();
        String email = emailInputRegister.getText();

        /* if user did not fill all required fields */
        if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() || email.isEmpty()) {
            if (lastName.isEmpty() && (!(firstName.isEmpty() || id.isEmpty() || email.isEmpty())))
                new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please enter full name").showAndWait();
            else {
                new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill in all the fields")
                        .showAndWait();
            }
        }
        
        /* if guide tries to register again with same id */
        else if (TravelerControl.findGuide(id) != null)
            new Alerts(AlertType.ERROR, "Register Error", "Register Error", "You are already Registerd")
                    .showAndWait();
        else if (id.length() != 9) {
    		new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Id length must be 9").showAndWait();
        }
        else {
            // if user that wants to be guide is a traveller , we delete him from traveller table.
            if (VerificationControl.checkTravelerPresence(id))
                TravelerControl.travelerDeleteFromDB(id);
            
            TravelerControl.addGuideToDB(id, firstName, lastName, email, phoneNumber);
            new Alerts(AlertType.INFORMATION, "Success", "Guided Added",
                    "Guide was added successfully").showAndWait();

        }
    }
}
