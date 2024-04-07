package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controllers.CommunicationControl;
import Controllers.OrderControl;
import Controllers.ParkControl;
import alerts.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import logic.Order;
import logic.OrderTable;
import logic.Park;
import logic.Traveler;
import resources.MsgTemplates;
import logic.OrderStatusName;


/**
 * Controller class for managing the orders view for a traveler. This class provides functionalities to view, confirm, and cancel orders.
 */
public class ShowTravelerOrders implements Initializable {

	ObservableList<OrderTable> ov = FXCollections.observableArrayList();
	@FXML
	private TableColumn<OrderTable, String> orderStatusTableColumn;
	@FXML
	private Label headerLabel;

	@FXML
	private TableView<OrderTable> ordersTableView;

	@FXML
	private TableColumn<OrderTable, Integer> orderIdTableColumn;

	@FXML
	private TableColumn<OrderTable, String> visitTimeTableColumn;

	@FXML
	private TableColumn<OrderTable, String> visitDateTableColumn;

	@FXML
	private Button confirmOrderButton;

	@FXML
	private Label orderIdLabel;
	@FXML
	private Button cancelOrderButton;

	@FXML
	private Label visitDateLabel;

	@FXML
	private Label visitTimeLabel;

	@FXML
	private Label orderStatusLabel;

	 /**
     * Initializes the controller class.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadTableView();
		confirmOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				confirmButton();
				clearLabals();
			}
		});
		cancelOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancelButton();
				clearLabals();
			}
		});
	}
	/**
     * Loads the orders into the TableView for the current traveler.
     */
	
	@FXML
	public void loadTableView() {
	    String id;
	    Traveler trv = TravelerLoginController.traveler;
	    if (trv != null) {
	        id = String.valueOf(trv.getTravelerId());
	        ArrayList<Order> ordersArrayList = OrderControl.findOrders(id);
	        ArrayList<OrderTable> tbOrdersArrayList = OrderControl.changeOrderToOrderTable(ordersArrayList);
	        init(tbOrdersArrayList);
	        ordersTableView.setItems(getOrders(tbOrdersArrayList));
	    } else {
	        
	    }
	}


	private ObservableList<OrderTable> getOrders(ArrayList<OrderTable> orderArray) {
		ordersTableView.getItems().clear();
		for (OrderTable order : orderArray) {
			ov.add(order);
		}
		return ov;
	}

	
	private void init(ArrayList<OrderTable> orders) {
		orderIdTableColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, Integer>("orderId"));
		visitDateTableColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("orderDate"));
		visitTimeTableColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("orderTime"));
		orderStatusTableColumn.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("orderStatus"));

		ordersTableView.setRowFactory(tv -> {
			TableRow<OrderTable> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					OrderTable clickedRow = row.getItem();
					orderIdLabel.setText(String.valueOf(clickedRow.getOrderId()));
					visitDateLabel.setText(clickedRow.getOrderDate());
					visitTimeLabel.setText(clickedRow.getOrderTime());
					orderStatusLabel.setText(clickedRow.getOrderStatus());
				}
			});
			return row;
		});
	}


	/**
	 * Handles the action to cancel a selected order.
	 * This method checks if an order is selected and its status allows for cancellation. If so,
	 * it attempts to update the order status to CANCELED. It provides user feedback based on the
	 * outcome of the operation. Additionally, it checks the waiting list for any orders that can
	 * now be confirmed due to the cancellation and sends a cancellation message to the traveler.
	 */

	
	private void cancelButton() {

		
		if (!orderChose())
			return;

		if (orderStatusLabel.getText().equals(OrderStatusName.PENDING.toString())
				|| orderStatusLabel.getText().equals(OrderStatusName.PENDING_24_HOURS_BEFORE.toString())
				|| orderStatusLabel.getText().equals(OrderStatusName.IN_WAITING_LIST.toString())
				|| orderStatusLabel.getText().equals(OrderStatusName.AVAILABLE_SPOT.toString())
				|| orderStatusLabel.getText().equals(OrderStatusName.CONFIRMED.toString())) {
			boolean orderControlResult = OrderControl.updateOrderStatus(orderIdLabel.getText(), OrderStatusName.CANCELED);
		
			if (orderControlResult) {
				Order order = new Order(ordersTableView.getSelectionModel().getSelectedItem());
				loadTableView();
				new Alerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order canceled")
						.showAndWait();

				
				OrderControl.reviewWaitingList(Integer.parseInt(orderIdLabel.getText()));
				
				
				
				CancelmessageTraveler(order);
				return;
			} else {
				
				new Alerts(AlertType.ERROR, "System Error", "System Error",
						"Cancellation of this order is currently not possible. Please attempt again later.").showAndWait();
				return;
			}
		} else {
			
			new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Order cannot be canceled").showAndWait();
			return;
		}
	}
	/**
	 * Sends a cancellation message to the traveler for the specified order.
	 * This method constructs a cancellation message using predefined templates and sends it to the traveler.
	 * 
	 * @param order The order that is being cancelled.
	 */
	
	private void CancelmessageTraveler(Order order) {
		Park park = ParkControl.findParkById(String.valueOf(order.getParkId()));
		String subject = MsgTemplates.orderCancel[0];
		String content = String.format(MsgTemplates.orderCancel[1].toString(), park.getParkName(), order.getOrderDate(),
				order.getOrderTime());

		String travelerId = order.getTravelerId();
		String date = LocalDate.now().toString();
		String time = LocalTime.now().toString();
		int orderId = Integer.parseInt(orderIdLabel.getText());

		CommunicationControl.sendMessageToTraveler(travelerId, date, time, subject, content, String.valueOf(orderId));
	}
	/**
	 * Clears the order detail labels in the UI.
	 * This method is typically called after an order operation (confirm/cancel) to reset the displayed information.
	 */
	
	private void clearLabals() {
		orderIdLabel.setText("");
		visitDateLabel.setText("");
		visitTimeLabel.setText("");
		orderStatusLabel.setText("");
	}
	/**
	 * Handles the confirmation of a selected order.
	 * This method checks if the order is in an appropriate status to be confirmed and attempts to update its status accordingly.
	 * It provides user feedback based on the outcome of the operation.
	 */

	private void confirmButton() {
		// Did the user choose order
		if (!orderChose())
			return;


		if (orderStatusLabel.getText().equals(OrderStatusName.AVAILABLE_SPOT.toString())
				|| orderStatusLabel.getText().equals(OrderStatusName.PENDING_24_HOURS_BEFORE.toString())) {
			boolean orderControlResult = OrderControl.updateOrderStatus(orderIdLabel.getText(),
					OrderStatusName.CONFIRMED);
			// Status Changed
			if (orderControlResult) {
				loadTableView();
				new Alerts(AlertType.INFORMATION, "Changes were made", "Changes were made", "Order confirmed")
						.showAndWait();
				return;
			} else {
				new Alerts(AlertType.ERROR, "System Error", "System Error",
						"Could not confirm this order, please try again later.").showAndWait();
				return;
			}
		} else {
			new Alerts(AlertType.ERROR, "Input Error", "Order Error",
					"Make sure the order is in status Available spot or Pending 24 hours before").showAndWait();
			return;
		}
	}
	
	/**
	 * Checks if an order has been selected by the user.
	 * 
	 * @return True if an order is selected; otherwise, false.
	 */
	private boolean orderChose() {
		if (orderIdLabel.getText().isEmpty()) {
			new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose one of the available orders.")
					.showAndWait();
			return false;
		}
		return true;
	}

}