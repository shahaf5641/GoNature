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
import logic.GoNatureFinals;

/**
 * This Class is the GUI controller of MainScreenMenu.fxml
 * It handles all the JavaFx nodes events.
 * 
 * This is the menu in the main screen
 *
 */
public class MainScreenMenuController implements Initializable {
	@FXML
	private Button orderVisit;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    @FXML
    private void loadContactUs() {
        switchScene("ContactUs.fxml", "GoNature8 - Contact Us");
    }

    @FXML
    private void loadMemberLogin() {
        switchSceneWithController("MemberLogin.fxml", "GoNature8 - Member Login", 2);
    }

    @FXML
    private void loadTravelerLogin() {
        switchSceneWithController("TravelerLogin.fxml", "GoNature8 - Traveler Login", 1);
    }

    @FXML
    private void loadOrderVisit() {
        switchSceneWithController("OrderVisit.fxml", "GoNature8 - Order A Visit", 3);
    }

    @FXML
    private void loadPrices() {
        switchSceneWithController("Prices.fxml", "GoNature8 - Prices", 4);
    }

    private void switchScene(String fxmlName, String screenTitle) {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            loader.load();
            Parent p = loader.getRoot();
            Stage newStage = new Stage();

            /* Block parent stage until child stage closes */
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);

            newStage.setTitle(screenTitle);
            newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load form");
            e.printStackTrace();
        }
    }

    private void switchSceneWithController(String fxmlName, String screenTitle, int id) {
        try {
            Stage thisStage = getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            if (id == 1) {
                TravelerLoginController controller = new TravelerLoginController(thisStage);
                loader.setController(controller);
            } else if (id == 2) {
                MemberLoginController controller = new MemberLoginController(thisStage);
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

            /* Block parent stage until child stage closes */
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(thisStage);

            newStage.setTitle(screenTitle);
            newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            System.out.println("Failed to load form");
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) orderVisit.getScene().getWindow();
    }
}
