package gui;

import java.net.URL;
import java.util.ResourceBundle;
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
import Controllers.VerificationControl;
import client.ClientUI;
/**
 * Controls the Service Worker's main application window, managing navigation
 * and user actions within the service worker interface.
 */
public class ServiceEmployeeController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Button profileButton;

    @FXML
    private Button currentVisitorsButton;

    @FXML
    private Button enterVisitorIDButton;
    
    @FXML
    private Label userLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Button registerButton;
    private Stage mainScreenStage;
    private Stage stage;
 

    FxmlToContollerLoader loader = new FxmlToContollerLoader();
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * 
     * @param location  The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        loadServiceEmployeeProfile();
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
    }
    /**
     * Gets the current stage of this controller.
     *
     * @return The active Stage object.
     */
    private Stage getStage() {
        return stage;
    }
    /**
     * Sets the stage for this controller. This stage represents the window in which this controller's scene is displayed.
     *
     * @param stage The stage to set for this controller.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Sets the main screen stage. This is typically the primary stage of the application.
     *
     * @param stage The main screen stage to set.
     *
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }
    /**
     * Loads the Park Parameters view into the center of the BorderPane of the Service Worker's UI.
     * This view allows the service worker to view and possibly edit parameters related to park settings.
     */
    @FXML
    private void loadParkParams() {
        Pane view = loader.PaneControllerToFxml("/gui/ParkParams.fxml", "ParkParams");
        borderPane.setCenter(view);
    }
    /**
     * Loads the Register Guide view into the center of the BorderPane of the Service Worker's UI.
     * This view enables the service worker to register new guides into the system.
     */
    @FXML
    private void loadRegisterGuide() {
        Pane view = loader.PaneControllerToFxml("/gui/AddGuide.fxml", "addGuide");
        borderPane.setCenter(view);
    }
    /**
     * Handles the logout process for the service worker. It performs the logout operation,
     * closes the current stage, and returns the user to the main screen of the application.
     */

    @FXML
    private void logOut() {
        VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
        getStage().close();
        mainScreenStage.show();
    }
    
    /**
     * Loads the Service Worker Profile view into the center of the BorderPane.
     * This view displays the service worker's profile information.
     */
    @FXML
    private void loadServiceEmployeeProfile() {
        loader.setWorker(true);
        Pane view = loader.PaneControllerToFxml("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }

    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }

}
