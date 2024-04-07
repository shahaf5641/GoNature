package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.Park;
import Controllers.ParkControl;

/**
 * Controls the Park Parameters view, displaying various statistics and settings for parks.
 * It is intended for use by employees to view current visitor counts, maximum visitor capacities,
 * and the allowed visitor gap for either a specific park or any park they choose from a dropdown menu.
 */
public class ParkParamsController implements Initializable {

    @FXML
    private Label headerLabel;

    @FXML
    private Label currentvisitorsLabel;


    @FXML
    private Label allowedLabel;
    

    @FXML
    private Label maxVisitorsLabel;
    
    @FXML
    private ComboBox<String> parkComboBox;

    @FXML
    private Label actualLabel;

    @FXML
    private Label chooseParkLabel;

    /**
     * Initializes the controller class. Depending on the employee's associated park,
     * it either populates a combo box with parks to choose from or directly loads park parameters.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (EmployeeLoginController.member.getParkId() == 0) {
            initComboBoxs();
            chooseParkLabel.setVisible(true);
            parkComboBox.setVisible(true);
            parkComboBox.setDisable(false);
        } else {
            chooseParkLabel.setVisible(false);
            parkComboBox.setVisible(false);
            parkComboBox.setDisable(true);
            loadParams(ParkControl.findParkById(String.valueOf(EmployeeLoginController.member.getParkId())));
        }
    }

    /**
     * Initializes the park combo box with the names of all parks and sets up a listener
     * to load parameters for the selected park.
     */
    private void initComboBoxs() {
    
        ArrayList<String> parksNames = ParkControl.findParksNames();
        if (parksNames != null) {
            parkComboBox.getItems().addAll(parksNames);
        }

        parkComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                loadParams(ParkControl.findParkByName(newItem));
            }
        });

    }
    /**
     * Sets the labels to display park-specific parameters including current visitors,
     * maximum visitors, allowed visitor gap, and the actual capacity left.
     *
     * @param park The park whose parameters are to be displayed.
     */
    private void setLabels(Park park) {
        currentvisitorsLabel.setText(park.getCurrentVisitors() + "");
        maxVisitorsLabel.setText(park.getMaxVisitors() + "");
        allowedLabel.setText(park.getGapBetweenMaxAndCapacity() + "");
        int temp = park.getMaxVisitors() - park.getCurrentVisitors();
        temp = temp < 0 ? 0 : temp;
        actualLabel.setText(temp + "");
    }
    /**
     * Loads the park parameters into the labels on the GUI.
     *
     * @param park The park whose parameters are to be loaded.
     */
    private void loadParams(Park park) {
        if (park != null)
            setLabels(park);
    }

}
