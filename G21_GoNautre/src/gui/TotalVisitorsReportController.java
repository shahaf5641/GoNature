package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import Controllers.ManagementReportControl;
import alerts.Alerts;
import client.ChatClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GoNatureConstants;
import logic.Order;
import logic.Report;

/**
 * Controller for generating and visualizing a report on total visitors.
 * Supports functionalities for both park managers and department managers.
 */

@SuppressWarnings("unchecked")
public class TotalVisitorsReportController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    
	@FXML
	private Label headerLabel;

	@FXML
	private Label monthLabel;

	@FXML
	private Label individualLabel;

	@FXML
	private Label groupsLabel;

	@FXML
	private Label totalLabel;

	@FXML
	private Button sendToManagerButton;

	@FXML
	private TextArea commentTextArea;

	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	private ArrayList<String> newReportList;
	private static ArrayList<Integer> reportList;
	private int parkID;
	private int numberOfMonth;
	private String comment;
	private int[] daysSolosClean = new int[7];
	private int[] daysGroupClean = new int[7];
	private int[] totalClean = new int[7];
	private boolean chekDepartmentManager = false;
	private ArrayList<Order> solosOrdersUnClean = new ArrayList<Order>();
	private ArrayList<Order> groupsOrdersUnClean = new ArrayList<Order>();
	
	/**
     * Initializes the controller class and prepares the visualization components.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
		getData();
		cleanData();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadBarChat();
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();

	}
	  /**
     * Sets up initial configurations for UI components and event handlers.
     */
	private void init() {
		initLabels();
		commentTextArea.setText(comment);

		if (chekDepartmentManager) {
			commentTextArea.setEditable(false);
			commentTextArea.setPromptText("Park manager comment:");
			sendToManagerButton.setText("         Save Report Locally         ");

			sendToManagerButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					saveReportAsPdf();
					getStage().close();
				}

			});
		} else {
			sendToManagerButton.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		            sendToManagerButton();
		            getStage().close();
				}
			});
		}
	}

	/**
	 * Loads the given data into the bar chart as a new series.
	 * Each element in the data array corresponds to a day of the week, starting with Sunday.
	 * 
	 * @param data An array of integers representing visitor counts for each day of the week.
	 * @param name The name of the series to be added to the bar chart, usually indicating the type of visitors.
	 */

	private void loadDataToChart(int[] data, String name) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(name);
		series.getData().add(new XYChart.Data<>("Sunday", data[0]));
		series.getData().add(new XYChart.Data<>("Monday", data[1]));
		series.getData().add(new XYChart.Data<>("Tuesday", data[2]));
		series.getData().add(new XYChart.Data<>("Wednesday", data[3]));
		series.getData().add(new XYChart.Data<>("Thursday", data[4]));
		series.getData().add(new XYChart.Data<>("Friday", data[5]));
		series.getData().add(new XYChart.Data<>("Saturday", data[6]));
		barChart.getData().add(series);
	}
	/**
	 * Sets up tool tips for each data point in the bar chart to show the number of visitors.
	 * This enhances user interaction by displaying detailed information when hovering over a bar.
	 */
	private void setToolTip() {
		for (XYChart.Series<String, Number> s : barChart.getData()) {
			for (XYChart.Data<String, Number> d : s.getData()) {

				Tooltip.install(d.getNode(), new Tooltip(
						"Number of " + s.getName().trim() + " visitors in " + d.getXValue() + ": " + d.getYValue()));
				
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
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
	    
		WritableImage nodeshot = rootPane.snapshot(new SnapshotParameters(), null);
		String fileName = "Total Visitors Report - park " + parkID + " - month number " + numberOfMonth + ".pdf";
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
	 * Prepares and loads data into the bar chart for visual representation. 
	 * Configures the yAxis range based on the data to ensure all values are properly displayed.
	 */
	private void loadBarChat() {
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound(Arrays.stream(totalClean).max().getAsInt() + 2);
		yAxis.setTickUnit(1);
		xAxis.setCategories(FXCollections.<String>observableArrayList(
				Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")));

		barChart.setBarGap(2);

		loadDataToChart(daysSolosClean, "Solos      ");
		loadDataToChart(daysGroupClean, "Groups     ");
		loadDataToChart(totalClean, "Total      ");
		setToolTip();
}
	
	/**
	 * Handles the action triggered by the "Send to Manager" button click. 
	 * Generates a report object and attempts to add it to the system for managerial review.
	 */

	@FXML
	private void sendToManagerButton() {

		Report r = new Report(0, "Total Visitors", parkID, numberOfMonth, commentTextArea.getText());
		if (ManagementReportControl.InsertReport(r)) {
			new Alerts(AlertType.INFORMATION, "Success", "Success",
					"The report on total visitors has been forwarded to the department manager.").showAndWait();
		} else {
			new Alerts(AlertType.ERROR, "Faild", "Faild", "An error occurred. Please try again later.")
					.showAndWait();
		}

		getStage().close();
	}

/**
 * Initializes labels with the appropriate text, including the month name and totals for individual and group visitors.
 */
	private void initLabels() {
		monthLabel.setText(GoNatureConstants.MONTH_NAMES[numberOfMonth]); 
		newReportList = new ArrayList<>();
		newReportList.add(String.valueOf(numberOfMonth));
		newReportList.add("Total Visitors");
		newReportList.add(String.valueOf(parkID));
		ManagementReportControl.DisplayReport(newReportList);
		reportList = ChatClient.responseFromServer.getSuccessSet();
		individualLabel.setText(String.valueOf(reportList.get(0)));
		groupsLabel.setText(String.valueOf(reportList.get(2)));
		totalLabel.setText(String.valueOf(reportList.get(0) + reportList.get(2)));

	}
	/**
	 * Fetches raw data on solos and groups orders from the server to be cleaned and visualized.
	 */
	private void getData() {
		solosOrdersUnClean = ManagementReportControl.findSoloVisitorsReportOrders(numberOfMonth, parkID);
		groupsOrdersUnClean = ManagementReportControl.findGroupVisitorsReportOrders(numberOfMonth, parkID);
	}


/**
 * Determines the day of the week for a given date string.
 * 
 * @param dateInString The date in "yyyy-MM-dd" format.
 * @return The day of the week as an integer (0 for Sunday, 6 for Saturday).
 */

	private int getNumberInWeek(String dateInString) {
		int dayOfWeek = 0;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			return (dayOfWeek - 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public void setMonthNumber(int month) {
		this.numberOfMonth = month;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setParkID(int parkID) {
		this.parkID = parkID;
	}

	private Stage getStage() {
		return (Stage) monthLabel.getScene().getWindow();
	}

	

	public void setIsDepManager(boolean IsDepManager) {
		this.chekDepartmentManager = IsDepManager;

	}
	/**
	 * Cleans the raw data by summarizing the total number of participants for each day of the week.
	 */
	private void cleanData() {
		for (Order order : solosOrdersUnClean) {
			String date = order.getOrderDate();
			int dayInWeek = getNumberInWeek(date);
			daysSolosClean[dayInWeek] += order.getNumberOfParticipants();
			totalClean[dayInWeek] += order.getNumberOfParticipants();
		}

		for (Order order : groupsOrdersUnClean) {
			String date = order.getOrderDate();
			int dayInWeek = getNumberInWeek(date);
			daysGroupClean[dayInWeek] += order.getNumberOfParticipants();
			totalClean[dayInWeek] += order.getNumberOfParticipants();
		}
	}

}
