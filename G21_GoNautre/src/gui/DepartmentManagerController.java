package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.VerificationControl;
import client.ClientUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.FxmlToContollerLoader;

/**
 * Controller class for Department Manager GUI.
 */
public class DepartmentManagerController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Label userLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Button currentVisitorsButton;

    @FXML
    private Button enterIDVisitorBtn;

    @FXML
    private Button createReportBtn;

    @FXML
    private Button updateParametersButton;

    @FXML
    private Button profileButton;

    private Stage stage;
    private Stage mainScreenStage;

    private FxmlToContollerLoader loader = new FxmlToContollerLoader();

    /**
     * Initializes the controller class.
     * 
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    /**
     * Initializes the Department Manager GUI.
     */
    private void init() {
        loadDepartmentProfile();
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
    }

    /**
     * Retrieves the stage.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return stage;
    }

    /**
     * Sets the main screen stage.
     * 
     * @param stage The main screen stage.
     */
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }
    
    /**
     * Sets the stage.
     * 
     * @param stage The stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Loads the parameters of the park.
     */
    @FXML
    private void loadParamsOfPark() {
        Pane view = loader.PaneControllerToFxml("/gui/ParkParams.fxml", "ParkParams");
        borderPane.setCenter(view);
    }

    /**
     * Loads the department profile.
     */
    @FXML
    private void loadDepartmentProfile() {
        loader.setWorker(true);
        Pane view = loader.PaneControllerToFxml("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }

    /**
     * Loads the department reports.
     */
    @FXML
    private void loadDepartmentReports() {
        Pane view = loader.PaneControllerToFxml("/gui/DepartmentManagerReports.fxml", "DPMReports");
        borderPane.setCenter(view);
    }

    /**
     * Loads the view requests for changes.
     */
    @FXML
    private void loadShowRequests() {
        Pane view = loader.PaneControllerToFxml("/gui/ShowChangesRequests.fxml", "showRequests");
        borderPane.setCenter(view);
    }

    /**
     * Logs out from the system.
     */
    @FXML
    private void logOut() {
        VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
        getStage().close();
        mainScreenStage.show();
    }
}