package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Controllers.ParkControl;
import Controllers.ReportsControl;
import alerts.CustomAlerts;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.GoNatureFinals;
import logic.Park;

/**
 * counts pending orders with passed date and cancelled orders for each park.
 * presents it in report and shows in total how many orders were cancelled
 */
public class CancelsReportController implements Initializable{
	
	@FXML
	private TextField startDatetext;
	
	@FXML
	private TextField endDatetext;
	
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label headerLabel;

    @FXML
    private Label MosesCancelLabel;

    @FXML
    private Label PheonixCancelsLabel;

    @FXML
    private Label YosemiteCancelsLabel;

    @FXML
    private Label CancelsTotalLabel;
    
    @FXML
    private Label MosesNotArrivedLabel;
    
    @FXML
    private Label PheonixNotArrivedLabel;
    
    @FXML
    private Label YosemiteNotArrivedLabel;
    
    @FXML
    private Label NotArrivedTotalLabel;
    
    @FXML
    private Label MosesMedianCLabel;
    
    @FXML
    private Label PheonixMedianCLabel;
    
    @FXML
    private Label YosemiteMedianCLabel;
    
    @FXML
    private Label TotalMedianCLabel;
    
    @FXML
    private Label MosesAverageCLabel;
    
    @FXML
    private Label PheonixAverageCLabel;
    
    @FXML
    private Label YosemiteAverageCLabel;
    
    @FXML
    private Label TotalAverageCLabel;
    
    @FXML
    private Label MosesMedianALabel;
    
    @FXML
    private Label PheonixMedianALabel;
    
    @FXML
    private Label YosemiteMedianALabel;
    
    @FXML
    private Label TotalMedianALabel;
    
    @FXML
    private Label MosesAverageALabel;
    
    @FXML
    private Label PheonixAverageALabel;
    
    @FXML
    private Label YosemiteAverageALabel;
    
    @FXML
    private Label TotalAverageALabel;
    
    @FXML
    private ComboBox<String> parkComboBox;
    
    private String startdate, enddate;
    
    private int cancels=0,cancelsPark1=0,cancelsPark2=0,cancelsPark3=0, notarrived= 0, notarrivedPark1=0, notarrivedPark2=0, notarrivedPark3=0;
    
    private ArrayList<Integer> mosesCancelesDay = new ArrayList<>();
    private ArrayList<Integer> pheonixCancelesDay = new ArrayList<>();
    private ArrayList<Integer> yosemiteCancelesDay = new ArrayList<>();
    private ArrayList<Integer> totalCancelesDay = new ArrayList<>();
    private ArrayList<Integer> mosesNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> pheonixNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> yosemiteNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> totalNotArrivedDay = new ArrayList<>();

    		
    
    private double mosesAvgC, pheonixAvgC, yosemiteAvgC, totalAvgC , mosesAvgA, pheonixAvgA, yosemiteAvgA, totalAvgA;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initLabels();
		initComboBoxs();
	}
		
	
	
	private void initLabels() {
		MosesCancelLabel.setText("0");
		PheonixCancelsLabel.setText("0");
		YosemiteCancelsLabel.setText("0");
		CancelsTotalLabel.setText("0");
		MosesNotArrivedLabel.setText("0");
		PheonixNotArrivedLabel.setText("0");
		YosemiteNotArrivedLabel.setText("0");
		NotArrivedTotalLabel.setText("0");
		MosesMedianCLabel.setText("0");
		PheonixMedianCLabel.setText("0");
		YosemiteMedianCLabel.setText("0");
		TotalMedianCLabel.setText("0");
		MosesAverageCLabel.setText("0");
		PheonixAverageCLabel.setText("0");
		YosemiteAverageCLabel.setText("0");
		TotalAverageCLabel.setText("0");
		MosesMedianALabel.setText("0");
		PheonixMedianALabel.setText("0");
		YosemiteMedianALabel.setText("0");
		TotalMedianALabel.setText("0");
		MosesAverageALabel.setText("0");
		PheonixAverageALabel.setText("0");
		YosemiteAverageALabel.setText("0");
		TotalAverageALabel.setText("0");
		
		
		
		
		//To be continued
	}
	
	@FXML
	private void viewDetailsButton() {
	    // Get the start date and end date from the text fields
	    SetStartDate(startDatetext.getText());
	    SetEndDate(endDatetext.getText());

	    // Check if both start date and end date are not empty
	    if (startdate.isEmpty() || enddate.isEmpty()) {
	        new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill all the fields").showAndWait();
	        return;
	    }

	    // Get the selected park from the combo box
	    String selectedPark = parkComboBox.getValue();
	    if (selectedPark == null) {
	        new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose a park").showAndWait();
	        return;
	    }

	    // Validate the dates
	    if (!isValidDates(startdate, enddate)) {
	        new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Invalid dates").showAndWait();
	        return;
	    }
	    
	    //Passed all checks
	    
	    getData();
	    
	    // Load data based on the selected park
	    if (selectedPark.equals("Show All")) {
	        // Load details for all parks
	    	loadAllParameters();
	    } else {
	    	Park park = ParkControl.getParkByName(selectedPark);
	    	loadParameters(park);
	    }

	}

	@FXML
	private void saveReportAsPdf() {
		File directory = new File(System.getProperty("user.home") + "/Desktop/reports/");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
	    
		WritableImage nodeshot = rootPane.snapshot(new SnapshotParameters(), null);
		String fileName = "Cancels Report " + startdate + " - " + enddate + ".pdf";
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
			new CustomAlerts(AlertType.INFORMATION, "Success", "Success",
					"The report was saved in your desktop under reports folder").showAndWait();
		} catch (IOException ex) {
			System.out.println("faild to create pdf");
			ex.printStackTrace();
		}
		
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();

	}
	
	
    private void initComboBoxs() {
        /* Set parks combo box to load dynamically from database */
        ArrayList<String> parksNames = ParkControl.getParksNames();
        if (parksNames != null) {
        	parkComboBox.getItems().add("Show All");
            parkComboBox.getItems().addAll(parksNames);
        }
    }
    
    
    private void loadAllParameters() {
    	DecimalFormat df = new DecimalFormat("#.##");
        MosesCancelLabel.setText(String.valueOf(cancelsPark1));
        MosesNotArrivedLabel.setText(String.valueOf(notarrivedPark1));
        MosesMedianCLabel.setText(String.valueOf(calculateMedian(mosesCancelesDay)));
        MosesAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
        MosesMedianALabel.setText(String.valueOf(calculateMedian(mosesNotArrivedDay)));
        MosesAverageALabel.setText(df.format(mosesAvgA)); // Apply DecimalFormat
        PheonixCancelsLabel.setText(String.valueOf(cancelsPark2));
        PheonixNotArrivedLabel.setText(String.valueOf(notarrivedPark2));
        PheonixMedianCLabel.setText(String.valueOf(calculateMedian(pheonixCancelesDay)));
        PheonixAverageCLabel.setText(df.format(pheonixAvgC)); // Apply DecimalFormat
        PheonixMedianALabel.setText(String.valueOf(calculateMedian(pheonixNotArrivedDay)));
        PheonixAverageALabel.setText(df.format(pheonixAvgA)); // Apply DecimalFormat
        YosemiteCancelsLabel.setText(String.valueOf(cancelsPark3));
        YosemiteNotArrivedLabel.setText(String.valueOf(notarrivedPark3));
        YosemiteMedianCLabel.setText(String.valueOf(calculateMedian(yosemiteCancelesDay)));
        YosemiteAverageCLabel.setText(df.format(yosemiteAvgC)); // Apply DecimalFormat
        YosemiteMedianALabel.setText(String.valueOf(calculateMedian(yosemiteNotArrivedDay)));
        YosemiteAverageALabel.setText(df.format(yosemiteAvgA)); // Apply DecimalFormat
        //Totals
        CancelsTotalLabel.setText(String.valueOf(cancels));
        NotArrivedTotalLabel.setText(String.valueOf(notarrived));
        TotalMedianCLabel.setText(String.valueOf(calculateMedian(totalCancelesDay)));
        TotalMedianALabel.setText(String.valueOf(calculateMedian(totalNotArrivedDay)));
        TotalAverageCLabel.setText(df.format(totalAvgC)); // Apply DecimalFormat
        TotalAverageALabel.setText(df.format(totalAvgA)); // Apply DecimalFormat
    }
    
    
    private void loadParameters(Park park) {
        if (park != null)
            setLabels(park);
    }

    
    
    
    private void setLabels(Park park) {
        DecimalFormat df = new DecimalFormat("#.##");
        switch (park.getParkName()) {
            case "Moses":
                MosesCancelLabel.setText(String.valueOf(cancelsPark1));
                MosesNotArrivedLabel.setText(String.valueOf(notarrivedPark1));
                CancelsTotalLabel.setText(String.valueOf(cancelsPark1));
                NotArrivedTotalLabel.setText(String.valueOf(notarrivedPark1));
                MosesMedianCLabel.setText(String.valueOf(calculateMedian(mosesCancelesDay)));
                MosesAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
                MosesMedianALabel.setText(String.valueOf(calculateMedian(mosesNotArrivedDay)));
                MosesAverageALabel.setText(df.format(mosesAvgA)); // Apply DecimalFormat
                TotalMedianCLabel.setText(String.valueOf(calculateMedian(mosesCancelesDay)));
                TotalMedianALabel.setText(String.valueOf(calculateMedian(mosesNotArrivedDay)));
                TotalAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
                TotalAverageALabel.setText(df.format(mosesAvgA)); // Apply DecimalFormat
                PheonixCancelsLabel.setText("0");
                PheonixNotArrivedLabel.setText("0");
                PheonixMedianCLabel.setText("0");
                PheonixMedianALabel.setText("0");
                PheonixAverageCLabel.setText("0");
                PheonixAverageALabel.setText("0");
                YosemiteCancelsLabel.setText("0");
                YosemiteNotArrivedLabel.setText("0");
                YosemiteMedianCLabel.setText("0");
                YosemiteMedianALabel.setText("0");
                YosemiteAverageCLabel.setText("0");
                YosemiteAverageALabel.setText("0");
                break;
            case "Pheonix":
                PheonixCancelsLabel.setText(String.valueOf(cancelsPark2));
                PheonixNotArrivedLabel.setText(String.valueOf(notarrivedPark2));
                CancelsTotalLabel.setText(String.valueOf(cancelsPark2));
                NotArrivedTotalLabel.setText(String.valueOf(notarrivedPark2));
                PheonixMedianCLabel.setText(String.valueOf(calculateMedian(pheonixCancelesDay)));
                PheonixAverageCLabel.setText(df.format(pheonixAvgC)); // Apply DecimalFormat
                PheonixMedianALabel.setText(String.valueOf(calculateMedian(pheonixNotArrivedDay)));
                PheonixAverageALabel.setText(df.format(pheonixAvgA)); // Apply DecimalFormat
                TotalMedianCLabel.setText(String.valueOf(calculateMedian(pheonixCancelesDay)));
                TotalMedianALabel.setText(String.valueOf(calculateMedian(pheonixNotArrivedDay)));
                TotalAverageCLabel.setText(df.format(pheonixAvgC)); // Apply DecimalFormat
                TotalAverageALabel.setText(df.format(pheonixAvgA)); // Apply DecimalFormat
                MosesCancelLabel.setText("0");
                MosesNotArrivedLabel.setText("0");
                MosesMedianCLabel.setText("0");
                MosesMedianALabel.setText("0");
                MosesAverageCLabel.setText("0");
                MosesAverageALabel.setText("0");
                YosemiteCancelsLabel.setText("0");
                YosemiteNotArrivedLabel.setText("0");
                YosemiteMedianCLabel.setText("0");
                YosemiteMedianALabel.setText("0");
                YosemiteAverageCLabel.setText("0");
                YosemiteAverageALabel.setText("0");
                break;
            case "Yosemite":
                YosemiteCancelsLabel.setText(String.valueOf(cancelsPark3));
                YosemiteNotArrivedLabel.setText(String.valueOf(notarrivedPark3));
                CancelsTotalLabel.setText(String.valueOf(cancelsPark3));
                NotArrivedTotalLabel.setText(String.valueOf(notarrivedPark3));
                YosemiteMedianCLabel.setText(String.valueOf(calculateMedian(yosemiteCancelesDay)));
                YosemiteAverageCLabel.setText(df.format(yosemiteAvgC)); // Apply DecimalFormat
                YosemiteMedianALabel.setText(String.valueOf(calculateMedian(yosemiteNotArrivedDay)));
                YosemiteAverageALabel.setText(df.format(yosemiteAvgA)); // Apply DecimalFormat
                TotalMedianCLabel.setText(String.valueOf(calculateMedian(yosemiteCancelesDay)));
                TotalMedianALabel.setText(String.valueOf(calculateMedian(yosemiteNotArrivedDay)));
                TotalAverageCLabel.setText(df.format(yosemiteAvgC)); // Apply DecimalFormat
                TotalAverageALabel.setText(df.format(yosemiteAvgA)); // Apply DecimalFormat
                PheonixCancelsLabel.setText("0");
                PheonixNotArrivedLabel.setText("0");
                PheonixMedianCLabel.setText("0");
                PheonixMedianALabel.setText("0");
                PheonixAverageCLabel.setText("0");
                PheonixAverageALabel.setText("0");
                MosesCancelLabel.setText("0");
                MosesNotArrivedLabel.setText("0");
                MosesMedianCLabel.setText("0");
                MosesMedianALabel.setText("0");
                MosesAverageCLabel.setText("0");
                MosesAverageALabel.setText("0");
                break;
            default:
        }
    }

            
    
    
    private int calculateDaysDifference(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
    
	
	private void getData() {
		/*Moses*/
		cancelsPark1=ReportsControl.getParkCancels("1",startdate,enddate).get(0);			//get cancels for Moses
		notarrivedPark1=ReportsControl.getParkNotArrived("1",startdate,enddate).get(0);//get not arrived for Moses
		/*Pheonix*/
		cancelsPark2=ReportsControl.getParkCancels("2",startdate,enddate).get(0);			//get cancels for Pheonix
		notarrivedPark2=ReportsControl.getParkNotArrived("2",startdate,enddate).get(0);//get not arrived for for Pheonix
		/*Yosemite*/
		cancelsPark3=ReportsControl.getParkCancels("3",startdate,enddate).get(0);			//get cancels for Yosemite
		notarrivedPark3=ReportsControl.getParkNotArrived("3",startdate,enddate).get(0);//get not arrived for for Yosemite
		//Total
		cancels=cancelsPark1+cancelsPark2+cancelsPark3;
		notarrived = notarrivedPark1+notarrivedPark2+notarrivedPark3;
		int daysBetweenDates = calculateDaysDifference(startdate, enddate);
		LocalDate startDateLD = LocalDate.parse(startdate); // Convert startdate to LocalDate
		//Build lists of days for each park and report
		for (int i = 0; i < daysBetweenDates; i++) {
		     LocalDate currentDate = startDateLD.plusDays(i); // Increment startDateLD by i days
		     String currentDateStr = currentDate.toString(); // Convert currentDate back to String
		     mosesCancelesDay.add(ReportsControl.getParkCancels("1",currentDateStr,currentDateStr).get(0));
		     mosesNotArrivedDay.add(ReportsControl.getParkNotArrived("1",currentDateStr,currentDateStr).get(0));
		     pheonixCancelesDay.add(ReportsControl.getParkCancels("2",currentDateStr,currentDateStr).get(0));
		     pheonixNotArrivedDay.add(ReportsControl.getParkNotArrived("2",currentDateStr,currentDateStr).get(0));
		     yosemiteCancelesDay.add(ReportsControl.getParkCancels("3",currentDateStr,currentDateStr).get(0));
		     yosemiteNotArrivedDay.add(ReportsControl.getParkNotArrived("3",currentDateStr,currentDateStr).get(0));
		 }
	     totalCancelesDay.addAll(mosesCancelesDay);
	     totalCancelesDay.addAll(pheonixCancelesDay);
	     totalCancelesDay.addAll(yosemiteCancelesDay);
	     totalNotArrivedDay.addAll(mosesNotArrivedDay);
	     totalNotArrivedDay.addAll(pheonixNotArrivedDay);
	     totalNotArrivedDay.addAll(yosemiteNotArrivedDay);
	     //Calculate Cancels and Not Arrived Average
	     mosesAvgC = (double) cancelsPark1/daysBetweenDates;
	     pheonixAvgC = (double) cancelsPark2/daysBetweenDates;
	     yosemiteAvgC = (double) cancelsPark3/daysBetweenDates;
	     totalAvgC = (double) cancels/daysBetweenDates;
	     mosesAvgA = (double) notarrivedPark1/daysBetweenDates;
	     pheonixAvgA = (double) notarrivedPark2/daysBetweenDates;
	     yosemiteAvgA = (double) notarrivedPark3/daysBetweenDates;
	     totalAvgA = (double) notarrived/daysBetweenDates;
	}
	
	
    private int calculateMedian(ArrayList<Integer> values) {
        Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2;
        } else {
            return values.get(size / 2);
        }
    }


	
	//Need to comment
    private boolean isValidDates(String startDate, String endDate) {
        int res = startDate.compareTo(endDate);
        // Start date is after end date
        if (res > 0) {
            return false;
        }
        //Format
        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(startDate, formatter);
            LocalDate.parse(endDate, formatter);
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
        // Extract current year from local time
        int currentYear = LocalDate.now().getYear();
        
        
        // Extract years from start and end dates
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        
        // Check if years are valid
        if (startYear > currentYear || endYear > currentYear || endYear < 2000 || startYear < 2000) {
            return false;
        }
        return true;
    }
	
	/**
	 * Setter for class variable stardate
	 * 
	 * @param startdate The startdate
	 */
	public void SetStartDate(String startdate){
		this.startdate = startdate;	
	}
	
	/**
	 * Setter for class variable enddate
	 * 
	 * @param enddate The enddate
	 */
	
	public void SetEndDate(String enddate){
		this.enddate = enddate;	
	}


}