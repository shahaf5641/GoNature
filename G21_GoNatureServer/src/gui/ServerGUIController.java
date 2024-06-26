package gui;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import controllers.DataControl;
import controllers.QueriesConnectionSQL.MysqlConnection;
import server.GoNatureServer;
import server.ServerUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GoNatureConstants;

/**
 * This Class is the GUI controller of ServerGUI.fxml
 * It handles all the JavaFx nodes events.
 * 
 * This is the server configuration screen
 *
 */
public class ServerGUIController {
	
	@FXML
	private Button Importdatabtn;

	@FXML
	private Pane paneMainPane;

	@FXML
	private Button btnStartServer;

	@FXML
	private Label headerLabel;

	@FXML
	private TextArea txtareaLog;

	@FXML
	private TextField txtFldPort;

	@FXML
	private Label lblPort;

	private static server.GoNatureServer srv;

	/**
	 * This function loads the GUI
	 * @param primaryStage The server's stage
	 * @throws Exception If failed to load the GUI
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerGui.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setTitle("G21_Server");
		primaryStage.getIcons().add(new Image(GoNatureConstants.APP_ICON_PATH));
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		/* When the user press close(X) */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				try {
					if (srv != null) {
						srv.stopListening();
						srv.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@FXML
	private void startServer(ActionEvent event) {
		int port = 5555;
		if (!txtFldPort.getText().isEmpty())
			port = Integer.parseInt(txtFldPort.getText());
		try {
			srv = new GoNatureServer(port, this);
			ServerUI.runServer(srv);
		} catch (Exception e) {
			updateTextAreaLog("Server is not up");
		}

	}

	/**
	 * This function updates the text area
	 * 
	 * @param msg The string to add to the text area
	 */
	public void updateTextAreaLog(String msg) {
		if (txtareaLog != null)
			txtareaLog.appendText(msg + "\n");
	}
	
	@FXML
	private void Importdatabtn(MouseEvent event)
	{
		if (DataControl.exportData()==0)
		{
			updateTextAreaLog("Data import succeed");
		}
		else
		{
			updateTextAreaLog("Please empty tables and try again");
		}	
	}
	
}