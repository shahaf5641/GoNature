package gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Controllers.ReportsControl;
import alerts.CustomAlerts;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Order;
import logic.Report;

public class TotalVisitorsReportController implements Initializable {

	@FXML
	private Button sendToManagerBtn;
	
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
    private int monthNumber;
    private String comment;
    private boolean isDepManager = false;

    /* Orders distributed by order type */
    private ArrayList<Order> solosOrdersUnClean = new ArrayList<>();
    private ArrayList<Order> groupsOrdersUnClean = new ArrayList<>();

    /*
     * Numbers of visitors distributed by days
     * 0 - Sunday
     * 6 - Saturday
     */
    private int[] daysSolosClean = new int[7];
    private int[] daysGroupClean = new int[7];
    private int[] totalClean = new int[7];

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
        getData();
        cleanData();
        loadBarChart();
        
    }

    private void init() {
        initLabels();
        commentTextArea.setText(comment);
    }

    private void loadBarChart() {
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(IntStream.of(totalClean).max().getAsInt() + 2);
        yAxis.setTickUnit(1);
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")));

        barChart.setBarGap(2);

        loadDataToChart(daysSolosClean, "Solos      ");
        loadDataToChart(daysGroupClean, "Groups     ");
        loadDataToChart(totalClean, "Total      ");
    }

    private void loadDataToChart(int[] data, String name) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (int i = 0; i < data.length; i++) {
            series.getData().add(new XYChart.Data<>(xAxis.getCategories().get(i), data[i]));
        }
        barChart.getData().add(series);
    }

    private void saveReportAsPdf() {
        String fileName = "Total Visitors Report - park " + parkID + " - month number " + monthNumber + ".pdf";
        try (PDDocument doc = new PDDocument(); OutputStream os = new FileOutputStream(System.getProperty("user.home") + "/Desktop/reports/" + fileName)) {
            WritableImage nodeshot = rootPane.snapshot(new SnapshotParameters(), null);
            File file = new File("test.png");
            javax.imageio.ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
            PDPage page = new PDPage();
            PDImageXObject pdimage = PDImageXObject.createFromFile("test.png", doc);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.drawImage(pdimage, 50, 100, 500, 600);
            content.close();
            doc.addPage(page);
            doc.save(os);
            file.delete();
            System.out.println("Report saved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to create PDF: " + e.getMessage());
        }
    }

	@FXML
	private void sendToManagerBtn() {

		Report r = new Report(0, "Usage", parkID, monthNumber, commentTextArea.getText());
		if (ReportsControl.addReport(r)) {
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"Total Visitors report has been sent to department manager.").showAndWait();
		} else {
			new CustomAlerts(AlertType.ERROR, "Faild", "Faild", "Something went wrong. Please try again late.")
					.showAndWait();
		}
	}

    @SuppressWarnings("unchecked")
	private void initLabels() {
        monthLabel.setText(GoNatureFinals.MONTHS[monthNumber]); // set the name of the month
        newReportList = new ArrayList<>();
        newReportList.add(String.valueOf(monthNumber));
        newReportList.add("Total Visitors");
        newReportList.add(String.valueOf(parkID));
        ReportsControl.showReport(newReportList);
        reportList = ChatClient.responseFromServer.getResultSet();
        individualLabel.setText(String.valueOf(reportList.get(0)));
        groupsLabel.setText(String.valueOf(reportList.get(2)));
        totalLabel.setText(String.valueOf(reportList.get(0) + reportList.get(1) + reportList.get(2)));
    }

    private void getData() {
        solosOrdersUnClean = ReportsControl.getSolosOrdersVisitorsReport(monthNumber, parkID);
        groupsOrdersUnClean = ReportsControl.getGroupOrdersVisitorsReport(monthNumber, parkID);
    }

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

    private int getNumberInWeek(String dateStr) {
        int dayOfWeek = 0;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
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
        this.monthNumber = month;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private void closeStage() {
        Stage stage = (Stage) monthLabel.getScene().getWindow();
        stage.close();
    }

    public void setParkID(int parkID) {
        this.parkID = parkID;
    }

    public void setIsDepManager(boolean b) {
        this.isDepManager = b;
    }
}
