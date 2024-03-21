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

import Controllers.RequestControl;
import alerts.CustomAlerts;

public class UpdateParametersController implements Initializable {

    @FXML
    private AnchorPane updateParametersRootPane;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane maxVisitorsTP;

    @FXML
    private AnchorPane identificationAP;

    @FXML
    private TextField newMaxVisitorsTextField;

    @FXML
    private TitledPane estimatedTimeTP;

    @FXML
    private AnchorPane informationAP;

    @FXML
    private TextField newEsitimatedTIme;

    @FXML
    private TitledPane gapTP;

    @FXML
    private AnchorPane paymentAP;

    @FXML
    private TextField gapTextField;

    @FXML
    private TitledPane discountTP;

    @FXML
    private AnchorPane discountAP;

    @FXML
    private TextField discountPercentage;

    @FXML
    private TextField discountStartDateTextField;

    @FXML
    private TextField discountEndDateTextField;

    @FXML
    private Button sendForApprovealButton;

    @FXML
    private void sendForApprovealButtonClicked() {

        ArrayList<String> arrayOfTextRequests = new ArrayList<>();
        arrayOfTextRequests.add(newMaxVisitorsTextField.getText());
        arrayOfTextRequests.add(newEsitimatedTIme.getText());
        arrayOfTextRequests.add(gapTextField.getText());
        Integer parkID = MemberLoginController.member.getParkId();
        String parkIdString = parkID.toString(); // Convert Integer to String
        arrayOfTextRequests.add(parkIdString);
        RequestControl.addNewRequest(arrayOfTextRequests);
        System.out.println("2");
        new CustomAlerts(AlertType.INFORMATION, "Sent", "Sent", "New requests were sent to Department Manager")
                .showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        accordion.setExpandedPane(maxVisitorsTP);
        

        initTextFields();
        
    }

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
}
