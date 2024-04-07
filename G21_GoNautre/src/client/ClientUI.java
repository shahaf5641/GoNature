package client;

//import client.ClientController;
import gui.ClientSettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import logic.GoNatureConstants;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class ClientUI extends Application {
	public static ClientController chat;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/clientConfiguration.fxml"));
			ClientSettingsController controller = new ClientSettingsController();
			loader.setController(controller);
			controller.setStage(primaryStage);
			loader.load();
			Parent p = loader.getRoot();
			primaryStage.setTitle("GoNature Client Set UP");
			primaryStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
			primaryStage.setScene(new Scene(p));
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}