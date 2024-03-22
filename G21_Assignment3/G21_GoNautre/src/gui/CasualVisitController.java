package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ResourceBundle;

import Controllers.OrderControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.calculatePrice.CasualSoloFamilyVisitCheckOut;
import Controllers.calculatePrice.CheckOut;
import Controllers.calculatePrice.GroupCasualCheckOut;
import alerts.CustomAlerts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTb;
import logic.OrderType;
import logic.Park;
import util.UtilityFunctions;

/**
 * This Class is the GUI controller of CasualTravelerVisit.fxml It handles all
 * the JavaFx nodes events.
 * 
 * In this screen the entrance worker makes a casual visit.
 *
 */
public class CasualVisitController implements Initializable {

	@FXML
	private TextField idInputCasualVisit;

	@FXML
	private TextField emailInputCasualVisit;

	@FXML
	private ComboBox<OrderType> typeComboBox;

	@FXML
	private TextField numOfVisitorsCasualVisit;

	@FXML
	private Label headerLabel;

	@FXML
	private Button placeOrderBtn;

	@FXML
	private Label totalPriceLabel;

	@FXML
	private Button checkPriceBtn;

	@FXML
	private Label permissionLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxOrderType();
		initListeners();

		// In order to check price - all info has to be valid
		checkPriceBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validInfo())
					checkPricebtnAction();
			}
		});

		// In order to place order - info and price has to be valid.
		placeOrderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validInfo() && validPrice())
					placeOrderAction();
			}
		});
	}

	private void initComboBoxOrderType() {

		typeComboBox.getItems().clear();
		typeComboBox.getItems().addAll(Arrays.asList(OrderType.values()));

		/* Listener to order type ComboBox. activate on every item selected */
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.toString().equals("Solo Visit")) {
					numOfVisitorsCasualVisit.setText("1");
					numOfVisitorsCasualVisit.setEditable(false);

				} else {
					numOfVisitorsCasualVisit.setText("");
					numOfVisitorsCasualVisit.setPromptText("Visitor's Number");
					numOfVisitorsCasualVisit.setEditable(true);
				}
			}
		});
	}

	/*
	 * This function init the listeners for permission label.
	 */
	private void initListeners() {
		idInputCasualVisit.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

				permissionLabel.setText("Guest");

				initComboBoxOrderType();
			}
		});
	}

	/*
	 * This function return the current OrderType in a string format if the user did
	 * not choose, it returns null.
	 */
	private String currentOrderType() {
		if (typeComboBox.getValue() == null)
			return "null";
		return typeComboBox.getValue().toString();
	}

	/*
	 * This function return if order details are valid, otherwise it shows a pop up
	 * screen with relevent message.
	 */
	private boolean validInfo() {

		// Recive the data from the text fields.
		String idOfTraveler = idInputCasualVisit.getText();
		String orderType = currentOrderType();
		String email = emailInputCasualVisit.getText();
		String numOfCasualVisitors = numOfVisitorsCasualVisit.getText();

		// Check if one of the text fields is empty.
		if (idOfTraveler.isEmpty() || orderType.equals("null") || email.isEmpty() || numOfCasualVisitors.isEmpty()) {
			popNotification(AlertType.ERROR, "Input Error", "Please fill all of the fields correctly ");
			return false;
		}

		// Input validation for id and email.
		if (!UtilityFunctions.isNumeric(idOfTraveler) || idOfTraveler.length() != 9
				|| !UtilityFunctions.isValidEmailAddress(email)) {
			popNotification(AlertType.ERROR, "Input Error", "Please fill the form correctly");
			return false;
		}

		// Recive the data after it has been validated, in order to avoid exceptions.
		int numOfVisitors = Integer.parseInt(numOfCasualVisitors);
//
		// Input validation for number of visitors.
		if (numOfVisitors > 15 || numOfVisitors <= 0) {
			popNotification(AlertType.ERROR, "Input Error", "The number of visitors must be up to 15. ");
			return false;
		}
		if(TravelerControl.getSubscriber(idInputCasualVisit.getText()) == null
				&& orderType.equals(OrderType.GROUP.toString()))
		{
			new CustomAlerts(AlertType.ERROR, "Bad Input", "Not Registered Guide",
					"Please contact service employee").showAndWait();
		}

		// Input validation - group with one participant.
		if (orderType.equals(OrderType.GROUP.toString()) && numOfVisitors < 2) {
			popNotification(AlertType.ERROR, "Input Error", "Can't order for group of 1");
			return false;
		}

		return true;
	}

	/*
	 * This function calculate the order price.
	 */
	private double calculatePriceForVisit() {
		double price = 0;
		// Recive the data from the text fields.
		// String idOfTraveler = idInputCasualVisit.getText();
		int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());
		String orderType = currentOrderType();

		// Setting up vars
		// boolean existTraveler = TravelerControl.isTravelerExist(idOfTraveler);
		// LocalDate today = LocalDate.now();
		// int parkId = MemberLoginController.member.getParkId();

		// Setting up price class.

		// Order for group has no discount.
		if (orderType.equals(OrderType.GROUP.toString())) {
			price = (new GroupCasualCheckOut(numberOfVisitors)).getPrice();
			return price;
		}
		if (orderType.equals(OrderType.SOLO.toString()) || orderType.equals(OrderType.FAMILY.toString())) {
			price = (new CasualSoloFamilyVisitCheckOut(numberOfVisitors)).getPrice();
			return price;
		}

		return price;
	}

	/*
	 * This function set the label for price label.
	 */
	private void checkPricebtnAction() {
		double price = calculatePriceForVisit();
		totalPriceLabel.setText(String.valueOf(price));
	}

	/*
	 * This function check if the price that appears in the form is valid
	 */
	private boolean validPrice() {
		if (totalPriceLabel.getText().equals("")) {
			popNotification(AlertType.ERROR, "Price error", "Please check the price in order to continue");
			return false;
		}

		double priceInForm = Double.parseDouble(totalPriceLabel.getText());
		double realPrice = calculatePriceForVisit();
		if (priceInForm == realPrice)
			return true;
		popNotification(AlertType.ERROR, "Price error", "Please check the price in order to continue");
		return false;
	}

	/*
	 * This function is a wrapper for alerts.
	 */
	private void popNotification(AlertType type, String header, String content) {
		new CustomAlerts(type, header, header, content).showAndWait();
	}

	/*
	 * This function handle the place order button when pressed.
	 */
	private void placeOrderAction() {

		// Recive the data from the text fields.
		String idOfTraveler = idInputCasualVisit.getText();
		int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());
		String orderType = currentOrderType();
		String email = emailInputCasualVisit.getText();
		int parkId = MemberLoginController.member.getParkId();

		// Creating new order with relevent details.
		Order order = new Order(idOfTraveler, parkId, LocalDate.now().toString(), LocalTime.now().toString(), orderType,
				numberOfVisitors, email, Double.parseDouble(totalPriceLabel.getText()),
				OrderStatusName.ENTERED_THE_PARK.toString());

		// Since addVisit Uses orderTb from previous controllers,we need to convert it
		// with builder.
		// Adding casual Order is the same as adding order
		// Adding visit adds the visit to the DB

		OrderTb orderTb = new OrderTb(order);
		if (OrderControl.addCasualOrder(order)) {
			OrderControl.addVisit(orderTb);

			// Updated number = the number of visitors after the entrance of the casual
			// visit.
			int updateNumber = ParkControl.getParkById(String.valueOf(parkId)).getCurrentVisitors() + numberOfVisitors;

			// Updating the number of visitors in the park
			ParkControl.updateCurrentVisitors(parkId, updateNumber);
			Park park = ParkControl.getParkById(String.valueOf(MemberLoginController.member.getParkId()));

			ParkControl.updateIfParkFull(park);

			// Notifying the visit is approved.

			// Need to get orderId from DB
			order = OrderControl.getTravelerRecentOrder(idOfTraveler);
			System.out.println(order.getOrderDate() + order.getOrderTime() + order.getOrderType());

			// Closing the scene and updating the table for entrance worker.
			Stage stage = (Stage) idInputCasualVisit.getScene().getWindow();
			ManageTravelerController manageTravelerController = (ManageTravelerController) stage.getUserData();
			manageTravelerController.loadTableView();
			stage.close();

			// Setting the receipt window.
			loadOrderConfirmation(order);
			System.out.println("701");
		} else {
			popNotification(AlertType.ERROR, "System Error", "An error has occurred, please try again");
		}
	}

	/*
	 * This function handle the receipt.
	 */
	public void loadOrderConfirmation(Order order) {
		try {
			if (order == null) {
				System.out.println("Order is null. Cannot load order confirmation.");
				return;
			}
			// Get the controller instance from the FXMLLoader
			CasualVisitReceiptController controller = new CasualVisitReceiptController();

			controller.setOrder(order);
			System.out.println("1" + order.getOrderDate() + order.getOrderTime() + order.getOrderType());


			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CasualVisitReceipt.fxml"));
			Parent root = loader.load();

			Stage newStage = new Stage();
			newStage.setTitle("Order receipt");
			newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			System.out.println("Failed to load form");
			e.printStackTrace();
		}
	}

}