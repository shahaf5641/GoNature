package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Order;
import logic.OrderTb;
import Controllers.ParkControl;

/**
 * This Class is the GUI controller of CasualVisitReceipt.fxml
 * It handles all the JavaFx nodes events.
 * 
 * This is the visit receipt for casual visits
 */
public class CasualVisitReceiptController implements Initializable {

    @FXML
    private Label summaryPark;

    @FXML
    private Label summaryDate;

    @FXML
    private Label summaryTime;

    @FXML
    private Label summaryType;

    @FXML
    private Label summaryVisitors;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button finishBtn;

    private Order order;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	System.out.println("300");
        setOrderInfo();
        System.out.println("301");
        // On Finish button click
        finishBtn.setOnAction(event -> closeStage());
        System.out.println("302");
    }

    public void setOrderInfo() {
    	System.out.println("400");
        if (order != null) {
        	System.out.println("401");

            summaryPark.setText(ParkControl.getParkName(order.getParkId() + ""));
            summaryDate.setText(order.getOrderDate());
            summaryTime.setText(order.getOrderTime());
            summaryType.setText(order.getOrderType());
            summaryVisitors.setText(order.getNumberOfParticipants() + "");
            totalPriceLabel.setText(order.getPrice() + "₪");
        	System.out.println("402");

        }
    }

    // Method to close the stage
    public void closeStage() {
        Stage stage = (Stage) finishBtn.getScene().getWindow();
    	
        stage.close();
    }

    /**
     * Getter for the current Stage
     * 
     * @return Current stage
     */
    public Stage getStage() {
    	System.out.println("500");

        return (Stage) totalPriceLabel.getScene().getWindow();
        
    }

    /**
     * Setter for the class variable order
     * 
     * @param recentOrder The last order the traveler did
     */
    public void setOrder(Order recentOrder) {
    	System.out.println("600");

        this.order = recentOrder;
    }
    
    
}
