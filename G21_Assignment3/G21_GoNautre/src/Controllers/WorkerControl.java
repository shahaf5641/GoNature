package Controllers;
import java.util.ArrayList;
import client.ChatClient;
import client.ClientUI;
import gui.ManageTravelerController;
import logic.ClientToServerRequest;
import logic.ClientToServerRequest.Request;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;

import java.util.Arrays;

import logic.Employees;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import logic.ClientToServerRequest.Request;

/**
 * WorkerControl class handles all the worker related functionalities
 */
@SuppressWarnings("unchecked")
public class WorkerControl {
	public static Order order = null;

	/**
	 * This function get an employee's id and returns Employee object,
	 * or null if there is no Employee with the given id.
	 * 
	 * @param id The employee's id.
	 * @return Employee object or null.
	 */
	public static Employees getEmployeeByID(String id) {

		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_EMPLOYEE,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Employees employee = (Employees) ChatClient.responseFromServer.getResultSet().get(0);
		return employee;

	}
	
	public static String getEmployeeId(String username, String password)
	{
		ClientToServerRequest<?> request = new ClientToServerRequest<>(Request.GET_EMPLOYEEID,
				new ArrayList<String>(Arrays.asList(username,password)));
		ClientUI.chat.accept(request);
		String id = (String) ChatClient.responseFromServer.getResultSet().get(0);
		return id;
	}

	/**
	 * This function gets an id of employee and return his email and password in array list.
	 * 
	 * @param id The employee's id
	 * @return ArrayList. at index 0 the employee's email, at index 1 the employee's password.
	 */
	public static ArrayList<String> getEmployeeEmailAndPassword(String id) {

		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_EMPLOYEE_PASSWORD,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		ArrayList<String> employeeInfo = ChatClient.responseFromServer.getResultSet();
		return employeeInfo;

	}
	
	/**
	 * This function runs all the actions that needs to happened when exiting the park.
	 * Update exit time in the database.
	 * Update park's current visitors.
	 * 
	 * @param id The id of the exiting traveler.
	 * @param exitTime The traveler exit time
	 * @param cardReaderController  The GUI controller to update.
	 */
	public static void executeExitSequence(String id, String exitTime) {
		order = OrderControl.getRelevantOrder_ParkExit(id);
		if (order != null) {
			updateVisitExitTime(order, exitTime);
			OrderControl.changeOrderStatus(String.valueOf(order.getOrderId()), OrderStatusName.COMPLETED);
			Park park = ParkControl.getParkById(String.valueOf(order.getParkId()));
			ParkControl.updateCurrentVisitors(order.getParkId(),
					park.getCurrentVisitors() - order.getNumberOfParticipants());
		}
		else
		{
			System.out.println("NEED TO ALERT");
		}
	}
	

	/**
	 * This function update visit exit time.
	 * This function is only to update exit visit time using the card reader simulator
	 * It's update the exit time artificially.
	 * 
	 * @param order the order to update.
	 */
	private static void updateVisitExitTime(Order order, String exitTime) {
		ClientToServerRequest<Order> request = new ClientToServerRequest<>(Request.UPDATE_EXIT_TIME);
		request.setObj(order);
		request.setInput(exitTime);
		ClientUI.chat.accept(request);
	}
}
	

