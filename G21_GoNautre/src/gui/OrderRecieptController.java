
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ParkControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Order;
import logic.Traveler;
/**
 * Controller class for managing the order confirmation view.
 * This class handles displaying order information to the user,
 * including details such as traveler information, park details,
 * order date and time, payment method, and order status.
 */
public class OrderRecieptController implements Initializable {

    @FXML
    private Label headerLabel;

    @FXML
    private Label summaryIDLabel;
    
    @FXML
    private Label summaryFullNameLabel;
    @FXML
    private Label summaryParkLabel;

    @FXML
    private Label summaryDateLabel;

    @FXML
    private Label summaryEmailLabel;

    @FXML
    private Label summaryPhoneLabel;
 
    @FXML
    private Label summaryTimeLabel;

    @FXML
    private Label summaryType;

    @FXML
    private Label summaryVisitors;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button finishButton;

    @FXML
    private Label messageLine;

    @FXML
    private Label messageLine1;

    @FXML
    private Label summaryPayment;


    @FXML
    private Label orderStatusLabel;
	private Order order;
	private String paymentMethod;
	private Traveler traveler;
	private boolean isOrderFromWeb = false;
	private boolean isWaitingList = false;
	   /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SetOrderInformation();
	}
	 /**
     * Sets the order information to be displayed on the confirmation screen.
     * This includes traveler details, park information, order date and time, payment method, and order status.
     */
	public void SetOrderInformation() {
		if (isWaitingList) {
			headerLabel.setText("You Are In The Waiting List");
			messageLine.setText("If someone will cancel their visit we will let you know");
			
		}
		if (order != null && traveler != null) {
			summaryIDLabel.setText(order.getTravelerId());
			summaryFullNameLabel.setText(traveler.getFirstName() + " " + traveler.getLastName());
			summaryPhoneLabel.setText(traveler.getPhoneNumber());
			summaryEmailLabel.setText(traveler.getEmail());
			summaryParkLabel.setText(ParkControl.findParkName(order.getParkId() + ""));
			summaryDateLabel.setText(order.getOrderDate());
			summaryTimeLabel.setText(order.getOrderTime());
			summaryType.setText(order.getOrderType());
			summaryVisitors.setText(order.getNumberOfParticipants() + "");
			summaryPayment.setText(paymentMethod);
			orderStatusLabel.setText(order.getOrderStatus());
			totalPriceLabel.setText(order.getTotalPrice() + "₪");
		}

		
	}
	/**
     * Closes the current stage.
     * This method is called when the user clicks the finish button to close the order confirmation window.
     */

	@FXML
	private void closeStage() {
		getStage().close();
	}
	 /**
     * Retrieves the stage associated with the current scene.
     *
     * @return The stage of the current scene.
     */
	private Stage getStage() {
		return (Stage) totalPriceLabel.getScene().getWindow();
	}
	/**
     * Sets the recent order for which the confirmation is being displayed.
     *
     * @param recentOrder The recent order to be displayed.
     */
	public void setOrder(Order recentOrder) {
		this.order = recentOrder;
	}

	  /**
     * Sets the payment method for the order confirmation.
     *
     * @param paymentMethod The payment method for the order.
     */
	public void setSummaryPayment(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	/**
     * Sets whether the order confirmation is for an order placed from the web.
     *
     * @param isOrderFromWeb True if the order is from the web, false otherwise.
     */
	
	public void setOrderFromWeb(boolean isOrderFromWeb) {
		this.isOrderFromWeb = isOrderFromWeb;
	}
	 /**
     * Sets whether the order confirmation is for an order placed on the waiting list.
     *
     * @param isWaitingList True if the order is on the waiting list, false otherwise.
     */
	public void setWaitingList(boolean isWaitingList) {
		this.isWaitingList = isWaitingList;
	}
	
	   /**
     * Sets the traveler associated with the order confirmation.
     *
     * @param traveler The traveler associated with the order.
     */
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	
	

}
