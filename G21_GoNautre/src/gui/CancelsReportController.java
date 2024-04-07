package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import Controllers.ParkControl;
import Controllers.ManagementReportControl;
import alerts.Alerts;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.Park;


public class CancelsReportController implements Initializable{
	
	@FXML
	private TextField startDatetext;
	
	@FXML
	private TextField endDatetext;
	
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private StackPane LoadingPane;
    
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
    
	@FXML
	private BarChart<String, Number> BarChartBetween;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;
    private String startdate, enddate;
    
    private int cancels=0,cancelsPark1=0,cancelsPark2=0,cancelsPark3=0, notarrived= 0, notarrivedPark1=0, notarrivedPark2=0, notarrivedPark3=0,AmountOfDaysBetweenDates = 0;
    
    private ArrayList<Integer> mosesCancelesDay = new ArrayList<>();
    private ArrayList<Integer> pheonixCancelesDay = new ArrayList<>();
    private ArrayList<Integer> yosemiteCancelesDay = new ArrayList<>();
    private ArrayList<Integer> totalCancelesDay = new ArrayList<>();
    private ArrayList<Integer> mosesNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> pheonixNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> yosemiteNotArrivedDay = new ArrayList<>();
    private ArrayList<Integer> totalNotArrivedDay = new ArrayList<>();
    private ArrayList<String> datesBetween = new ArrayList<>();
    private ObservableList<String> observableCategories = FXCollections.observableArrayList();
    		
    
    private double mosesAvgC, pheonixAvgC, yosemiteAvgC, totalAvgC , mosesAvgA, pheonixAvgA, yosemiteAvgA, totalAvgA;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initLabels();
		initComboBoxs();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			}
		}));
		timeline.setCycleCount(1);
		timeline.play();
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
	}
	
	@FXML
	private void viewDetailsButton() {
	    ClearData();
	    SetStartDate(startDatetext.getText());
	    SetEndDate(endDatetext.getText());
	    startDatetext.clear();
	    endDatetext.clear();

	    String selectedPark = parkComboBox.getValue();
	    if (selectedPark == null) {
	        new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please choose a park").showAndWait();
	        return;
	    }
	    

	    if (!isValidDates(startdate, enddate)) {
	        return;
	    }

	    datesBetween = getDatesBetween(startdate, enddate);

	   
	    FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("LoadingScreen.fxml"));
	    Parent loadingRoot;
	    try {
	        loadingRoot = loadingLoader.load();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }

	    // Create a new stage for the loading screen
	    Stage loadingStage = new Stage();
	    loadingStage.initStyle(StageStyle.UNDECORATED);
	    loadingStage.initModality(Modality.APPLICATION_MODAL);
	    loadingStage.initOwner(rootPane.getScene().getWindow()); // Assuming rootPane is the parent of your main FXML elements
	    loadingStage.setScene(new Scene(loadingRoot));

	    // Show the loading screen
	    loadingStage.show();

	    // Disable user interaction with the main FXML elements
	    rootPane.setDisable(true);
	    BarChartBetween.getData().clear();

	 // Start the background task
	    Task<Void> task = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	            getData();
	            BarChartBetween.getData().clear();
	        	if (selectedPark.equals("Show All"))
	        	{
		            Platform.runLater(() -> loadAllParameters());
		            Platform.runLater(() -> loadBarChart());
		            Platform.runLater(() -> loadDataToChart(datesBetween, totalCancelesDay, "TotalCancels    "));
		            Platform.runLater(() -> loadDataToChart(datesBetween, totalNotArrivedDay, "TotalNotArrived    "));
		            Platform.runLater(() -> setToolTip());
	        	}
	        	else
	        	{
	        	    Park park = ParkControl.findParkByName(selectedPark);
	        		Platform.runLater(() -> loadParameters(park));
	        	}
	            return null;
	        }
	    };

	    task.setOnSucceeded(event -> {
	        // Close loading screen and enable user interaction with main FXML elements
	        loadingStage.close();
	        rootPane.setDisable(false);
	    });

	    task.setOnFailed(event -> {
	        // Handle task failure
	        loadingStage.close();
	        rootPane.setDisable(false);
	        new Alerts(AlertType.ERROR, "Error", "Error", "Failed to load details").showAndWait();
	    });

	    new Thread(task).start();
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
			new Alerts(AlertType.INFORMATION, "Success", "Success",
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
        ArrayList<String> parksNames = ParkControl.findParksNames();
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
        MosesAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
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
                MosesMedianALabel.setText(String.valueOf(calculateMedian(mosesNotArrivedDay)));
                MosesAverageALabel.setText(df.format(mosesAvgA)); // Apply DecimalFormat
                TotalMedianCLabel.setText(String.valueOf(calculateMedian(mosesCancelesDay)));
                TotalMedianALabel.setText(String.valueOf(calculateMedian(mosesNotArrivedDay)));
                TotalAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
                TotalAverageALabel.setText(df.format(mosesAvgA)); // Apply DecimalFormat
                MosesAverageCLabel.setText(df.format(mosesAvgC)); // Apply DecimalFormat
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
                //Graph
                //BarChartBetween.getData().clear();
    	    	loadBarChart();
    			loadDataToChart(datesBetween, mosesCancelesDay, "Cancels    ");
    			loadDataToChart(datesBetween, mosesNotArrivedDay, "NotArrived    ");
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
                //Graph
                //BarChartBetween.getData().clear();
    	    	loadBarChart();
    			loadDataToChart(datesBetween, pheonixCancelesDay, "Cancels    ");
    			loadDataToChart(datesBetween, pheonixNotArrivedDay, "NotArrived    ");
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
                //Graph
                //BarChartBetween.getData().clear();
    	    	loadBarChart();
    			loadDataToChart(datesBetween, yosemiteCancelesDay, "Cancels    ");
    			loadDataToChart(datesBetween, yosemiteNotArrivedDay, "NotArrived    ");
                break;
            default:
    			
        }
        setToolTip();
    }
    private int calculateDaysDifference(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
    
	
	private void getData() {
		//Moses
		cancelsPark1=ManagementReportControl.findParkCancels("1",startdate,enddate).get(0);			//get cancels for Moses
		notarrivedPark1=ManagementReportControl.findParkNotArrived("1",startdate,enddate).get(0);//get not arrived for Moses
		//Pheonix/
		cancelsPark2=ManagementReportControl.findParkCancels("2",startdate,enddate).get(0);			//get cancels for Pheonix
		notarrivedPark2=ManagementReportControl.findParkNotArrived("2",startdate,enddate).get(0);//get not arrived for for Pheonix
		//Yosemite/
		cancelsPark3=ManagementReportControl.findParkCancels("3",startdate,enddate).get(0);			//get cancels for Yosemite
		notarrivedPark3=ManagementReportControl.findParkNotArrived("3",startdate,enddate).get(0);//get not arrived for for Yosemite
		//Total
		cancels=cancelsPark1+cancelsPark2+cancelsPark3;
		notarrived = notarrivedPark1+notarrivedPark2+notarrivedPark3;
		LocalDate startDateLD = LocalDate.parse(startdate); // Convert startdate to LocalDate
		//Build lists of days for each park and report
		for (int i = 0; i < AmountOfDaysBetweenDates; i++) {
		     LocalDate currentDate = startDateLD.plusDays(i); // Increment startDateLD by i days
		     String currentDateStr = currentDate.toString(); // Convert currentDate back to String
		     mosesCancelesDay.add(i, (ManagementReportControl.findParkCancels("1",currentDateStr,currentDateStr).get(0)));
		     mosesNotArrivedDay.add(i, (ManagementReportControl.findParkNotArrived("1",currentDateStr,currentDateStr).get(0)));
		     pheonixCancelesDay.add(i, (ManagementReportControl.findParkCancels("2",currentDateStr,currentDateStr).get(0)));
		     pheonixNotArrivedDay.add(i, (ManagementReportControl.findParkNotArrived("2",currentDateStr,currentDateStr).get(0)));
		     yosemiteCancelesDay.add(i, (ManagementReportControl.findParkCancels("3",currentDateStr,currentDateStr).get(0)));
		     yosemiteNotArrivedDay.add(i, (ManagementReportControl.findParkNotArrived("3",currentDateStr,currentDateStr).get(0)));

		 }
         for (int i = 0; i < AmountOfDaysBetweenDates; i++) {
        	 totalCancelesDay.add(mosesCancelesDay.get(i) + pheonixCancelesDay.get(i) + yosemiteCancelesDay.get(i));
        	 totalNotArrivedDay.add(mosesNotArrivedDay.get(i) + pheonixNotArrivedDay.get(i) + yosemiteNotArrivedDay.get(i));
         }
	     //Calculate Cancels and Not Arrived Average
	     mosesAvgC = (double) cancelsPark1/AmountOfDaysBetweenDates;
	     pheonixAvgC = (double) cancelsPark2/AmountOfDaysBetweenDates;
	     yosemiteAvgC = (double) cancelsPark3/AmountOfDaysBetweenDates;
	     totalAvgC = (double) cancels/AmountOfDaysBetweenDates;
	     mosesAvgA = (double) notarrivedPark1/AmountOfDaysBetweenDates;
	     pheonixAvgA = (double) notarrivedPark2/AmountOfDaysBetweenDates;
	     yosemiteAvgA = (double) notarrivedPark3/AmountOfDaysBetweenDates;
	     totalAvgA = (double) notarrived/AmountOfDaysBetweenDates;
	}
	
	
    private int calculateMedian(ArrayList<Integer> values) {
        ArrayList<Integer> sortedValues = new ArrayList<>(values);
        Collections.sort(sortedValues); // Sort the copied list
        Collections.sort(sortedValues);
        int size = sortedValues.size();
        if (size % 2 == 0) {
            return (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            return sortedValues.get(size / 2);
        }
    }


	
	//Need to comment
    private boolean isValidDates(String startDate, String endDate) {
	    // Check if both start date and end date are not empty
	    if (startdate.isEmpty() || enddate.isEmpty()) {
	        new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - Please fill all the fields").showAndWait();
	        return false;
	    }

        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(startDate, formatter);
            LocalDate.parse(endDate, formatter);
        } catch (DateTimeParseException e) {
  	      new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - Please fill the fields according to the format (YYYY-MM-DD)").showAndWait();
  	      return false;
        }
        
        int res = startDate.compareTo(endDate);
        // Start date is after end date
        if (res > 0) {
    	      new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - Start date can't be greater than End date").showAndWait();
    	      return false;
        }

        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Parse start and end dates into LocalDate objects
        LocalDate startDateD = LocalDate.parse(startDate);
        int endYear = LocalDate.parse(endDate).getYear();
        int startYear = LocalDate.parse(startDate).getYear();
        // Check if current date is within the range
        // Check if dates are valid
        
        if (!currentDate.isAfter(startDateD))
        {
  	      new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - We can't predict the future").showAndWait();
  	      return false;
        }
        
        if (endYear < 2000 || startYear < 2000) {
    	      new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - No information before year 2000").showAndWait();
      	      return false;
        }
        AmountOfDaysBetweenDates = calculateDaysDifference(startdate, enddate);
        if (AmountOfDaysBetweenDates >= 20)
        {
	        new Alerts(AlertType.ERROR, "Input Error", "Input Error", "WRONG INPUT - Maximum range of dates is up to 20 days").showAndWait();
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
	
	
	private void loadBarChart() {
		
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(0);
		yAxis.setUpperBound((double) Math.max(cancels, notarrived));
		yAxis.setMinorTickVisible(false);
        yAxis.setTickUnit(1);
        xAxis.setAutoRanging(false);
        xAxis.setTickLength(0.5);
        BarChartBetween.setBarGap(1);
        observableCategories.clear();
        observableCategories.addAll(getUpdatedCategories());
        xAxis.setCategories(observableCategories);
}
	
	
	private ObservableList<String> getUpdatedCategories() {
	    // Example: Get updated categories from a data source or generate them dynamically
	    ObservableList<String> updatedCategories = FXCollections.observableArrayList();
	    updatedCategories.addAll(datesBetween);
	    return updatedCategories;
	}
	
	
	private void loadDataToChart(ArrayList<String> datesbetween, ArrayList<Integer> listreports, String name ) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(name);
		for (int i=0;i< AmountOfDaysBetweenDates; i++)
		{
			series.getData().add(new XYChart.Data<>(datesbetween.get(i), listreports.get(i)));
		}
		BarChartBetween.getData().add(series);
	}
	
	
	
    public static ArrayList<String> getDatesBetween(String startDateStr, String endDateStr) {
        ArrayList<String> datesInRange = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String dateString = currentDate.format(formatter);
            datesInRange.add(dateString);
            currentDate = currentDate.plusDays(1);
        }

        return datesInRange;
    }
    
	private void setToolTip() {
		for (XYChart.Series<String, Number> s : BarChartBetween.getData()) {
			for (XYChart.Data<String, Number> d : s.getData()) {

				Tooltip.install(d.getNode(), new Tooltip(
						"Number of " + s.getName().trim() + " visitors in " + d.getXValue() + ": " + d.getYValue()));
				
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
	}
	
	private void ClearData()
	{
		mosesCancelesDay.clear();
		pheonixCancelesDay.clear();
		yosemiteCancelesDay.clear();
		totalCancelesDay.clear();
		mosesNotArrivedDay.clear();
		pheonixNotArrivedDay.clear();
		yosemiteNotArrivedDay.clear();
		totalNotArrivedDay.clear();
		datesBetween.clear();
		
	    mosesCancelesDay = new ArrayList<>();
	    pheonixCancelesDay = new ArrayList<>();
	    yosemiteCancelesDay = new ArrayList<>();
	    totalCancelesDay = new ArrayList<>();
	    mosesNotArrivedDay = new ArrayList<>();
	    pheonixNotArrivedDay = new ArrayList<>();
	    yosemiteNotArrivedDay = new ArrayList<>();
	    totalNotArrivedDay = new ArrayList<>();
	    datesBetween = new ArrayList<>();

	}

}