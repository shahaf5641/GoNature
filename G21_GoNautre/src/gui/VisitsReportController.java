package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.GoNatureConstants;
import logic.VisitReport;

public class VisitsReportController implements Initializable {

    @FXML
    private Label headerLabel;
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private LineChart<Number, Number> StayTimeChart;
    
    @FXML
    private LineChart<Number, Number> EntranceTimeChart;

    @FXML
    private NumberAxis stayX2;
    
    @FXML
    private NumberAxis stayY;

    @FXML
    private NumberAxis enterX2;

    @FXML
    private NumberAxis enterY;

    @FXML
    private Label lblMonth;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> dataComboBox;

    private int monthNumber; 
    
    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
    
    /**
     * Initializes the components and data.
     */

    private void init() {
        lblMonth.setText(GoNatureConstants.MONTH_NAMES[monthNumber]); 
        initGraphs();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initComboBox();

                EntranceTimeChart.getData().clear();
                StayTimeChart.getData().clear();
                LoadDataOfSolos(comboBox.getSelectionModel().getSelectedItem());
                LoadDataOfGroups(comboBox.getSelectionModel().getSelectedItem());

            }
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }
    
    /**
     * Sets the month number for the report.
     *
     * @param month The month number to set.
     */
    public void SetMonth(int month) {
        this.monthNumber = month;
    }
    
    /**
     * Loads data of group visits based on the selected select.
     *
     * @param select The selected select for group visits.
     */
    @SuppressWarnings("unchecked")
    private void LoadDataOfGroups(String select) {
        ArrayList<VisitReport> VisitReport2 = new ArrayList<VisitReport>();
        if (!select.equals("Show whole month"))
            ManagementReportControl.enterTimeGroupsCountDays(monthNumber, select);
        else
            ManagementReportControl.enterTimeGroupCount(monthNumber);
        VisitReport2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getSuccessSet();
        XYChart.Series<Number, Number> seriesversion2 = new Series<Number, Number>();
        double hour, min, time;
        int maxNumOfVisitors = 0, sum;
        for (int i = 0; i < VisitReport2.size(); i++) {
            sum = VisitReport2.get(i).getSumVisitors();
            if (maxNumOfVisitors < sum) {
                maxNumOfVisitors = sum;
            }
            String data = VisitReport2.get(i).getDataReport(); 

           
            String[] parts = data.split(":");
            if (parts.length >= 2) { 
                try {
                   
                    hour = Double.parseDouble(parts[0]);
                    min = Double.parseDouble(parts[1]);
                    
                 
                    time = hour + (min / 60.0);
                    seriesversion2.getData().add(new Data<Number, Number>(time, sum));
                } catch (NumberFormatException e) {
                  
                    System.err.println("Error parsing hour or minute: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid time format");
            }
            
        }

        seriesversion2.setName("Groups");
        EntranceTimeChart.getData().add(seriesversion2);

        setToolTip();
        maxNumOfVisitors++;
        if (maxNumOfVisitors % 2 != 0)
            maxNumOfVisitors++;
      
        enterY.setUpperBound(maxNumOfVisitors + 30);
        enterY.setTickUnit(Math.ceil(maxNumOfVisitors * 0.1));

        VisitReport2 = new ArrayList<VisitReport>();
        if (!select.equals("Show whole month"))
            ManagementReportControl.visitTimeGroupsCountDays(monthNumber, select);
        else
            ManagementReportControl.visitTimeGroupCount(monthNumber);
        VisitReport2 = (ArrayList<VisitReport>) ChatClient.responseFromServer.getSuccessSet();
        double totalNumOfVisitors = 0;
     
        for (int i = 0; i < VisitReport2.size(); i++)
            totalNumOfVisitors += VisitReport2.get(i).getSumVisitors();

        seriesversion2 = new Series<Number, Number>();
        hour = 0;
        min = 0;
        time = 0;
        maxNumOfVisitors = 0;
        sum = 0;
        for (int i = 0; i < VisitReport2.size(); i++) {
            sum = VisitReport2.get(i).getSumVisitors();
            if (maxNumOfVisitors < sum) {
                maxNumOfVisitors = sum;
            }
            String data = VisitReport2.get(i).getDataReport(); 

        
            String[] parts = data.split(":");
            if (parts.length >= 2) { 
                try {
                 
                    hour = Double.parseDouble(parts[0]);
                    min = Double.parseDouble(parts[1]);
                    
                 
                    time = hour + (min / 60.0);
                    
                } catch (NumberFormatException e) {
                   
                    System.err.println("Error parsing hour or minute: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid time format");
            }

            seriesversion2.getData().add(new Data<Number, Number>(time, sum / totalNumOfVisitors * 100));
        }

        seriesversion2.setName("Groups");
        StayTimeChart.getData().add(seriesversion2);

        setToolTip();
        
      
        if (totalNumOfVisitors == 0)
            totalNumOfVisitors++;
        stayY.setUpperBound(maxNumOfVisitors / totalNumOfVisitors * 100 + 5 > 100 ? 100
                : Math.ceil(maxNumOfVisitors / totalNumOfVisitors * 100 + 30));
    }

    /**
     * Loads data of solo visits based on the selected select.
     *
     * @param select The selected select for solo visits.
     */
    @SuppressWarnings("unchecked")
    private void LoadDataOfSolos(String select) {
        ArrayList<VisitReport> VisitReport = new ArrayList<VisitReport>();
        if (!select.equals("Show whole month"))
            ManagementReportControl.enterTimeSoloCountDays(monthNumber, select);
        else
            ManagementReportControl.enterTimeSoloCount(monthNumber);
        VisitReport = (ArrayList<VisitReport>) ChatClient.responseFromServer.getSuccessSet();
        XYChart.Series<Number, Number> series = new Series<Number, Number>();

        double hour, min, time;
        int maxNumOfVisitors = 0, sum;
        for (int i = 0; i < VisitReport.size(); i++) {
            sum = VisitReport.get(i).getSumVisitors();
            if (maxNumOfVisitors < sum) {
                maxNumOfVisitors = sum;
            }
            String data = VisitReport.get(i).getDataReport(); 

            
            String[] parts = data.split(":");
            if (parts.length >= 2) { 
                try {
                  
                    hour = Double.parseDouble(parts[0]);
                    min = Double.parseDouble(parts[1]);
                    time = hour + (min / 60.0);
                    series.getData().add(new Data<Number, Number>(time, sum));
                } catch (NumberFormatException e) {
                    
                    System.err.println("Error parsing hour or minute: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid time format");
            }
            
        }
        series.setName("Solos      ");
        EntranceTimeChart.getData().add(series);

        setToolTip();
        maxNumOfVisitors++;
        if (maxNumOfVisitors % 2 != 0)
            maxNumOfVisitors++;
        enterY.setUpperBound(maxNumOfVisitors + 30);
        enterY.setTickUnit(Math.ceil(maxNumOfVisitors * 0.1));
        if (!select.equals("Show whole month"))
            ManagementReportControl.visitTimeSoloCountDays(monthNumber, select);
        else
            ManagementReportControl.visitTimeSoloCount(monthNumber);
        VisitReport = new ArrayList<VisitReport>();
        VisitReport = (ArrayList<VisitReport>) ChatClient.responseFromServer.getSuccessSet();
        double totalNumOfVisitors = 0;
        for (int i = 0; i < VisitReport.size(); i++)
            totalNumOfVisitors += VisitReport.get(i).getSumVisitors();
        series = new Series<Number, Number>();
        hour = 0;
        min = 0;
        time = 0;
        maxNumOfVisitors = 0;
        sum = 0;
        for (int i = 0; i < VisitReport.size(); i++) {
            sum = VisitReport.get(i).getSumVisitors();
            if (maxNumOfVisitors < sum) {
                maxNumOfVisitors = sum;
            }
            String data = VisitReport.get(i).getDataReport();

         String[] parts = data.split(":");
         if (parts.length >= 2) {
             try {
                 hour = Double.parseDouble(parts[0]);
                 min = Double.parseDouble(parts[1]);
                 
                 time = hour + (min / 60.0);
             } catch (NumberFormatException e) {
                 System.err.println("Error parsing hour or minute: " + e.getMessage());
             }
         } else {
             System.err.println("Invalid time format");
         }
         	
            series.getData().add(new Data<Number, Number>(time, sum / totalNumOfVisitors * 100));
        }

        series.setName("Solos      ");
        StayTimeChart.getData().add(series);
        setToolTip();
        if (totalNumOfVisitors == 0)
            totalNumOfVisitors++;
        stayY.setUpperBound(maxNumOfVisitors / totalNumOfVisitors * 100 + 5 > 100 ? 100
                : Math.ceil(maxNumOfVisitors / totalNumOfVisitors * 100 + 30));

    }


    /**
     * Sets the tooltip for data points on the line charts.
     */
    private void setToolTip() {
        for (XYChart.Series<Number, Number> s : StayTimeChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {

                Tooltip.install(d.getNode(), new Tooltip(d.getYValue() + "%"));

                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
        for (XYChart.Series<Number, Number> s : EntranceTimeChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {

                Tooltip.install(d.getNode(), new Tooltip(d.getYValue() + " Visitors"));

                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }
    
    /**
     * Initializes the combo boxes for selecting data.
     */
    private void initComboBox() {
        int days = Calculatedays();
        ObservableList<String> month_days = FXCollections.observableArrayList();
        month_days.add("Show whole month");
        for (int i = 1; i <= days; i++) {
            month_days.add(String.valueOf(i));
        }
        comboBox.getItems().addAll(month_days);
        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem == null) {
            } else {
                if (dataComboBox.getSelectionModel().getSelectedItem().equals("Show All")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfSolos(comboBox.getSelectionModel().getSelectedItem());
                    LoadDataOfGroups(comboBox.getSelectionModel().getSelectedItem());
                } else if (dataComboBox.getSelectionModel().getSelectedItem().equals("Solo Visits")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfSolos(comboBox.getSelectionModel().getSelectedItem());
                } else if (dataComboBox.getSelectionModel().getSelectedItem().equals("Group Visits")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfGroups(comboBox.getSelectionModel().getSelectedItem());
                }
            }
        });

        dataComboBox.getItems().addAll("Show All", "Solo Visits", "Group Visits");
        dataComboBox.getSelectionModel().select(0);
        dataComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem == null) {
            } else {
                if (newItem.equals("Show All")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfSolos(comboBox.getSelectionModel().getSelectedItem());
                    LoadDataOfGroups(comboBox.getSelectionModel().getSelectedItem());

                } else if (newItem.equals("Solo Visits")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfSolos(comboBox.getSelectionModel().getSelectedItem());
                } else if (newItem.equals("Group Visits")) {
                    EntranceTimeChart.getData().clear();
                    StayTimeChart.getData().clear();
                    LoadDataOfGroups(comboBox.getSelectionModel().getSelectedItem());
                }
            }
        });

    }

    /**
     * Saves the report as a PDF file.
     */
    @FXML
    private void saveReportAsPdf() {
        File directory = new File(System.getProperty("user.home") + "/Desktop/reports/");
        if (!directory.exists()) {
            directory.mkdir();
        }

        WritableImage nodeshot = rootPane.snapshot(new SnapshotParameters(), null);
        String fileName = "Visits Report - month number " + monthNumber + ".pdf";
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
            doc.save(System.getProperty("user.home") + "/Desktop/reports/" + fileName);
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

    /**
     * Calculates the number of days in the current month.
     *
     * @return The number of days in the month.
     */
    private int Calculatedays() {
        String month = null;
        if (monthNumber < 10)
            month = "0" + monthNumber;
        else
            month = String.valueOf(monthNumber);
        YearMonth ym = YearMonth.parse(Calendar.getInstance().get(Calendar.YEAR) + "-" + month);
        return ym.lengthOfMonth();
    }
    
    /**
     * Initializes the line charts and axes.
    */
    private void initGraphs() {
        enterX2.setAutoRanging(false);
        enterX2.setLowerBound(7.5);
        enterX2.setUpperBound(18.0);
        enterX2.setMinorTickVisible(false);
        enterX2.setTickUnit(0.5);
        enterY.setAutoRanging(false);
        enterY.setLowerBound(0);
        enterY.setMinorTickVisible(false);
        stayX2.setAutoRanging(false);
        stayX2.setLowerBound(0.0);
        stayX2.setUpperBound(10.0);
        stayX2.setMinorTickVisible(false);
        stayX2.setTickUnit(0.5);
        stayY.setAutoRanging(false);
        stayY.setLowerBound(0);
        stayY.setTickUnit(1);
        stayY.setMinorTickVisible(false);

    }
}
