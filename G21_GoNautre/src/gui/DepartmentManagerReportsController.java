package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import logic.Report;
import logic.ReportTable;
import Controllers.ManagementReportControl;
import alerts.Alerts;

/**
 * Controller class for the Department Manager Reports GUI.
 */
public class DepartmentManagerReportsController implements Initializable {

    ObservableList<ReportTable> observable = FXCollections.observableArrayList(); 

    @FXML
    private Label headerLabel;
    @FXML
    private TableColumn<ReportTable, String> reportTableColumn;

    @FXML
    private TableColumn<ReportTable, String> commentTableColumn;

    @FXML
    private TableView<ReportTable> ReportsTableView;

    @FXML
    private TableColumn<ReportTable, Integer> reportIDTableColumn;

    @FXML
    private TableColumn<ReportTable, Integer> IDparkCol;
    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TableColumn<ReportTable, Integer> monthTableColumn;


    @FXML
    private Button visitReportButton;

    @FXML
    private Button CancelsReportButton;

   

    private String fxmlName;
    private String screenTitle;

    /**
     * Initializes the controller class.
     * 
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadTabelView();
        initComboBox();
        initTabelView();
    }

    /**
     * Initializes the TableView.
     */
    private void initTabelView() {
        ReportsTableView.setTooltip(new Tooltip("Double click on a row to open the report"));
        ReportsTableView.setRowFactory(tv -> {
            TableRow<ReportTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    ReportTable clickedRow = row.getItem();
                    String name = clickedRow.getReportType();
                    int month = clickedRow.getMonth();
                    int parkID = clickedRow.getParkID();
                    String comment = clickedRow.getComment();
                    loadReport(name, month, parkID, comment);
                }
            });
            return row;
        });
    }

    /**
     * Loads the report based on its type.
     * 
     * @param name    The name of the report type.
     * @param month   The month of the report.
     * @param parkID  The ID of the park related to the report.
     * @param comment The comment of the report.
     */
    private void loadReport(String name, int month, int parkID, String comment) {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = null;
            Stage newStage = new Stage();

            if (name.equals("Usage")) {
                screenTitle = "Usage Report";
                loader = new FXMLLoader(getClass().getResource("/gui/UsageReport.fxml"));
                UsageReportController controller = new UsageReportController();
                controller.setComment(comment);
                controller.setMonthNumber(month);
                controller.setParkID(parkID);
                controller.setIsDepManager(true);
                loader.setController(controller);
            } else if (name.equals("Total Visitors")) {
                screenTitle = "Total Visitors Report";
                loader = new FXMLLoader(getClass().getResource("/gui/TotalVisitorsReport.fxml"));
                TotalVisitorsReportController controller = new TotalVisitorsReportController();
                controller.setComment(comment);
                controller.setMonthNumber(month);
                controller.setParkID(parkID);
                controller.setIsDepManager(true);
                loader.setController(controller);
            }

            loader.load();
            Parent p = loader.getRoot();

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setTitle(screenTitle);
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("failed to load form");
            e.printStackTrace();
        }
    }

    /**
     * Handles the cancel report button action.
     */
    @FXML
    private void cancelReportButton() {
        fxmlName = "/gui/CancelsReport.fxml";
        screenTitle = "Cancels Report";
        switchScenceWithController();
    }
    
    /**
     * Handles the visit report button action.
     */
    @FXML
    private void visitReportButton() {
        fxmlName = "/gui/VisitsReports.fxml";
        screenTitle = "Visits Report";
        if (monthComboBox.getSelectionModel().getSelectedIndex() != 0) {
            switchScenceWithController();
        } else {
            new Alerts(AlertType.ERROR, "Error", "Month Error", "Please choose a month.").showAndWait();
        }
    }
    
    /**
     * Loads data into the TableView.
     */
    private void loadTabelView() {
        ArrayList<Report> reports = ManagementReportControl.findReports();
        ArrayList<ReportTable> tbReports = reportsToportTb(reports);
        init(tbReports);
        ReportsTableView.setItems(getReports(tbReports));
    }

    /**
     * Converts reports to ReportTable.
     * 
     * @param reports The list of reports to convert.
     * @return The list of converted ReportTable.
     */
    private static ArrayList<ReportTable> reportsToportTb(ArrayList<Report> reports) {
        ArrayList<ReportTable> tbReports = new ArrayList<ReportTable>();
        for (Report report : reports) {
            ReportTable tbReport = new ReportTable(report);
            tbReports.add(tbReport);
        }
        return tbReports;
    }

    /**
     * Initializes the columns of the TableView.
     * 
     * @param tbReports The list of ReportTable.
     */
    private void init(ArrayList<ReportTable> tbReports) {
        reportIDTableColumn.setCellValueFactory(new PropertyValueFactory<ReportTable, Integer>("reportID"));
        reportTableColumn.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("reportType"));
        IDparkCol.setCellValueFactory(new PropertyValueFactory<ReportTable, Integer>("parkID"));
        monthTableColumn.setCellValueFactory(new PropertyValueFactory<ReportTable, Integer>("month"));
        commentTableColumn.setCellValueFactory(new PropertyValueFactory<ReportTable, String>("comment"));
    }

    /**
     * Retrieves the reports for the TableView.
     * 
     * @param tbReports The list of ReportTable.
     * @return The observable list of ReportTable.
     */
    private ObservableList<ReportTable> getReports(ArrayList<ReportTable> tbReports) {
        ReportsTableView.getItems().clear();
        for (ReportTable report : tbReports) {
            observable.add(report);
        }
        return observable;
    }

    /**
     * Initializes the ComboBox.
     */
    private void initComboBox() {
        monthComboBox.getItems().addAll(GoNatureConstants.MONTH_NAMES);
        monthComboBox.getSelectionModel().select(0);
    }

    /**
     * Retrieves the stage.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return (Stage) monthComboBox.getScene().getWindow();
    }

    /**
     * Switches scene with controller.
     */
    private void switchScenceWithController() {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            if (screenTitle.equals("Cancels Report")) {
                CancelsReportController controller = new CancelsReportController();
                loader.setController(controller);
            } else {
                VisitsReportController controller = new VisitsReportController();
                controller.SetMonth(monthComboBox.getSelectionModel().getSelectedIndex());
                loader.setController(controller);

            }
            loader.load();
            Parent p = loader.getRoot();
            Stage newStage = new Stage();

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setTitle(screenTitle);
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("failed to load form");
            e.printStackTrace();
        }

    }

}