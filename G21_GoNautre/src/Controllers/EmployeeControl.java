/**
 * The EmployeeControl class manages operations related to employees in the system.
 * It includes methods for finding employee details, updating exit time for orders, and handling exit status changes.
 */
package Controllers;
 
import java.util.ArrayList;
import java.util.Arrays;
 
import alerts.Alerts;
import client.ChatClient;
import client.ClientUI;
import javafx.scene.control.Alert.AlertType;
import logic.Employee;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
 
@SuppressWarnings("unchecked")
public class EmployeeControl {
 
    /** Holds the current order. */
    public static Order order = null;
 
    /**
     * Finds the email and password associated with the given employee ID.
     *
     * @param id The ID of the employee.
     * @return An ArrayList containing the email and password of the employee.
     */
    public static ArrayList<String> findEmployeeEmailAndPassword(String id) {
 
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_EMPLOYEE_PASSWORD,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
 
    }
 
    /**
     * Finds the employee details associated with the given employee ID.
     *
     * @param id The ID of the employee.
     * @return The Employee object containing details of the employee.
     */
    public static Employee findEmployeeByID(String id) {
 
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_EMPLOYEE,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return (Employee) ChatClient.responseFromServer.getSuccessSet().get(0);
 
    }
 
    /**
     * Updates the exit time for the given order.
     *
     * @param order    The order for which exit time needs to be updated.
     * @param exitTime The exit time to be set.
     */
    private static void UpdateExitTime(Order order, String exitTime) {
        RequestsFromClientToServer<Order> request = new RequestsFromClientToServer<>(Request.UPDATE_EXIT);
        request.setObj(order);
        request.setInput(exitTime);
        ClientUI.chat.accept(request);
    }
 
    /**
     * Finds the employee ID associated with the given username and password.
     *
     * @param username The username of the employee.
     * @param password The password of the employee.
     * @return The ID of the employee.
     */
    public static String findEmployeeID(String username, String password) {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.GET_EMPLOYEE_ID,
                new ArrayList<String>(Arrays.asList(username, password)));
        ClientUI.chat.accept(request);
        return (String) ChatClient.responseFromServer.getSuccessSet().get(0);
    }
 
    /**
     * Runs the exit status change process for the specified order ID and exit time.
     * If the order is found, updates its exit time, marks it as completed, and adjusts the park's current visitors count.
     * Otherwise, displays an error alert.
     *
     * @param id       The ID of the order.
     * @param exitTime The exit time to be set.
     */
    public static void RunExitStatusChange(String id, String exitTime) {
        order = OrderControl.findOrderForParkExit(id);
        if (order != null) {
            UpdateExitTime(order, exitTime);
            OrderControl.updateOrderStatus(String.valueOf(order.getOrderId()), OrderStatusName.COMPLETED);
            Park park = ParkControl.findParkById(String.valueOf(order.getParkId()));
            ParkControl.changeVisitorsNumber(order.getParkId(),
                    park.getCurrentVisitors() - order.getNumberOfParticipants());
        } else {
            new Alerts(AlertType.ERROR, "Error", "Error",
                    "Could not run RunExitStatusChange").showAndWait();
        }
    }
}