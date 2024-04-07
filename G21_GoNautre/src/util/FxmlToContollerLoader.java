package util;

import java.io.IOException;

import gui.GenerateReportsController;
import gui.DepartmentManagerReportsController;
import gui.TravelerManagementController;
import gui.OrderVisitController;
import gui.ParkParamsController;
import gui.ProfileController;
import gui.ShowTravelerOrders;
import gui.UpdateParamsController;
import gui.ShowMessagesController;
import gui.ShowChangesRequestsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * FxmlUtil is a utility class to load scenes
 *
 */
public class FxmlToContollerLoader {
	private Pane rootPane;
	private boolean IsEmployee; // true if it is employee

	/**
	 * This function loads the fxml and connect it to it's controller
	 * 
	 * @param FxmlName - fxml to load
	 * @param ContollerName - the fxml's controller
	 * @return rootPane
	 */
	public Pane PaneControllerToFxml(String FxmlName, String ContollerName) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlName));
		if (ContollerName.equals("OrderVisit")) {
			OrderVisitController controller = new OrderVisitController();
			loader.setController(controller);
		} else if (ContollerName.equals("profile")) {
			ProfileController controller = new ProfileController();
			controller.setEmployee(IsEmployee);
			loader.setController(controller);
		} else if (ContollerName.equals("Travelermng")) {
			TravelerManagementController controller = new TravelerManagementController();
			loader.setController(controller);
		} else if (ContollerName.equals("ParkParams")) {
			ParkParamsController controller = new ParkParamsController();
			loader.setController(controller);
		} else if (ContollerName.equals("TravelerOrders")) {
			ShowTravelerOrders controller = new ShowTravelerOrders();
			loader.setController(controller);
		} else if (ContollerName.equals("TravelerMessages")) {
			ShowMessagesController controller = new ShowMessagesController();
			loader.setController(controller);
		} else if (ContollerName.equals("showRequests")) {
			ShowChangesRequestsController controller = new ShowChangesRequestsController();
			loader.setController(controller);
		} else if (ContollerName.equals("UpdateParams")) {
			UpdateParamsController controller = new UpdateParamsController();
			loader.setController(controller);
		} else if (ContollerName.equals("GenerateReport")) {
			GenerateReportsController controller = new GenerateReportsController();
			loader.setController(controller);
		}
		 else if (ContollerName.equals("DPMReports")) {
			 DepartmentManagerReportsController controller = new DepartmentManagerReportsController();
				loader.setController(controller);
			}
		try {
			loader.load();
			rootPane = loader.getRoot();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rootPane;

	}

	/**
	 * Setter for the class variable IsEmployee
	 * 
	 * @param IsEmployee True if this screen is a employee screen
	 */
	public void setWorker(boolean IsEmployee) {
		this.IsEmployee = IsEmployee;
	}

}