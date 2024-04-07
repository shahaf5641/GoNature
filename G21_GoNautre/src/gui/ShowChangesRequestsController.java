package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Controllers.ParkControl;
import Controllers.ManagementRequestControl;
import alerts.Alerts;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.OrderStatusName;
import logic.Request;

/**
 * Controller for the view requests for changes UI.
 * Allows park managers to view, confirm, or decline parameter change requests for parks.
 */
@SuppressWarnings("unchecked")
public class ShowChangesRequestsController implements Initializable {

	@FXML
	private Label headerLabel;

	@FXML
	private TableView<Request> parametersTableView;

	@FXML
	private TableColumn<Request, Integer> parametersIdTableColumn;

	@FXML
	private TableColumn<Request, String> parkIDTableColumn;
	@FXML
	private Button confirmRequestButton;

	@FXML
	private Button cancelRequestButton;

	@FXML
	private TableColumn<Request, String> newValueTableColumn;
	@FXML
	private TableColumn<Request, String> oldValueTableColumn;

	@FXML
	private TableColumn<Request, String> typeTableColumn;


	@FXML
	private TableColumn<Request, String> parametersStatusTableColumn;
    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file has been loaded.
     */

	@Override
	public void initialize(URL firstarg, ResourceBundle secondarg) {

		loadChanges();
	}

	 /**
     * Handles the action to confirm a request.
     * Changes the status of the selected request to confirmed and updates park parameters accordingly.
     */
	@FXML
	void confirmRequestButton() {
		ArrayList<String> changeParkParameterList = new ArrayList<>();
		TableViewSelectionModel<Request> request = null;
			request = parametersTableView.getSelectionModel();
			Request r = request.getSelectedItem();
			if (r!=null)
			{
			if (r.getRequestStatus().equals(OrderStatusName.PENDING.toString())) {
				ManagementRequestControl.updateRequestStatus(r.getRequestId(), true);
				changeParkParameterList.add(r.getChangeParamName());
				changeParkParameterList.add(r.getNewParamValue());
				System.out.println(r.getParkId());
				changeParkParameterList.add(String.valueOf(r.getParkId()));
				new Alerts(AlertType.INFORMATION, "Sent", "Sent",
						"Request " + r.getChangeParamName() + " was confirmed with value " + r.getNewParamValue()).showAndWait();
				ParkControl.updateParkParameters(changeParkParameterList);
			}

			else {
				new Alerts(AlertType.INFORMATION, "Error", "Not sent", "It's not possible to alter the status unless it's 'pending'.")
						.showAndWait();
			}
			}
			else
			{
				new Alerts(AlertType.ERROR, "Error", "Try again", "Please select request")
				.showAndWait();
			}
		loadChanges();
	}
	  /**
     * Handles the action to decline a request.
     * Changes the status of the selected request to declined.
     */


	@FXML
	void cancelRequestButton() {
		TableViewSelectionModel<Request> request = null;
			request = parametersTableView.getSelectionModel();
			Request r = request.getSelectedItem();
			if (r!=null)
			{
			if (r.getRequestStatus().equals(OrderStatusName.PENDING.toString())) {
				ManagementRequestControl.updateRequestStatus(r.getRequestId(), false);
				new Alerts(AlertType.INFORMATION, "Sent", "Sent",
						"Request " + r.getChangeParamName() + " was declined with value " + r.getNewParamValue()).showAndWait();
			} else {
				new Alerts(AlertType.INFORMATION, "Error", "Not sent", "cannot change status that is not 'pending'")
						.showAndWait();
			}
			}
			else
			{
				new Alerts(AlertType.ERROR, "Error", "Try again", "Please select request")
				.showAndWait();
				
			}
			
		loadChanges();
	}
	
	 /**
     * Loads the changes requests from the server and populates the table view with these requests.
     */
	void loadChanges() {
		parametersTableView.setTooltip(new Tooltip("click on a row to select it"));
		ManagementRequestControl.displayActiveRequests();
		parametersIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
		parkIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("parkId"));
		typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("changeParamName"));
		parametersStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
		newValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("newParamValue"));
		oldValueTableColumn.setCellValueFactory(new PropertyValueFactory<>("oldParamValue"));
		ObservableList<Request> requests = FXCollections.observableArrayList();
		requests.addAll(ChatClient.responseFromServer.getSuccessSet());
		parametersTableView.setItems(requests);
	}
}