package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import alerts.CustomAlerts;
import Controllers.AutenticationControl;
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

/**
 * window for adding new subscriber to subscriber table gets information for new subscriber
 * handles with pop up windows of errors in case form is not filled right in case new subscriber's ID is in traveler table, we delete him from there
 */
public class AddSubscriberController implements Initializable {

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initTextFields();
    }

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
                new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please enter full name").showAndWait();
            else {
                new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill in all the fields")
                        .showAndWait();
            }
        }
        
        /* if subscriber tries to subscribe again with same id */
        else if (TravelerControl.getSubscriber(id) != null)
            new CustomAlerts(AlertType.ERROR, "Register Error", "Register Error", "You are already Registerd")
                    .showAndWait();
        else {
            // if user that wants to be subscriber is a traveller , we delete him from traveller table
            if (AutenticationControl.isTravelerExist(id))
                TravelerControl.deleteFromTravelerTable(id);
            
            TravelerControl.insertSubscriberToSubscriberTable(id, firstName, lastName, email, phoneNumber);
            new CustomAlerts(AlertType.INFORMATION, "Subscriber Added", "Subscriber Added",
                    "Subscriber was added successfully").showAndWait();

        }


    }
}


