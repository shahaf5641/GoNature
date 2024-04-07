package gui;

import java.net.URL;
import java.util.ResourceBundle;
import Controllers.ParkControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.EmployeeType;

/**
 * Controller for managing the profile view. It displays different information based on the user type (employee or traveler).
 */
public class ProfileController implements Initializable {

	@FXML
	private Label headerLabel;
	@FXML
	private Label profileNameLabel;
	@FXML
	private Label profileLastNameLabel;
	@FXML
	private Label profileParkLabel;
	@FXML
	private Label parkLabel;
	@FXML
	private Label profileIDLabel;
	@FXML
	private Label profileAccountTypeLabel;
	@FXML
	private Label ProfileEmailLabel;

	private boolean isemployee;
	 /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. It initializes the profile view.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init();
	}

    /**
     * Initializes the profile information display by calling the method to load user-specific information.
     */
	private void init() {
		loadInformation();
	}

	  /**
     * Loads the information to be displayed in the profile view based on the user type.
     * If the user is an employee, it displays employee information.
     * If the user is a traveler, it displays traveler information.
     */
	private void loadInformation() {
	
		if (isemployee) {
			EmployeeType role = EmployeeLoginController.member.getJob();
			profileNameLabel.setText(EmployeeLoginController.member.getFirstName());
			profileLastNameLabel.setText(EmployeeLoginController.member.getLastName());
			profileIDLabel.setText(String.valueOf(EmployeeLoginController.member.getEmployeeId()));
			ProfileEmailLabel.setText(EmployeeLoginController.member.getEmail());
			String parkId = String.valueOf(EmployeeLoginController.member.getParkId());
			profileParkLabel.setText(ParkControl.findParkName(parkId));
			profileAccountTypeLabel.setText(role.getType());
			if (role.equals(EmployeeType.DEPARTMENT_MANAGER) || role.equals(EmployeeType.SERVICE)) { 
				profileParkLabel.setVisible(false);
				parkLabel.setVisible(false);
			}
		}

		
		else {
			profileNameLabel.setText(TravelerLoginController.traveler.getFirstName());
			profileLastNameLabel.setText(TravelerLoginController.traveler.getLastName());
			profileIDLabel.setText(TravelerLoginController.traveler.getTravelerId());
			ProfileEmailLabel.setText(TravelerLoginController.traveler.getEmail());
			profileParkLabel.setVisible(false);
			parkLabel.setVisible(false);
			profileAccountTypeLabel.setText("Guest");

		}
	}
	 /**
     * Sets the flag indicating whether the profile belongs to a employee (employee) or not (traveler).
     * 
     * @param isemployee true if the profile is for an employee, false if for a traveler.
     */

	
	public void setEmployee(boolean isemployee) {
		this.isemployee = isemployee;
	}


}