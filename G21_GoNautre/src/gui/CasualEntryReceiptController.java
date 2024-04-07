package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Order;
import Controllers.ParkControl;

/**
 * Controller class for the casual entry receipt window.
 * This class is responsible for displaying information about a recent order for casual entry to a park.
 * 
 * 
 */
public class CasualEntryReceiptController implements Initializable {
    
    @FXML
    private Label LabeltotalPrice;

    @FXML
    private Button Buttonfinish;

    @FXML
    private Label summaryPark;

    @FXML
    private Label summaryTime;

    @FXML
    private Label summaryDate;

    @FXML
    private Label summaryType;

    @FXML
    private Label summaryVisitors;

    private Order order;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up necessary event handlers and initializes order information.
     * 
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SetOrderInformation();
        Buttonfinish.setOnAction(event -> closeStage());
    }

    /**
     * Sets the information of the recent order to be displayed on the receipt.
     */
    public void SetOrderInformation() {
        if (order != null) {
            summaryPark.setText(ParkControl.findParkName(order.getParkId() + ""));
            summaryDate.setText(order.getOrderDate());
            summaryTime.setText(order.getOrderTime());
            summaryType.setText(order.getOrderType());
            summaryVisitors.setText(order.getNumberOfParticipants() + "");
            LabeltotalPrice.setText(order.getTotalPrice() + "₪");
        }
    }

    /**
     * Closes the stage associated with this controller.
     */
    public void closeStage() {
        Stage stage = (Stage) Buttonfinish.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the order whose information will be displayed on the receipt.
     * 
     * @param recentOrder The recent order to be displayed.
     */
    public void setOrder(Order recentOrder) {
        this.order = recentOrder;
    }

    /**
     * Gets the stage associated with this controller.
     * 
     * @return The stage associated with this controller.
     */
    public Stage getStage() {
        return (Stage) LabeltotalPrice.getScene().getWindow();
    }
}

