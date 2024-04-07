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


public class TravelerScreenController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane topAnchorPane;

    @FXML
    private Label travelerLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Button profileButton;

    @FXML
    private Button viewOrdersButton;
    @FXML
    private Button orderButton;

    @FXML
    private Button viewMessagesButton;

    @FXML
    private Pane midPane;

    private Stage stage;
    private Stage mainScreenStage;

    FxmlToContollerLoader loader = new FxmlToContollerLoader();
    /**
     * Initializes the controller class. This method is automatically invoked after the FXML file has been loaded.
     * It sets up the UI components and prepares the application window for interaction.
     *
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();

    }
    /**
     * Initializes the main screen's UI components and functionality. It sets up the logout procedure and loads
     * the traveler's profile view by default.
     */
    private void init() {
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                String travelerId = "";
                if (TravelerLoginController.traveler != null) {
                    travelerId = TravelerLoginController.traveler.getTravelerId();
                }
                VerificationControl.Logout(travelerId);
                mainScreenStage.close();
                ClientUI.chat.getClient().quit();
            }
        });
        loadTravelerProfile();
    }


    private Stage getStage() {
        return stage;
    }
    
    public void setMainScreenStage(Stage stage) {
        this.mainScreenStage = stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Loads the Order Traveler Visit view into the center of the main screen.
     */
  

    @FXML
    private void loadOrderTravelerVisit() {
        Pane view = loader.PaneControllerToFxml("/gui/OrderVisit.fxml", "OrderVisit");
        borderPane.setCenter(view);
    }

    /**
     * Loads the Traveler Orders view into the center of the main screen.
     */

    @FXML
    private void loadTravelerOrders() {
        Pane view = loader.PaneControllerToFxml("/gui/ShowTravelerOrders.fxml", "TravelerOrders");
        borderPane.setCenter(view);
    }
    /**
     * Loads the Messages view into the center of the main screen.
     */
    @FXML
    private void loadMessages() {
        Pane view = loader.PaneControllerToFxml("/gui/ShowMessages.fxml", "TravelerMessages");
        borderPane.setCenter(view);
    }
    /**
     * Logs out the user from the system and returns to the main screen.
     */
    @FXML
    private void logOut() {
        String travelerId = "";
        if (TravelerLoginController.traveler != null) {
            travelerId = TravelerLoginController.traveler.getTravelerId();
        }
        VerificationControl.Logout(travelerId);
        getStage().close();
        mainScreenStage.show();
    }
    /**
     * Loads the Traveler Profile view into the center of the main screen.
     */
    @FXML
    private void loadTravelerProfile() {
        loader.setWorker(false);
        Pane view = loader.PaneControllerToFxml("/gui/Profile.fxml", "profile");
        borderPane.setCenter(view);
    }


}
