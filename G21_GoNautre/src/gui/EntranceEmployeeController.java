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
 * Controller class for managing entrance employee functionalities.
 */

public class EntranceEmployeeController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topPane;

    @FXML
    private Label userLabel;

    @FXML
    private VBox vbox;
    @FXML
    private Button IDenterVisitorButton;

    @FXML
    private Button profileEntranceWorkerButton;

    @FXML
    private Button currentVisitorsButton;



    private Stage stage;
    private Stage mainScreenStage;

    FxmlToContollerLoader loader = new FxmlToContollerLoader();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }

    /**
     * Initializes the components and sets up event handling.
     */
    private void init() {
        loadEntranceEmployeeProfile();
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
    }

    /**
     * Gets the stage of this controller.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage of this controller.
     * 
     * @param stage The stage to set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

   
    /**
     * Sets the main screen stage.
     * 
     * @param stage The main screen stage to set.
     */
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }

    /**
     * Loads the traveler management view.
     */
    @FXML
    private void loadTravelerManagement() {
        Pane view = loader.PaneControllerToFxml("/gui/TravelerManagement.fxml", "Travelermng");
        borderPane.setCenter(view);
    }
    
    /**
     * Loads the entrance employee profile view.
     */
    @FXML
    private void loadEntranceEmployeeProfile() {
        loader.setWorker(true);
        Pane view = loader.PaneControllerToFxml("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }
    
    /**
     * Logs out the current user.
     */
    @FXML
    private void logOut() {
        VerificationControl.Logout(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
        getStage().close();
        mainScreenStage.show();
    }
    /**
     * Loads the park parameters view.
     */

    @FXML
    private void loadParams() {
        Pane view = loader.PaneControllerToFxml("/gui/ParkParams.fxml", "ParkParams");
        borderPane.setCenter(view);
    }



}
