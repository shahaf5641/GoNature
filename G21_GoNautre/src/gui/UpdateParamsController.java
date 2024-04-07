package gui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import Controllers.ManagementRequestControl;
import alerts.Alerts;

/**
 * Controller for the Update Parameters page.
 * This class handles the UI logic for updating park parameters and sending these updates for approval.
 */

public class UpdateParamsController implements Initializable {
	@FXML
    private TitledPane maxVisitorsTitledPane;

    @FXML
    private AnchorPane updateParametersRootPane;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane estimatedTimeTitledPane;

    @FXML
    private AnchorPane identificationAnchorPane;

    @FXML
    private TextField newMaxVisitorsTextField;

    @FXML
    private AnchorPane informationAnchorPane;

 
    @FXML
    private TitledPane gapTitledPane;
    @FXML
    private Button sendForApprovealButton;

    @FXML
    private AnchorPane paymentAnchorPane;

    @FXML
    private TextField gapTextField;

    @FXML
    private TitledPane discountTitledPane;

    @FXML
    private AnchorPane discountAnchorPane;

    @FXML
    private TextField discountPercentageTextField;

    @FXML
    private TextField discountStartDateTextField;

    @FXML
    private TextField discountEndDateTextField;
    @FXML
    private TextField newEsitimatedTIme;

    /**
     * Initializes the controller class. Sets up the initial state of UI components.
     */
    
    @Override
    public void initialize(URL firstarg, ResourceBundle secondarg) {
        accordion.setExpandedPane(maxVisitorsTitledPane);
        initTextFields();
        
    }
    /**
     * Initializes text fields with validation logic to ensure correct input format.
     */
    private void initTextFields() {
        gapTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                gapTextField.setText(arg2.replaceAll("[^\\d]", ""));
            }
        });
        
        newMaxVisitorsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                newMaxVisitorsTextField.setText(arg2.replaceAll("[^\\d]", ""));
            }
        });
    }
    /**
     * Handles the action of sending park parameter update requests for approval.
     * Collects input from text fields and forwards them as a request to the Department Manager.
     */
    @FXML
    private void sendForApprovealButtonClicked() {

        ArrayList<String> arrayOfTextRequests = new ArrayList<>();
        arrayOfTextRequests.add(newMaxVisitorsTextField.getText());
        arrayOfTextRequests.add(newEsitimatedTIme.getText());
        arrayOfTextRequests.add(gapTextField.getText());
        Integer parkID = EmployeeLoginController.member.getParkId();
        String parkIdString = parkID.toString(); 
        arrayOfTextRequests.add(parkIdString);
        ManagementRequestControl.insertRequest(arrayOfTextRequests);
        if (arrayOfTextRequests.get(0).isEmpty() && 
        	    arrayOfTextRequests.get(1).isEmpty() && 
        	    arrayOfTextRequests.get(2).isEmpty()) {
        	    new Alerts(AlertType.ERROR, "Error", "Not sent", "Please fill at least one field")
        	            .showAndWait();
        	}

        else
        {
            new Alerts(AlertType.INFORMATION, "Sent", "Sent", "New requests have been forwarded to the Department Manager.")
            .showAndWait();
        }

    }

}
