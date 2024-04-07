package gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Controllers.ParkControl;
import Controllers.ManagementReportControl;
import alerts.Alerts;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Report;

/**
 * Controller for the Usage Report page.
 * This controller manages the interaction between the user and the usage report interface,
 * including generating reports, sending comments to managers, and exporting reports.
 */
public class UsageReportController implements Initializable {
	@FXML
	private Label headerLabel;


    @FXML
    private TableView<String> isNotFullDatesTableView;

	@FXML
	private Label monthLabel;

    @FXML
    private TableColumn<String, String> dateTableColumn;
    
    @FXML
    private StackPane rootStackPane;
    

	@FXML
	private AnchorPane root;

	@FXML
	private TextArea commentTextArea;

	@FXML
	private javafx.scene.control.Button sendToManagerButton;
	
	private int monthNumber;
	private int parkID;
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private String comment;
	private boolean isDepManager = false;
	  /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    Locale.setDefault(Locale.ENGLISH);
	    dateTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
	    init();

	}
	/**
     * Initializes labels and loads the usage report data.
     */

	private void init() {
		Locale.setDefault(Locale.ENGLISH);
		initLabels();
	    CreateAUsageReport();

		if (isDepManager) {
			commentTextArea.setEditable(false);
			commentTextArea.setPromptText("Park manager comment:");
			sendToManagerButton.setText("         Save Report Locally         ");

			sendToManagerButton.setOnAction(event -> {
				saveReportAsPdf();
				getStage().close();
			});
		} else {
			sendToManagerButton.setOnAction(event -> {
				sendToManagerButton();
				getStage().close();
			});
		}
	}
	 /**
     * Creates and displays a usage report for the selected park and month.
     */
	public void CreateAUsageReport() {
	    isNotFullDatesTableView.getItems().clear();
	    int daysInMonth = getCalendarDaysInMonth(year, monthNumber);
    	int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (monthNumber > currentMonth)
			year--;
	    for (int day = 1; day <= daysInMonth; day++) {
	        ArrayList<String> comments = isParkIsFullAtDate(year, monthNumber, day);
	        if (comments.get(0).equals("notFull")) {
	            String date = String.format("%d-%02d-%02d", year, monthNumber, day);
	            isNotFullDatesTableView.getItems().add(date);
	        }
	    }
	}

	/**
     * Initializes the labels with data related to the report.
     */
	private void initLabels() {
		monthLabel.setText(GoNatureConstants.MONTH_NAMES[monthNumber]); 
		commentTextArea.setText(comment);
	}
	  /**
     * Checks if the park was full on a specific date.
     *
     * @param year Year of the date to check.
     * @param monthNumber Month of the date to check.
     * @param day Day of the date to check.
     * @return A list of comments related to the park's status on the given date.
     */
	private ArrayList<String> isParkIsFullAtDate(int year, int monthNumber, int day) {
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (monthNumber > currentMonth)
			year--;
		
		String date = year + "-" + monthNumber + "-" + day;
		return ParkControl.checkParkFullCapacityAtDate(date, String.valueOf(parkID));
	}
	/**
	 * Saves the current state of the bar chart as a PDF document.
	 * The PDF is saved to a 'reports' directory on the user's desktop.
	 */
	private void saveReportAsPdf() {
		File directory = new File(System.getProperty("user.home") + "/Desktop/reports/");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    
		WritableImage nodeshot = root.snapshot(new SnapshotParameters(), null);
		String fileName = "Usage Report - park " + parkID + " - month number " + monthNumber + ".pdf";
		File file = new File("test.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		PDImageXObject pdimage;
		PDPageContentStream content;
		try {
			pdimage = PDImageXObject.createFromFile("test.png", doc);
			content = new PDPageContentStream(doc, page);
			content.drawImage(pdimage, 50, 100, 500, 600);
			content.close();
			doc.addPage(page);
			doc.save(System.getProperty("user.home") + "/Desktop/reports/" +fileName);
			doc.close();
			file.delete();
			new Alerts(AlertType.INFORMATION, "Success", "Success",
					"The report has been stored on your desktop within the directory named \"reports\".").showAndWait();
		} catch (IOException ex) {
			System.out.println("faild to create pdf");
			ex.printStackTrace();
		}

	}
	

	 /**
     * Sends the usage report to the department manager with comments.
     */
	@FXML
	private void sendToManagerButton() {

		Report r = new Report(0, "Usage", parkID, monthNumber, commentTextArea.getText());
		if (ManagementReportControl.InsertReport(r)) {
			new Alerts(AlertType.INFORMATION, "Success", "Success",
					"Usage report has been sent to department manager.").showAndWait();
			getStage().close();
		} else {
			new Alerts(AlertType.ERROR, "Faild", "Faild", "An error occurred. Please try again later.")
					.showAndWait();
		}
	}
	
	
	/**
     * Calculates the number of days in a given month and year.
     *
     * @param year The year to check.
     * @param month The month to check.
     * @return The number of days in the specified month and year.
     */ 
	
	private int getCalendarDaysInMonth(int year, int month) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month - 1, 1); 
	    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
	}

	private Stage getStage() {
		return (Stage) monthLabel.getScene().getWindow();
	}

	
	public void setMonthNumber(int month) {
		this.monthNumber = month;
	}

	public void setIsDepManager(boolean b) {
		this.isDepManager = b;
	}

	
	public void setComment(String comment) {
		this.comment = comment;
	}


	public void setParkID(int parkID) {
		this.parkID = parkID;
	}


}