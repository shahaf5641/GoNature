package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.GoNatureConstants;
/**
 * Controller class for the main screen menu.
 * Handles the initialization of the main screen menu and switching between different screens.
 */

public class HomeButtonsController implements Initializable {
    @FXML
    private Button orderVisit;
    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
    /**
     * Loads the "Contact Us" screen when the corresponding button is clicked.
     */
    @FXML
    private void loadContactUs() {
        switchScene("ContactUs.fxml", "Go Natrue - 21 - Contact Us");
    }
    /**
     * Loads the "Order Visit" screen when the corresponding button is clicked.
     */
    @FXML
    private void loadOrderVisit() {
        switchSceneWithController("OrderVisit.fxml", "Go Natrue - 21 - Order A Visit", 3);
    }
    /**
     * Loads the "Traveler Login" screen when the corresponding button is clicked.
     */
    @FXML
    private void loadTravelerLogin() {
        switchSceneWithController("TravelerLogin.fxml", "Go Natrue - 21 - Traveler Login", 1);
    }
    /**
     * Loads the "Prices" screen when the corresponding button is clicked.
     */
    @FXML
    private void loadPrices() {
        switchSceneWithController("Prices.fxml", "Go Natrue - 21 - Prices", 4);
    }

    /**
     * Loads the "Employee Login" screen when the corresponding button is clicked.
     */
    @FXML
    private void loadEmployeeLogin() {
        switchSceneWithController("EmployeeLogin.fxml", "Go Natrue - 21 - Employee Login", 2);
    }
    /**
     * Switches to a new scene with the specified FXML file and screen title.
     *
     * @param fxmlName    The name of the FXML file to load.
     * @param screenTitle The title of the new screen.
     */
    private void switchScene(String fxmlName, String screenTitle) {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            loader.load();
            Parent p = loader.getRoot();
            Stage newStage = new Stage();
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);
            newStage.setTitle(screenTitle);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load form");
            e.printStackTrace();
        }
    }
    /**
     * Switches to a new scene with the specified FXML file, screen title, and controller.
     *
     * @param fxmlName    The name of the FXML file to load.
     * @param screenTitle The title of the new screen.
     * @param id          The identifier used to determine the controller type.
     */
    private void switchSceneWithController(String fxmlName, String screenTitle, int id) {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            if (id == 1) {
                TravelerLoginController controller = new TravelerLoginController(thisStage);
                loader.setController(controller);
            } else if (id == 2) {
            	EmployeeLoginController controller = new EmployeeLoginController(thisStage);
                loader.setController(controller);
            } else if (id == 3) {
                OrderVisitController controller = new OrderVisitController();
                controller.setOrderFromMain(true);
                loader.setController(controller);
            } else if (id == 4) {
                PricesController controller = new PricesController();
                loader.setController(controller);
            }
            loader.load();
            Parent p = loader.getRoot();
            Stage newStage = new Stage();
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);
            newStage.setTitle(screenTitle);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load form");
            e.printStackTrace();
        }
    }
    /**
     * Retrieves the stage associated with the current window.
     *
     * @return The stage associated with the current window.
     */
    private Stage getStage() {
        return (Stage) orderVisit.getScene().getWindow();
    }
}
