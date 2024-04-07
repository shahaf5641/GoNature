package gui;

import java.net.URL;
import java.util.ResourceBundle;
import client.ClientUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for the main screen window.
 * This class is responsible for displaying the main screen of the application, including the logo, menu, and park images.
 * 

 */
public class HomeScreenController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView goNatureLogo;

    @FXML
    private Label menuLabel;

    @FXML
    private ImageView ImagePark1;

    @FXML
    private ImageView ImagePark2;

    @FXML
    private ImageView ImagePark3;

    private Stage stage;

    /**
     * Initializes the controller after its root element has been completely processed.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        init();
    }

    /**
     * Initializes the controller's behavior.
     */
    private void init() {
        getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                ClientUI.chat.getClient().quit();
            }
        });
    }

    /**
     * Sets the stage associated with this controller.
     * 
     * @param stage The stage to be associated with this controller.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Retrieves the stage associated with this controller.
     * 
     * @return The stage associated with this controller.
     */
    private Stage getStage() {
        return stage;
    }
}
