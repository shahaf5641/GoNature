package gui;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import Controllers.ParkControl;
import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<String> notFullDatesTable;

    @FXML
    private TableColumn<String, String> dateColumn;
    
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
	    Locale.setDefault(Locale.ENGLISH);
	    dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
	    init();

	}


	private void init() {
		Locale.setDefault(Locale.ENGLISH);
		initLabels();
	    generateUsageReport();

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
	
	public void generateUsageReport() {
	    notFullDatesTable.getItems().clear();
	    int daysInMonth = getDaysInMonth(year, monthNumber);
    	int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (monthNumber > currentMonth)
			year--;
	    for (int day = 1; day <= daysInMonth; day++) {
	        ArrayList<String> comments = isParkIsFullAtDate(year, monthNumber, day);
	        if (comments.get(0).equals("notFull")) {
	            String date = String.format("%d-%02d-%02d", year, monthNumber, day);
	            notFullDatesTable.getItems().add(date);
	        }
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
	
	// Helper method to get the number of days in a month
	private int getDaysInMonth(int year, int month) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month - 1, 1); // Set the calendar to the first day of the month
	    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // Get the maximum days in the month
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