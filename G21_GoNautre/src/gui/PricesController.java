package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import logic.GoNatureConstants;

/**
 * Controller for managing the display of prices in the GUI.
 * It updates a label with the current ticket price from a constant value.
 */

public class PricesController implements Initializable {
	  @FXML
	    private Label lblPrice;
	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the FXML file has been loaded.
	     *
	     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
	     * @param resources The resources used to localize the root object, or null if the root object was not localized.
	     */

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		init();
	}
	 /**
     * Initializes the price label with the full ticket price.
     * The value is retrieved from the {@link GoNatureConstants} class.
     */
	private void init() {
		lblPrice.setText(String.valueOf(GoNatureConstants.FULL_TICKET_PRICE));
	}
}

