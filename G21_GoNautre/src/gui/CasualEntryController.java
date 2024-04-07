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
import Controllers.CalculatePrice.CasualSoloFamilyVisitCheckOut;
import Controllers.CalculatePrice.GroupCasualCheckOut;
import alerts.Alerts;

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
import logic.GoNatureConstants;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTable;
import logic.OrderType;
import logic.Park;


/**
 * Controller class for managing casual entry orders.
 */
public class CasualEntryController implements Initializable {

    @FXML
    private TextField idInputCasualEntry;

    @FXML
    private TextField InputEmail;

    @FXML
    private Button placeButton;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button checkPriceButton;

    @FXML
    private ComboBox<OrderType> typeComboBox;

    @FXML
    private Label headerLabel;

    @FXML
    private Label permissionLabel;

    @FXML
    private TextField numOfVisitorsCasualVisit;

    private Order order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxTypeOrder();
        initListeners();

        checkPriceButton.setOnAction(event -> {
            if (IsValidInformation())
                checkPricebtnAction();
        });

        placeButton.setOnAction(event -> {
            if (IsValidInformation() && IsvalidPrice())
                placeOrderAction();
        });
    }

    /**
     * Initializes listeners for text field changes.
     */
    private void initListeners() {
        idInputCasualEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            permissionLabel.setText("Guest");
            initComboBoxTypeOrder();
        });
    }

    /**
     * Initializes the order type combo box.
     */
    private void initComboBoxTypeOrder() {
        typeComboBox.getItems().clear();
        typeComboBox.getItems().addAll(Arrays.asList(OrderType.values()));

        typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
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

    /**
     * Retrieves the current order type from the combo box.
     *
     * @return The current order type.
     */
    private String currentOrderType() {
        if (typeComboBox.getValue() == null)
            return "null";
        return typeComboBox.getValue().toString();
    }

    /**
     * Validates the input information for placing an order.
     *
     * @return True if the information is valid, false otherwise.
     */
    private boolean IsValidInformation() {
        String orderType = currentOrderType();
        String travelerId = idInputCasualEntry.getText();
        String email = InputEmail.getText();
        String casualVisitorsNumber = numOfVisitorsCasualVisit.getText();

        if (travelerId.isEmpty() || email.isEmpty() || orderType.equals("null") || casualVisitorsNumber.isEmpty()) {
            popNotification(AlertType.ERROR, "Input Error", "Ensure all fields are completed accurately. ");
            return false;
        }

        if (!isNumeric(travelerId) || travelerId.length() != 9) {
            popNotification(AlertType.ERROR, "Input Error", "Please ensure accurate completion of the form.");
            return false;
        }

        int numOfVisitors = Integer.parseInt(casualVisitorsNumber);
        if (orderType.equals(OrderType.GROUP.toString()) && numOfVisitors < 2) {
            popNotification(AlertType.ERROR, "Input Error", "Unable to place an order for a single person.");
            return false;
        }

        if (numOfVisitors > 15 || numOfVisitors <= 0) {
            popNotification(AlertType.ERROR, "Input Error", "The number of visitors must be up to 15. ");
            return false;
        }

        if (TravelerControl.findGuide(travelerId) == null && orderType.equals(OrderType.GROUP.toString())) {
            new Alerts(AlertType.ERROR, "Bad Input", "Not Registered Guide", "Please contact service employee").showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Performs the action of checking the price.
     */
    private void checkPricebtnAction() {
        double price = entryPriceCalculate();
        totalPriceLabel.setText(String.valueOf(price));
    }

    /**
     * Calculates the entry price based on the order type and number of visitors.
     *
     * @return The calculated entry price.
     */
    private double entryPriceCalculate() {
        double price = 0;
        String orderType = currentOrderType();
        int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());

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

    /**
     * Validates the price entered by the user.
     *
     * @return True if the price is valid, false otherwise.
     */
    private boolean IsvalidPrice() {
        if (totalPriceLabel.getText().isEmpty()) {
            popNotification(AlertType.ERROR, "Price error", "Confirm the price to advance.");
            return false;
        }

        double priceInForm = Double.parseDouble(totalPriceLabel.getText());
        double realPrice = entryPriceCalculate();
        if (priceInForm == realPrice)
            return true;
        popNotification(AlertType.ERROR, "Price error", "Confirm the price to advance.");
        return false;
    }

    /**
     * Displays a notification dialog.
     *
     * @param type   The type of the alert.
     * @param header The header text of the alert.
     * @param content The content text of the alert.
     */
    private void popNotification(AlertType type, String header, String content) {
        new Alerts(type, header, header, content).showAndWait();
    }

    /**
     * Handles the action of placing an order.
     */
    private void placeOrderAction() {
        String travelerId = idInputCasualEntry.getText();
        int numberOfVisitors = Integer.parseInt(numOfVisitorsCasualVisit.getText());
        String orderType = currentOrderType();
        String email = InputEmail.getText();
        int parkId = EmployeeLoginController.member.getParkId();

        order = new Order(travelerId, parkId, LocalDate.now().toString(), LocalTime.now().toString(), orderType,
                numberOfVisitors, email, Double.parseDouble(totalPriceLabel.getText()),
                OrderStatusName.ENTERED_THE_PARK.toString());

        OrderTable OrderTable = new OrderTable(order);
        if (order.getOrderTime().compareTo("18:01:00") < 0) {
            if ((ParkControl.findParkById(String.valueOf(parkId)).getCurrentVisitors() + numberOfVisitors)
                    <= (ParkControl.findParkById(String.valueOf(parkId)).getMaxVisitors())) {
                if (OrderControl.AddCasualOrder(order)) {
                    OrderControl.AddVisit(OrderTable);

                    int updateNumber = ParkControl.findParkById(String.valueOf(parkId)).getCurrentVisitors()
                            + numberOfVisitors;

                    ParkControl.changeVisitorsNumber(parkId, updateNumber);
                    Park park = ParkControl.findParkById(String.valueOf(EmployeeLoginController.member.getParkId()));
                    ParkControl.setParkFullStatus(park);

                    order = OrderControl.findTravelerRecentOrder(travelerId);

                    Stage stage = (Stage) idInputCasualEntry.getScene().getWindow();
                    TravelerManagementController manageTravelerController = (TravelerManagementController) stage.getUserData();
                    manageTravelerController.loadTableView();
                    stage.close();

                    loadOrderConfirmation();
                } else {
                    popNotification(AlertType.ERROR, "System Error", "An error has occurred, please try again");
                }
            } else {
                new Alerts(AlertType.ERROR, "Error", "The park is full",
                        "Sorry, the park is full. Please come back at another time.").showAndWait();
            }
        } else {
            new Alerts(AlertType.ERROR, "Error", "The park is closed",
                    "Sorry, you can't enter the park after 18:00.").showAndWait();
        }
    }
	
	
	
    /**
     * Loads the order confirmation dialog.
     */
	public void loadOrderConfirmation() {
		try {
			if (order == null) {
				System.out.println("The order is NULL. Unable to proceed with loading the order confirmation.");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CasualEntryReceipt.fxml"));
			CasualEntryReceiptController controller = new CasualEntryReceiptController();
			controller.setOrder(order);
			loader.setController(controller);
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Order receipt");
			newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			System.out.println("Unable to load the form.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function check whether a string consists of only numbers.
	 * 
	 * @param str the string to check
	 * @return true if string consists of only numbers
	 * @return false otherwise
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}