package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import alerts.Alerts;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureConstants;

/**
 * Controller class for the Create Reports GUI.
 */
public class GenerateReportsController implements Initializable {

	
	
	
	/**
	 * AnchorPane for the root node of the Create Reports GUI.
	 */
	@FXML
	private AnchorPane createReportsRootPane;

	/**
	 * TitledPane for selecting the month.
	 */
	@FXML
	private TitledPane monthTP;

	/**
	 * AnchorPane for choosing the month.
	 */
	@FXML
	private AnchorPane chooseMonthAP;

	/**
	 * ComboBox for selecting the month.
	 */
	@FXML
	private ComboBox<String> monthCB;

	/**
	 * TitledPane for selecting the report type.
	 */
	@FXML
	private TitledPane reportTP;

	/**
	 * AnchorPane for choosing the report type.
	 */
	@FXML
	private AnchorPane chooseReportAP;

	/**
	 * RadioButton for selecting the total visitors report.
	 */
	@FXML
	private RadioButton totalVisitorsRB;

	/**
	 * RadioButton for selecting the usage report.
	 */
	@FXML
	private RadioButton useageRB;

	/**
	 * TitledPane for adding comments.
	 */
	@FXML
	private TitledPane commentTP;

	/**
	 * AnchorPane for adding comments.
	 */
	@FXML
	private AnchorPane addCommentAP;

	/**
	 * TextArea for entering comments.
	 */
	@FXML
	private TextArea commentTextArea;

	/**
	 * Accordion for managing titled panes.
	 */
	@FXML
	private Accordion accordionId;

	/**
	 * ProgressIndicator for indicating progress during report generation.
	 */
	@FXML
	private ProgressIndicator pi;

	/**
	 * AnchorPane for the root node of the Create Reports GUI.
	 * Note: This field is redundant and appears to be a duplicate of 'createReportsRootPane'.
	 */
	@FXML
	private AnchorPane CreateReportsRootPane;

	/**
	 * Name of the FXML file associated with the currently selected report type.
	 */
	private String fxmlName;

	/**
	 * Title of the screen or stage for the report.
	 */
	private String screenTitle;

	/**
	 * Integer representing the selected month.
	 */
	protected static int month;
    
    protected ArrayList<String> newReportList;

    /**
     * Initializes the controller.
     * 
     * @param arg0      The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1      The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }

    /**
     * Initializes the GUI components.
     */
    private void init() {
        accordionId.setExpandedPane(monthTP);
        initComboBox();
        /* Default settings */
        totalVisitorsRB.setSelected(true);
        this.fxmlName = "/gui/TotalVisitorsReport.fxml";
        screenTitle = "Total Visitors Report";
    }

    /**
     * Handles the action when the create report button is clicked.
     */
    @FXML
    private void createReportButton() {
        if (monthCB.getSelectionModel().getSelectedIndex() == 0) {
            new Alerts(AlertType.ERROR, "Error", "Month Error", "Please choose month.").showAndWait();
        } else {
            switchSceneWithController();
        }
    }

    /**
     * Handles the action when the total visitors radio button is selected.
     */
    @FXML
    private void totalVisitorsOnRadioButton() {
        totalVisitorsRB.setSelected(true);
        useageRB.setSelected(false);
        this.fxmlName = "/gui/TotalVisitorsReport.fxml";
        screenTitle = "Total Visitors Report";
    }

    /**
     * Handles the action when the usage radio button is selected.
     */
    @FXML
    private void useageOnRadioButton() {
        useageRB.setSelected(true);
        totalVisitorsRB.setSelected(false);
        this.fxmlName = "/gui/UsageReport.fxml";
        screenTitle = "Usage Report";
    }

    /**
     * Initializes the month combo box.
     */
    private void initComboBox() {
        monthCB.getItems().addAll(GoNatureConstants.MONTH_NAMES);
        monthCB.getSelectionModel().select(0);
    }

    /**
     * Retrieves the stage.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return (Stage) monthCB.getScene().getWindow();
    }

    /**
     * Switches the scene with the controller based on the selected report type.
     */
    private void switchSceneWithController() {
        Stage thisStage = getStage();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));

        if (totalVisitorsRB.isSelected()) {
            TotalVisitorsReportController controller = new TotalVisitorsReportController();
            controller.setComment(commentTextArea.getText());
            controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
            controller.setParkID(EmployeeLoginController.member.getParkId());
            loader.setController(controller);
        } else if (useageRB.isSelected()) {
            UsageReportController controller = new UsageReportController();
            controller.setComment(commentTextArea.getText());
            controller.setMonthNumber(monthCB.getSelectionModel().getSelectedIndex());
            controller.setParkID(EmployeeLoginController.member.getParkId());
            loader.setController(controller);
        }

        Task<Parent> task = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                // Load the FXML file in the background
                return loader.load();
            }
        };

        task.setOnRunning(event -> {
        	pi.setVisible(true);
        	createReportsRootPane.setDisable(true);
        	
        	}); // Show progress indicator while task is running

        task.setOnSucceeded(event -> {
            // Hide progress indicator and get the loaded Parent from the task
            pi.setVisible(false);
            createReportsRootPane.setDisable(false);
            Parent p = task.getValue();

            // Create a scene with the loaded FXML content
            Scene scene = new Scene(p);

            // Block parent stage until child stage closes
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setTitle(screenTitle);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        });

        task.setOnFailed(event -> {
            // Handle task failure by hiding the progress indicator
            pi.setVisible(false);
            new Alerts(AlertType.ERROR, "Error", "Error", "Failed to load form").showAndWait();
            task.getException().printStackTrace();
        });

        // Start the task in a new thread
        new Thread(task).start();
    }
 }