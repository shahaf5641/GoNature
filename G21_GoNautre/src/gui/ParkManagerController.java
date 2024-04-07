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
 * Controller for the Park Manager's dashboard, allowing navigation to various management functionalities
 * like profile viewing, visitor ID entry, current visitor overview, parameter updating, and report creation.
 */

public class ParkManagerController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Label userLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Button parkManagerProfileButton;
    @FXML
    private Button enterVisitorIDButton;

    @FXML
    private Button currentVisitorsButton;
    @FXML
    private Button createReportsButton;

    @FXML
    private Button updateParametersButton;

    private Stage stage;
    private Stage mainScreenStage;
    
    FxmlToContollerLoader loader = new FxmlToContollerLoader();
    /**
     * Initializes the controller class. This method is automatically invoked after the FXML file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
    /**
     * Initializes the park manager interface and sets up event handling for window closure.
     */
    private void init() {
        loadProfileParkManagerProfile();
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
             
            	VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
    }
    /**
     * Sets the primary stage of the application.
     * 
     * @param stage The primary stage of the application
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Retrieves the primary stage of the application.
     * 
     * @return The primary stage of the application
     */
    private Stage getStage() {
        return stage;
    }

    /**
     * Sets the main screen stage of the application.
     * 
     * @param stage The main screen stage of the application
     */
  
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }
    /**
     * Loads the update parameters view into the BorderPane.
     */
    @FXML
    private void loadUpdateParams() {
        FxmlToContollerLoader loader = new FxmlToContollerLoader();
        Pane view = loader.PaneControllerToFxml("/gui/UpdateParams.fxml", "UpdateParams");
        borderPane.setCenter(view);
    }

    /**
     * Loads the park manager's profile view into the BorderPane.
     */

    @FXML
    private void loadProfileParkManagerProfile() {
        loader.setWorker(true);
        Pane view = loader.PaneControllerToFxml("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }
    /**
     * Loads the park parameters view into the BorderPane.
     */
    @FXML
    private void loadParkParams() {
        Pane view = loader.PaneControllerToFxml("/gui/ParkParams.fxml", "ParkParams");
        borderPane.setCenter(view);
    }
    /**
     * Loads the create reports view into the BorderPane.
     */
    @FXML
    private void loadGenerateReports() {
        Pane view = loader.PaneControllerToFxml("/gui/GenerateReports.fxml", "GenerateReport");
        borderPane.setCenter(view);
    }

    @FXML
    private void logOut() {
    	VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
        getStage().close();
        mainScreenStage.show();
    }

}
