package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for the loading screen window.
 * This class is responsible for displaying a loading indicator while the application is loading.
 
 */
public class LoadingScreenController {

    @FXML
    private AnchorPane LoadingPane;

    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the progress indicator to show indeterminate progress.
     */
    public void initialize() {
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    /**
     * Retrieves the loading pane associated with this controller.
     * 
     * @return The loading pane associated with this controller.
     */
    public AnchorPane getLoadingPane() {
        return LoadingPane;
    }

    /**
     * Retrieves the progress indicator associated with this controller.
     * 
     * @return The progress indicator associated with this controller.
     */
    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
}
