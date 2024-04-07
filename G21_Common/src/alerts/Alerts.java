package alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * CustomAlerts provides a way to create customized alerts with different icons
 * and styles based on the alert type.
 *
 */
public class Alerts extends Alert {

	// Paths to the icons for different types of alerts

	final String errorIcon = "/resources/images/errorIcon.png";
	final String warningIcon = "/resources/images/warningIcon.png";
	final String informationIcon = "/resources/images/infoIcon.png";

	// Constructor for creating custom alerts
	public Alerts(AlertType alertType, String title, String header, String content) {
		super(alertType);
		this.setTitle(title);
		this.setHeaderText(header);
		this.setContentText(content);
		setAlertStyle(); // Set up the style and icon based on the alert type
	}

	// Method to set the style and icon based on the alert type
	private void setAlertStyle() {
		String alertType = this.getAlertType().toString();
		if (alertType.equals("ERROR"))
			setupErrorIcon();
		else if (alertType.equals("WARNING"))
			setupWarningIcon();
		else if (alertType.equals("INFORMATION"))
			setupInfoIcon();

	}

	// Method to set up the error icon and style
	private void setupErrorIcon() {
		DialogPane pane = this.getDialogPane();
		((Stage) pane.getScene().getWindow()).getIcons().add(new Image(errorIcon));
		pane.getStylesheets().add(getClass().getResource("Error_CSS.css").toExternalForm());
	}

	// Method to set up the warning icon and style
	private void setupWarningIcon() {
		DialogPane pane = this.getDialogPane();
		((Stage) pane.getScene().getWindow()).getIcons().add(new Image(warningIcon));
		pane.getStylesheets().add(getClass().getResource("Warning_CSS.css").toExternalForm());
	}

	// Method to set up the information icon and style
	private void setupInfoIcon() {
		DialogPane pane = this.getDialogPane();
		((Stage) pane.getScene().getWindow()).getIcons().add(new Image(informationIcon));
		((Stage) pane.getScene().getWindow()).getIcons().add(new Image(warningIcon));
		pane.getStylesheets().add(getClass().getResource("Info_CSS.css").toExternalForm());
	}

}
