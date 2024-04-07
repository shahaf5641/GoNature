package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import Controllers.OrderControl;
import Controllers.ParkControl;
import Controllers.TravelerControl;
import Controllers.CalculatePrice.*;
import alerts.Alerts;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderType;
import logic.Park;
import logic.Traveler;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Controller class for managing order visit functionality in the GUI.
 */

public class OrderVisitController implements Initializable {

	@FXML
	private AnchorPane orderVisitRootAnchorPane;

	@FXML
	private Accordion accordion;

	@FXML
	private TitledPane identificationTitledPane;

	@FXML
	private AnchorPane identificationAnchorPane;

	@FXML
	private ComboBox<String> parksComboBox;

	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField idInputOrderVisitTextField;

	@FXML
	private AnchorPane informationAnchorPane;


	@FXML
	private TextField emailInputOrderVisit;

	@FXML
	private ComboBox<OrderType> typeComboBox;

	@FXML
	private Label requiredFieldsLabel;

	@FXML
	private AnchorPane paymentAnchorPane;

	@FXML
	private RadioButton payNowRadioButton;

	@FXML
	private RadioButton payLaterRadioButton;

	@FXML
	private AnchorPane paymentPane;

	@FXML
	private ComboBox<String> timeComboBox;

	@FXML
	private TextField fullNameInput;

	@FXML
	private TextField phoneInput;

	@FXML
	private TextField cardHolderName;

	@FXML
	private TextField cardHolderLastName;

	@FXML
	private TextField CardNumberTextField;

	@FXML
	private TextField CCV;

	@FXML
	private TextField numOfVisitorsOrderVisit;

	@FXML
	private DatePicker CardExpiryDatePicker;

	@FXML
	private Label permissionLabel;

	@FXML
	private Label orderVisitHeaderLabel;
	@FXML
	private Label summaryTotalPrice;

	@FXML
	private Label summaryFullName;

	@FXML
	private ProgressIndicator pi;


	@FXML
	private Label summaryID;

	@FXML
	private Label summaryPark;

	@FXML
	private Label summaryDate;

	@FXML
	private Label summaryPayment;

	

	@FXML
	private Label summaryEmail;

	@FXML
	private Label summaryPhone;

	@FXML
	private Label summaryTime;
	@FXML
	private Label summaryType;

	@FXML
	private Label summaryVisitors;

	DecimalFormat df = new DecimalFormat("####0.00");
	private Traveler traveler;
	private boolean isOrderFromMain = false;
	private Order order;
	private Order recentOrder;
	   /**
     * Initializes the controller.
     * 
     * @param arg0 ResourceBundle
     * @param arg1 URL
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Locale.setDefault(Locale.ENGLISH);
		accordion.setExpandedPane(identificationTitledPane);
		initComboBoxes();
		initRadioBoxes();
		initDatePicker();
		initLabels();
	}
	/**
	 * Handles the action when the user clicks on the place order button.
	 * Validates user input and initiates the order placement process.
	 */
	@FXML
	private void placeOrderButton() {

		if (checkInputValid()) {

			Task<Boolean> task = new Task<Boolean>() {
				@Override
				protected Boolean call() throws Exception {

					order = new Order(0, summaryID.getText(), getIDParkIdSelected(), summaryDate.getText(),
							summaryTime.getText(), summaryType.getText(), Integer.parseInt(summaryVisitors.getText()),
							summaryEmail.getText(), CalculatePrice(), OrderStatusName.PENDING.toString());
					String[] travelerName = summaryFullName.getText().split(" ");
					String travelerFirstName = travelerName[0];
					String travelerLastName = travelerName.length == 1 ? "" : travelerName[1];
					traveler = new Traveler(summaryID.getText(), travelerFirstName, travelerLastName,
							summaryEmail.getText(), summaryPhone.getText());
					recentOrder = OrderControl.addNewOrderAndSendNotification(order, traveler);
					if (recentOrder != null)
						return true;
					return false;
				}
			};

			pi.setVisible(true);
			orderVisitRootAnchorPane.setDisable(true);
			new Thread(task).start();

			task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent t) {
					boolean res = false;
					res = task.getValue();
					pi.setVisible(false);
					orderVisitRootAnchorPane.setDisable(false);
					if (res) {
						loadOrderReciept();
					} else {
						loadWaitingListOrRescheduleScreen(order);
					}
				}
			});

			task.addEventHandler(WorkerStateEvent.WORKER_STATE_FAILED, new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent t) {
					pi.setVisible(false);
					orderVisitRootAnchorPane.setDisable(false);
				}
			});

		}

	}

	/**
	 * Retrieves the park ID of the selected park.
	 * 
	 * @return The park ID of the selected park, or -1 if not found.
	 */
	private int getIDParkIdSelected() {
		Park park = ParkControl.findParkByName(summaryPark.getText());
		if (park != null)
			return park.getParkId();
		else
			return -1;
	}
	/**
	 * Validates user input before placing the order.
	 * Displays error alerts for invalid input.
	 * 
	 * @return True if input is valid, false otherwise.
	 */

	private boolean checkInputValid() {
		if (!checkAllFields())
			new Alerts(AlertType.ERROR, "Bad Input", "Bad Input", "Please fill all the fields").showAndWait();
		else if (summaryID.getText().length() != 9)
			new Alerts(AlertType.ERROR, "Bad Input", "Bad ID Input", "Id length must be 9").showAndWait();
		else if (!VerifyOrderIfTimeIs24HouesFromNow()) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visit Time",
					"Visit time must be atleast 24 hours from now").showAndWait();
		} else if (Integer.parseInt(summaryVisitors.getText()) > 15
				&& summaryType.getText().equals(OrderType.GROUP.toString())) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visitor's Number",
					"Group order can be up to 15 travelers").showAndWait();
		} else if (Integer.parseInt(summaryVisitors.getText()) < 2
				&& summaryType.getText().equals(OrderType.GROUP.toString())) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visitor's Number",
					"Group order must have atleast 2 visitors").showAndWait();
		} 
		else if(TravelerControl.findGuide(summaryID.getText()) == null
				&& summaryType.getText().equals(OrderType.GROUP.toString()))
		{
			new Alerts(AlertType.ERROR, "Bad Input", "Not Registered Guide",
					"Please contact service employee").showAndWait();
		}
		else if (Integer.parseInt(summaryVisitors.getText()) > 9
				&& summaryType.getText().equals(OrderType.FAMILY.toString())) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visitor's Number",
					"Family order can be up to 9 travelers").showAndWait();
		} else if (Integer.parseInt(summaryVisitors.getText()) < 1) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visitor's Number",
					"Visitor's number must be positive number and atleast 2. ").showAndWait();
		} 
		 else if (!isNumeric(summaryVisitors.getText())) {
			new Alerts(AlertType.ERROR, "Bad Input", "Invalid Visitor's Number",
					"Visitor's number must be a positive number and atleast 1. ").showAndWait();
		} else {
			return true;
		}
		return false;

	}

	private boolean checkAllFields() {
		if (summaryID.getText().isEmpty() || summaryPark.getText().isEmpty() || summaryDate.getText().isEmpty()
				|| summaryType.getText().isEmpty() || summaryVisitors.getText().isEmpty()
				|| summaryEmail.getText().isEmpty() || summaryTime.getText().isEmpty()
				|| summaryFullName.getText().isEmpty() || summaryPhone.getText().isEmpty()
				|| summaryType.getText().equals("null") || summaryPark.getText().equals("null")
				|| summaryDate.getText().equals("null") || summaryTime.getText().equals("null")) {
			return false;
		} else if (summaryPayment.getText().equals("At The Park"))
			return true;
		else {
			if (cardHolderName.getText().isEmpty() || cardHolderLastName.getText().isEmpty() || CCV.getText().isEmpty()
					|| CardNumberTextField.getText().isEmpty() || CardExpiryDatePicker.valueProperty().getValue() == null)
				return false;
			else
				return true;
		}
	}

	private void loadOrderReciept() {
		try {
			Stage thisStage = getStage();
			Stage newStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderReciept.fxml"));
			OrderRecieptController controller = new OrderRecieptController();
			controller.setOrder(recentOrder);
			controller.setTraveler(traveler);
			controller.setSummaryPayment(summaryPayment.getText());
			controller.setOrderFromWeb(true);
			loader.setController(controller);
			loader.load();
			Parent p = loader.getRoot();

			newStage.setTitle("Order Reciept");
			newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			if (isOrderFromMain)
				thisStage.close();
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean VerifyOrderIfTimeIs24HouesFromNow() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String currentDateAndTime = dtf.format(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(currentDateAndTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.HOUR_OF_DAY, -1); // new
		currentDateAndTime = sdf.format(c.getTime());
		Date orderDate = null;
		Date dateOfTommorow = null;
		try {
			orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(summaryDate.getText() + " " + summaryTime.getText());
			dateOfTommorow = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(currentDateAndTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (dateOfTommorow.after(orderDate)) {
			return false;
		}
		return true;
	}
	/**
	 * Initializes the labels and listeners for the order visit screen.
	 * If the order is not from the main screen, sets traveler details and disables input fields.
	 */
	private void initLabels() {

		if (!isOrderFromMain) {
			String id = "";

			id = TravelerLoginController.traveler.getTravelerId();
			fullNameInput.setText(TravelerLoginController.traveler.getFirstName() + " "
					+ TravelerLoginController.traveler.getLastName());
			emailInputOrderVisit.setText(TravelerLoginController.traveler.getEmail());
			phoneInput.setText(TravelerLoginController.traveler.getPhoneNumber());
			idInputOrderVisitTextField.setText(id);
			idInputOrderVisitTextField.setDisable(true);
			fullNameInput.setDisable(true);
			emailInputOrderVisit.setDisable(true);
			phoneInput.setDisable(true);
			permissionLabel.setText("Guest");
			initComboBoxes();

		}
		summaryPark.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryPark.setVisible(false);
				else
					summaryPark.setVisible(true);
			}
		});
		summaryDate.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryDate.setVisible(false);
				else
					summaryDate.setVisible(true);
			}
		});
		summaryTime.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryTime.setVisible(false);
				else
					summaryTime.setVisible(true);
			}
		});

		summaryType.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("null"))
					summaryType.setVisible(false);
				else {
					summaryType.setVisible(true);
					if (!summaryID.getText().isEmpty() && !summaryDate.getText().isEmpty()
							&& !summaryVisitors.getText().isEmpty() && !summaryType.getText().isEmpty()) {
						summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
					}
				}
			}
		});

		phoneInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				phoneInput.setText(arg2.replaceAll("[^\\d]", ""));
			}
		});

		idInputOrderVisitTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				idInputOrderVisitTextField.setText(arg2.replaceAll("[^\\d]", ""));
				if (arg2.length() == 9) {
					permissionLabel.setText("Guest");

					if (!summaryVisitors.getText().isEmpty())
						summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
				} else {
					permissionLabel.setText("Guest");
					summaryTotalPrice.setText("");
				}

				initComboBoxes();
			}

		});

		summaryVisitors.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (!idInputOrderVisitTextField.getText().isEmpty())
					summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
				else {
					summaryTotalPrice.setText("");
				}
			}
		});

		datePicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
			}
		});

		summaryID.textProperty().bind(Bindings.convert(idInputOrderVisitTextField.textProperty()));
		summaryPark.textProperty().bind(Bindings.convert(parksComboBox.valueProperty()));
		summaryDate.textProperty().bind(Bindings.convert(datePicker.valueProperty()));
		summaryTime.textProperty().bind(Bindings.convert(timeComboBox.valueProperty()));
		summaryType.textProperty().bind(Bindings.convert(typeComboBox.valueProperty()));
		summaryVisitors.textProperty().bind(Bindings.convert(numOfVisitorsOrderVisit.textProperty()));
		summaryEmail.textProperty().bind(Bindings.convert(emailInputOrderVisit.textProperty()));
		summaryFullName.textProperty().bind(Bindings.convert(fullNameInput.textProperty()));
		summaryPhone.textProperty().bind(Bindings.convert(phoneInput.textProperty()));
	}
	/**
	 * Initializes the date picker to disable past dates.
	 */
	
	private void initDatePicker() {
		datePicker.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				setDisable(empty || date.compareTo(today) < 0 || date.compareTo(today) == 0);
			}
		});
	}
	/**
	 * Initializes the radio boxes for payment options.
	 */
	private void initRadioBoxes() {
		summaryPayment.setText("Online");
		payNowRadioButton.setSelected(true);
		payLaterRadioButton.setSelected(false);
		paymentPane.setVisible(true);
		paymentPane.setDisable(false);
	}


	/**
	 * Initializes the combo boxes for parks, order type, and visit time.
	 */
	private void initComboBoxes() {
		parksComboBox.getItems().clear();
		typeComboBox.getItems().clear();
		timeComboBox.getItems().clear();
		ArrayList<String> parksNames = ParkControl.findParksNames();
		if (parksNames != null) {
			parksComboBox.getItems().addAll(parksNames);
		}
		
		typeComboBox.getItems().addAll(Arrays.asList(OrderType.values()));
	
		typeComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (newItem == null) {
			} else {
				if (newItem.toString().equals(OrderType.SOLO.toString())) {
					numOfVisitorsOrderVisit.setText("1");
					numOfVisitorsOrderVisit.setDisable(true);

				} else {
					numOfVisitorsOrderVisit.setText("");
					numOfVisitorsOrderVisit.setPromptText("Visitor's Number");
					numOfVisitorsOrderVisit.setDisable(false);
				}
			}
		});

		timeComboBox.getItems().addAll(GoNatureConstants.AVAILABLE_HOURS_LIST);
	}
	/**
	 * Loads the reschedule screen for the given order.
	 * 
	 * @param order The order to reschedule.
	 */
	private void loadWaitingListOrRescheduleScreen(Order order) {
		try {
			Stage newStage = new Stage();
			Stage thisStage = getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WaitingListOrReschedule.fxml"));
			WaitingListOrRescheduleController controller = new WaitingListOrRescheduleController();
			controller.setOrder(order);
			loader.setController(controller);
			loader.load();
			controller.setRescheduleStage(newStage);
			if (isOrderFromMain) {
				controller.setOrderFromMain(true);

			}
			controller.setOrderStage(thisStage);
			controller.setTraveler(traveler);
			Parent p = loader.getRoot();
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(thisStage);
			newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
			newStage.setTitle("Reschedule");
			newStage.setScene(new Scene(p));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Retrieves the stage of the current scene.
	 * 
	 * @return The stage of the current scene.
	 */
	private Stage getStage() {
		return (Stage) summaryID.getScene().getWindow();
	}
	public void setOrderFromMain(boolean isOrderFromMain) {
		this.isOrderFromMain = isOrderFromMain;
	}
	/**
	 * Handles turning off the "Pay Now" option.
	 */
	@FXML
	private void turnOffPayNow() {
		if (!payNowRadioButton.isSelected())
			payLaterRadioButton.setSelected(true);
		else {
			payNowRadioButton.setSelected(false);
			paymentPane.setVisible(false);
			paymentPane.setDisable(true);
			summaryPayment.setText("At The Park");
		}
		summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
	}

/**
 * Handles turning off the "Pay Later" option.
 */
	@FXML
	private void turnOffPayLater() {
		if (!payLaterRadioButton.isSelected())
			payNowRadioButton.setSelected(true);
		else {
			payLaterRadioButton.setSelected(false);
			paymentPane.setVisible(true);
			paymentPane.setDisable(false);
			summaryPayment.setText("Online");
		}
		summaryTotalPrice.setText(df.format(CalculatePrice()) + "₪");
		
	}
	
	/**
	 * Calculates the price of the order based on the entered information.
	 * 
	 * @return The calculated price of the order.
	 */
	private Double CalculatePrice() {
		if (!summaryVisitors.getText().isEmpty() && !idInputOrderVisitTextField.getText().isEmpty()
				&& !summaryVisitors.getText().isEmpty() && !summaryDate.getText().isEmpty()
				&& !summaryDate.getText().equals("null")) {

			int visitorsNumber = 0;
			
			if (!isNumeric(summaryVisitors.getText())) {
				return 0.0;
			} else {
				visitorsNumber = Integer.parseInt(summaryVisitors.getText());
				if (visitorsNumber <= 0) {
					return 0.0;
				}
			}
		
			if (summaryPayment.getText().equals("At The Park")
					&& summaryType.getText().equals(OrderType.GROUP.toString())) {
				GuidePayAtParkCheckOut checkOut = new GuidePayAtParkCheckOut(visitorsNumber);

				return checkOut.getPrice();

			} else if (summaryPayment.getText().equals("Online")
					&& summaryType.getText().equals(OrderType.GROUP.toString())) {
				GuidePrePayCheckOut checkOut = new GuidePrePayCheckOut(visitorsNumber);
				return checkOut.getPrice();
			}
			

			else {
				SoloFamilyOrderCheckOut checkOut = new SoloFamilyOrderCheckOut(visitorsNumber);
				return checkOut.getPrice();
			}

		}

		return (double) GoNatureConstants.FULL_TICKET_PRICE;
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