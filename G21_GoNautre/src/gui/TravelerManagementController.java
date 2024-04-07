
package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controllers.OrderControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.CalculatePrice.RegularPreOrderCheckOut;
import Controllers.EmployeeControl;
import alerts.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTable;
import logic.OrderType;
import logic.Park;
import logic.Traveler;
/**
 * Controller class for managing traveler orders and visits.
 * This class handles searching for travelers, confirming their visits,
 * handling exit from the park, and displaying order details in a table view.
 */

public class TravelerManagementController implements Initializable {

	ObservableList<OrderTable> ov = FXCollections.observableArrayList();
	@FXML
	private TextField idTextField;
	
	@FXML
	private TextField visitorsTextField;

	@FXML
	private TableView<OrderTable> ordersTableView;

	@FXML
	private TableColumn<OrderTable, String> statusTableColumn;
	@FXML
	private Button searchButton;

	@FXML
	private Label headerLabel;
	@FXML
	private TableColumn<OrderTable, String> travelerIDTableColumn;

	@FXML
	private TableColumn<OrderTable, Integer> orderIDTableColumn;

	@FXML
	private TableColumn<OrderTable, String> dateTableColumn;

	@FXML
	private Button occVisitButton;

	@FXML
	private Button enterButton;

	@FXML
	private TableColumn<OrderTable, String> timeTableColumn;


	@FXML
	private Label visitorsLabel;

	@FXML
	private Label orderIdLabel;
	
	private OrderTable clickedRow;
	
    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTableView();
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchTraveler();
				clickedRow = null;
				clearLabels();
			}
		});

	}
	/**
	 * Initializes the table view with the provided list of orders.
	 *
	 * @param orders The list of orders to initialize the table view with.
	 */
	private void init(ArrayList<OrderTable> orders) {

		travelerIDTableColumn
				.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<OrderTable, String>("travelerId"));
		orderIDTableColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<OrderTable, Integer>("orderId"));
		dateTableColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<OrderTable, String>("orderDate"));
		timeTableColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<OrderTable, String>("orderTime"));
		statusTableColumn.setCellValueFactory(
				new javafx.scene.control.cell.PropertyValueFactory<OrderTable, String>("orderStatus"));

		ordersTableView.setRowFactory(tv -> {
			TableRow<OrderTable> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					clickedRow = row.getItem();
					orderIdLabel.setText(String.valueOf(clickedRow.getOrderId()));
					visitorsLabel.setText(String.valueOf(clickedRow.getNumberOfVisitors()));
				}
			});
			return row;
		});

	}
	/**
	 * Loads the casual visit form for entering details of a casual visitor.
	 * This method is invoked when the corresponding button is clicked.
	 */
	
	@FXML
	private void loadCasualVisit() {
		try {
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CasualTravelerEntry.fxml"));
			CasualEntryController controller = new CasualEntryController();
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();
			Stage newStage = new Stage();
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);
			newStage.getIcons().add(new javafx.scene.image.Image(GoNatureConstants.APP_ICON_PATH));
			newStage.setTitle("Casual Visit");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.setUserData(this);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("faild to load form");
		}
	}

	
	private Stage getStage() {
		return (Stage) occVisitButton.getScene().getWindow();
	}

	
	@FXML
	public void loadTableView() {
		ArrayList<Order> ordersArrayList = OrderControl.findOrdersByParkId(EmployeeLoginController.member.getParkId());
		ArrayList<OrderTable> tbOrdersArrayList = OrderControl.changeOrderToOrderTable(ordersArrayList);
		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

	
	private ObservableList<OrderTable> getOrders(ArrayList<OrderTable> orderArray) {
		ordersTableView.getItems().clear();
		for (OrderTable order : orderArray) {
			ov.add(order);
		}
		return ov;
	}
	/**
	 * Handles the action of exiting the park for the selected traveler.
	 * This method is invoked when the corresponding button is clicked.
	 */
    @FXML
    private void exitButton() {
		
		if (clickedRow == null) {
			new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please select traveler")
					.showAndWait();
			return;
		}
		if (!clickedRow.getOrderStatus().equals(OrderStatusName.ENTERED_THE_PARK.toString()))
		{
			new Alerts(AlertType.ERROR, "Input Error", "Order Error",
					"Make sure the traveler entered in the park").showAndWait();
			return;
		}
		
        if (idTextField.getText().isEmpty())
        {
			new Alerts(AlertType.ERROR, "Input Error", "Order Error", "Please enter ID")
				.showAndWait();
			return;
        }
        
        else
        {
        	LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            EmployeeControl.RunExitStatusChange(idTextField.getText(),formattedTime);
    		new Alerts(AlertType.INFORMATION, "Success", "Exit", "Exit succeed")
    		.showAndWait();
        }

    }
    /**
     * Clears the displayed labels showing order details.
     */
	private void clearLabels() {
		orderIdLabel.setText("");
		visitorsLabel.setText("");
	}
	
	/**
	 * Handles the action of confirming the selected order.
	 * This method is invoked when the corresponding button is clicked.
	 */
	
	@FXML
	private void enterButton() {
		if (clickedRow == null) {
			new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose order to confirm")
					.showAndWait();
			return;
		}
		if (!checkTravelerEnter()) {
			new Alerts(AlertType.ERROR, "Input Error", "Order Error",
					"Make sure the order is in the right time/date and that the order is confirmed").showAndWait();
			return;
		}

		double price = 0;
		int numberOfParticipantsInOriginalOrder = clickedRow.getNumberOfVisitors();
		int numberOfParticipantsInCurrentOrder = Integer.parseInt(visitorsLabel.getText());
		if (numberOfParticipantsInOriginalOrder < numberOfParticipantsInCurrentOrder) {
			new Alerts(AlertType.ERROR, "Input Error", "Order Error",
					"You can't list more people than the order mentioned").showAndWait();
			return;
		}
		if (numberOfParticipantsInCurrentOrder <= 0) {
			new Alerts(AlertType.ERROR, "Input Error", "Order Error", "Order must have more than 0 participants")
					.showAndWait();
			return;
		}
		
		OrderControl.updateOrderStatus(String.valueOf(clickedRow.getOrderId()), OrderStatusName.ENTERED_THE_PARK);
		String id = clickedRow.getTravelerId();
		String type = clickedRow.getOrderType().toString();
		price = clickedRow.getPrice();
		if (numberOfParticipantsInCurrentOrder != numberOfParticipantsInOriginalOrder) {
			
			OrderControl. updateVisitorsAmountInOrder(String.valueOf(clickedRow.getOrderId()),
					numberOfParticipantsInCurrentOrder);

			String orderId = String.valueOf(clickedRow.getOrderId());
			price = calculatePriceForVisit(id, numberOfParticipantsInCurrentOrder, type);
			
			OrderControl.updatePriceForOrder(orderId, price);
		}

		
		Order tempOrder = new Order(clickedRow);
		tempOrder.setNumberOfParticipants(numberOfParticipantsInCurrentOrder);
		tempOrder.setTotalPrice(price);
		clickedRow = new OrderTable(tempOrder);
		OrderControl.AddVisit(clickedRow);
		Park p = ParkControl.findParkById(String.valueOf(clickedRow.getParkId()));
		int updateNumber = p.getCurrentVisitors() + numberOfParticipantsInCurrentOrder;
		ParkControl.changeVisitorsNumber(clickedRow.getParkId(), updateNumber);
		ParkControl.setParkFullStatus(p);
		loadTableView();

	}
	/**
	 * Calculates the price for a visit based on the provided parameters.
	 *
	 * @param id             The ID of the traveler.
	 * @param visitorsNumber The number of visitors in the order.
	 * @param type           The type of order (e.g., SOLO, FAMILY).
	 * @return The calculated price for the visit.
	 */
	private double calculatePriceForVisit(String id, int visitorsNumber, String type) {
		double price = 0;
		String idOfTraveler = id;
		int numberOfVisitors = visitorsNumber;
		String orderType = type;
		LocalDate today = LocalDate.now();
		int parkId = EmployeeLoginController.member.getParkId();
		if (orderType.equals(OrderType.SOLO.toString()) || orderType.equals(OrderType.FAMILY.toString())) {

			price = new RegularPreOrderCheckOut(numberOfVisitors).getPrice();
			return price;
		} else {
			price = new RegularPreOrderCheckOut(numberOfVisitors).getPrice();
			
			return price;
		}

	}

	/**
	 * Searches for a traveler's orders within the current park based on the provided traveler ID.
	 * If the ID is empty, all orders for the park are loaded.
	 * @return True if the traveler has entered the park; otherwise, false.
	 */
	private boolean checkTravelerEnter() {

		LocalTime orderTime = LocalTime.parse(clickedRow.getOrderTime());
		LocalDate orderDate = LocalDate.parse(clickedRow.getOrderDate());

		if (!orderDate.equals(LocalDate.now()))
			return false;


		if (!clickedRow.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString()))
			return false;
		
		

		if (LocalTime.now().isAfter(orderTime.minusMinutes(30)))
			return true;
		return false;

	}
	
	/**
	 * Searches for a traveler's orders within the current park based on the provided traveler ID.
	 * If the ID is empty, all orders for the park are loaded.
	 */ 
	
	private void searchTraveler() {
		String id = idTextField.getText();
		if (id.isEmpty()) {
			loadTableView();
			return;
		}
	
		String parkId = String.valueOf(EmployeeLoginController.member.getParkId());
		ArrayList<Order> ordersArrayList = OrderControl.findTravelerOrdersInPark(parkId, id);
		ArrayList<OrderTable> tbOrdersArrayList = OrderControl.changeOrderToOrderTable(ordersArrayList);
		if (ordersArrayList.isEmpty()) {
			new Alerts(AlertType.ERROR, "Input error", "ID error", "No orders found for " + id).showAndWait();
			return;
		}

		init(tbOrdersArrayList);
		ordersTableView.setItems(getOrders(tbOrdersArrayList));
	}

}
