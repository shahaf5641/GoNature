package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controllers.VerificationControl;
import Controllers.EmployeeControl;
import alerts.Alerts;
import client.ChatClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Employee;
import logic.GoNatureConstants;

/**
 * Controller class for the Employee Login GUI.
 */
public class EmployeeLoginController implements Initializable {

    @FXML
    private javafx.scene.layout.AnchorPane loginContainer;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Label forgotPasswordLable;
    
    @FXML
    private javafx.scene.layout.AnchorPane lockImageContainer;

    @FXML
    private javafx.scene.image.ImageView lockImageView;

    @FXML
    private javafx.scene.layout.AnchorPane personImageContainer;

    @FXML
    private javafx.scene.image.ImageView userImageView;

    @FXML
    private Label createAccountLabel;

    @FXML
    private javafx.scene.control.TextField idTextField;

    @FXML
    private javafx.scene.control.PasswordField passwordTextField;

    @FXML
    private javafx.scene.control.Button loginButton;

    private Stage parentStage;
    public static Employee member;

    /**
     * Constructs an EmployeeLoginController with the specified parent stage.
     * 
     * @param parentStage The parent stage.
     */
    public EmployeeLoginController(Stage parentStage) {
        this.parentStage = parentStage;
    }

    /**
     * Initializes the controller class.
     * 
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    /**
     * Handles the recover password action.
     */
    @FXML
    private void recoverPassword() {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner((Stage) loginButton.getScene().getWindow());
            newStage.setTitle("GoNature21 - Recover Password");
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();

        } catch (Exception e) {
            System.out.println("failed to load form");
            e.printStackTrace();
        }
    }

    /**
     * Handles the login button action.
     */
    @FXML
    private void loginButtonAction() {
        String username = idTextField.getText();
        String pass = passwordTextField.getText();
        if (username.isEmpty() || pass.isEmpty())
            new Alerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill all the fields").showAndWait();
        else {
        	ArrayList<String> userpasslist = new ArrayList<>();
        	userpasslist.add(username);
        	userpasslist.add(pass);
        	String id = EmployeeControl.findEmployeeID(username,pass);
            int res = VerificationControl.userAuthenticationhandler(username, pass, id);
            if (res == 0) {
                member = (Employee) ChatClient.responseFromServer.getSuccessSet().get(0);
                String member_type = member.getJob().getType();
                String fxmlName = member_type.replaceAll("\\s+", ""); 
                switch (fxmlName) {
                    case "DepartmentManager":
                        switchScene("DepartmentManagerScreen.fxml", "GoNature21 - Department Manager", member_type);
                        break;
                    case "ParkManager":
                        switchScene("ParkManager.fxml", "GoNature21 - Park Manager", member_type);
                        break;
                    case "Entrance":
                        switchScene("EntranceEmployee.fxml", "GoNature21 - Entrance Employee", member_type);
                        break;
                    case "Service":
                        switchScene("ServiceEmployee.fxml", "GoNature21 - Service Employee", member_type);
                        break;
                    default:
                        break;
                }
            } else if (res == 1) {
                new Alerts(AlertType.ERROR, "Login Error", "Login Error", "This employee is already logged in!")
                        .showAndWait();
            } else {
                new Alerts(AlertType.ERROR, "Login Error", "Login Error", "Incorrect username or password!")
                        .showAndWait();
            }
        }

    }

    /**
     * Retrieves the stage.
     * 
     * @return The stage.
     */
    private Stage getStage() {
        return (Stage) loginButton.getScene().getWindow();
    }

    /**
     * Switches scene based on the employee type.
     * 
     * @param fxmlName The name of the FXML file.
     * @param title    The title of the stage.
     * @param type     The type of the employee.
     */
    private void switchScene(String fxmlName, String title, String type) {
        try {
            Stage thisStage = getStage();
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            if (type.equals("Service")) {
                ServiceEmployeeController controller = new ServiceEmployeeController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            } else if (type.equals("Park Manager")) {
                ParkManagerController controller = new ParkManagerController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            } else if (type.equals("Entrance")) {
                EntranceEmployeeController controller = new EntranceEmployeeController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            } else if (type.equals("Department Manager")) {
                DepartmentManagerController controller = new DepartmentManagerController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            }
            loader.load();
            Parent p = loader.getRoot();
            newStage.setTitle(title);
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
            newStage.show();
            thisStage.close();
            parentStage.hide();
        } catch (Exception e) {}
    }}
        