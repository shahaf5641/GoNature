package gui;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.AutenticationControl;
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
import util.FxmlUtil;

/**
 * This Class is the GUI controller of DepartmentManager.fxml
 * It handles all the JavaFx nodes events.
 * 
 * This is the main screen of the department manager
 *
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
    private Button profileButton;

    @FXML
    private Button currentVisitorsButton;

    @FXML
    private Button enterVisitorIDButton;

    @FXML
    private Button createReportsButton;

    @FXML
    private Button updateParametersButton;

    private Stage stage;
    private Stage mainScreenStage;

    FxmlUtil loader = new FxmlUtil();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        loadProfile();
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                AutenticationControl.userLogout(String.valueOf(MemberLoginController.member.getEmployeeId()));
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
    }

    private Stage getStage() {
        return stage;
    }

    /**
     * Setter for the class variable stage
     * 
     * @param stage The current stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Setter for the class variable mainScreenStage
     * 
     * @param stage The main stage
     */
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }

    @FXML
    private void loadProfile() {
        loader.setWorker(true);
        Pane view = loader.loadPaneToBorderPaneWithController("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }

    @FXML
    private void loadParkParameters() {
        Pane view = loader.loadPaneToBorderPaneWithController("/gui/ParkParameters.fxml", "parkParameters");
        borderPane.setCenter(view);
    }

    @FXML
    private void loadViewRequests() {
        Pane view = loader.loadPaneToBorderPaneWithController("/gui/ViewRequestsForChanges.fxml", "viewRequests");
        borderPane.setCenter(view);
    }
    
    @FXML
    private void loadReports() {
        Pane view = loader.loadPaneToBorderPaneWithController("/gui/DepartmentManagerReports.fxml", "reports");
        borderPane.setCenter(view);
    }

    @FXML
    private void logOut() {
        AutenticationControl.userLogout(String.valueOf(MemberLoginController.member.getEmployeeId()));
        getStage().close();
        mainScreenStage.show();
    }

}
