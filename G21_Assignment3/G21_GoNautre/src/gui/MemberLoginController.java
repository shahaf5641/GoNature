
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.AutenticationControl;
import Controllers.WorkerControl;
import alerts.CustomAlerts;
import client.ChatClient;
import client.ClientUI;
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
import logic.ClientToServerRequest;
import logic.Employees;
import logic.GoNatureFinals;
import logic.ClientToServerRequest.Request;

public class MemberLoginController implements Initializable {

    @FXML
    private javafx.scene.layout.AnchorPane loginContainer;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Label forgotPasswordLable;

    @FXML
    private javafx.scene.layout.AnchorPane personImageContainer;

    @FXML
    private javafx.scene.image.ImageView userImageView;

    @FXML
    private javafx.scene.layout.AnchorPane lockImageContainer;

    @FXML
    private javafx.scene.image.ImageView lockImageView;

    @FXML
    private Label createAccountLabel;

    @FXML
    private javafx.scene.control.TextField idTextField;

    @FXML
    private javafx.scene.control.PasswordField passwordTextField;

    @FXML
    private javafx.scene.control.Button loginButton;

    private Stage parentStage;
    public static Employees member; // Alon 12.12.20

    public MemberLoginController(Stage parentStage) {
        this.parentStage = parentStage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private void recoverPassword() {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecoverPassword.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            /* Block parent stage until child stage closes */
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner((Stage) loginButton.getScene().getWindow());
            newStage.setTitle("GoNature21 - Recover Password");
            newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.show();

        } catch (Exception e) {
            System.out.println("faild to load form");
            e.printStackTrace();
        }
    }

    @FXML
    private void loginButtonAction() {
        String username = idTextField.getText();
        String pass = passwordTextField.getText();
        if (username.isEmpty() || pass.isEmpty())
            new CustomAlerts(AlertType.ERROR, "Input Error", "Input Error", "Please fill all the fields").showAndWait();
        else {
        	ArrayList<String> userpasslist = new ArrayList<>();
        	userpasslist.add(username);
        	userpasslist.add(pass);
        	String id = WorkerControl.getEmployeeId(username,pass);
            int res = AutenticationControl.memberLoginHandler(username, pass, id);
            if (res == 0) {
                member = (Employees) ChatClient.responseFromServer.getResultSet().get(0);
                String member_type = member.getRole().getStr();
                String fxmlName = member_type.replaceAll("\\s+", ""); // Trimming all white spaces.
                switch (fxmlName) {
                    case "DepartmentManager":
                        switchScene("DepartmentManagerScreen.fxml", "GoNature21 - Department Manager", member_type);
                        break;
                    case "ParkManager":
                        switchScene("ParkManager.fxml", "GoNature21 - Park Manager", member_type);
                        break;
                    case "Entrance":
                        switchScene("EntranceWorker.fxml", "GoNature21 - Entrance Worker", member_type);
                        break;
                    case "Service":
                        switchScene("ServiceWorker.fxml", "GoNature21 - Service Worker", member_type);
                        break;
                    default:
                        break;
                }
            } else if (res == 1) {
                new CustomAlerts(AlertType.ERROR, "Login Error", "Login Error", "This employee is already logedin!")
                        .showAndWait();
            } else {
                new CustomAlerts(AlertType.ERROR, "Login Error", "Login Error", "Incorrect id or password!")
                        .showAndWait();
            }
        }

    }

    private void switchScene(String fxmlName, String title, String type) {
        try {
            Stage thisStage = getStage();
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            if (type.equals("Service")) {
                ServiceWorkerController controller = new ServiceWorkerController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            } else if (type.equals("Park Manager")) {
                ParkManagerController controller = new ParkManagerController();
                controller.setStage(newStage);
                controller.setMainScreenStage(parentStage);
                loader.setController(controller);
            } else if (type.equals("Entrance")) {
                EntranceWorkerController controller = new EntranceWorkerController();
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
            Parent p = loader

.getRoot();
            newStage.setTitle(title);
            newStage.setScene(new Scene(p));
            newStage.setResizable(false);
            newStage.getIcons().add(new Image(GoNatureFinals.APP_ICON));
            newStage.show();
            thisStage.close();
            parentStage.hide();
        } catch (Exception e) {
            System.out.println("faild to load form");
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) loginButton.getScene().getWindow();
    }

}
