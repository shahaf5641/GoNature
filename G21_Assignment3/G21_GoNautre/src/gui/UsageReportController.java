package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import Controllers.ParkControl;
import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Report;

/**
 * This class is the GUI controller of UsageReport.fxml
 * It handles all the JavaFx nodes events.
 * 
 * In the screen we can see the dates that the park was full or not.
 *
 */
public class UsageReportController implements Initializable {

    @FXML
    private StackPane rootPane;
    
	@FXML
	private Label headerLabel;

	@FXML
	private Label monthLabel;

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea commentTextArea;

	@FXML
	private javafx.scene.control.Button sendToManagerBtn;

	private int parkID;
	private int monthNumber;
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private String comment;
	private boolean isDepManager = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();
	}

	private void init() {
		Locale.setDefault(Locale.ENGLISH);
		initLabels();

		if (isDepManager) {
			commentTextArea.setEditable(false);
			commentTextArea.setPromptText("Park manager comment:");
			sendToManagerBtn.setText("         Save Report Locally         ");

			sendToManagerBtn.setOnAction(event -> {
				saveReportAsPdf();
				getStage().close();
			});
		} else {
			sendToManagerBtn.setOnAction(event -> {
				sendToManagerBtn();
				getStage().close();
			});
		}
	}

	private void initLabels() {
		monthLabel.setText(GoNatureFinals.MONTHS[monthNumber]); // Set the name of the month
		commentTextArea.setText(comment);
	}

	private ArrayList<String> isParkIsFullAtDate(int year, int monthNumber, int day) {
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (monthNumber > currentMonth)
			year--;
		
		String date = year + "-" + monthNumber + "-" + day;
		return ParkControl.isParkIsFullAtDate(date, String.valueOf(parkID));
	}
	
	private void saveReportAsPdf() {
		// Implementation of saving the report as PDF
	}

	/**
	 * Handle 'sendToManagerBtn' button
	 * On click it sends a request to the server to add the report to the database
	 */
	@FXML
	private void sendToManagerBtn() {

		Report r = new Report(0, "Usage", parkID, monthNumber, commentTextArea.getText());
		if (ReportsControl.addReport(r)) {
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"Usage report has been sent to department manager.").showAndWait();
			getStage().close();
		} else {
			new CustomAlerts(AlertType.ERROR, "Faild", "Faild", "Something went wrong. Please try again late.")
					.showAndWait();
		}
	}

	private Stage getStage() {
		return (Stage) monthLabel.getScene().getWindow();
	}

	/**
	 * Setter for class variable monthNumber
	 * 
	 * @param month The month number
	 */
	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	/**
	 * Setter for class variable comment
	 * 
	 * @param comment The park manager comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Setter for class variable parkID
	 * 
	 * @param parkID The park id
	 */
	public void setParkID(int parkID) {
		this.parkID = parkID;
	}

	/**
	 * Setter for class variable isDepManager
	 * 
	 * @param b true if opened from department manager screen
	 */
	public void setIsDepManager(boolean b) {
		this.isDepManager = b;
	}
}
