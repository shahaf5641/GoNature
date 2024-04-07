 
package Controllers;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
 
import client.ChatClient;
import client.ClientUI;
import logic.Park;
import logic.OrderTable;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
 
@SuppressWarnings("unchecked")
public class ParkControl {
 
    /**
     * Finds the park ID associated with a given order ID from a list of orders.
     *
     * @param orders  The list of orders.
     * @param orderId The ID of the order to find the associated park ID for.
     * @return The park ID associated with the given order ID, or -1 if not found.
     */
    public static int findParkIdByOrderId(ArrayList<OrderTable> orders, int orderId) {
        for (OrderTable o : orders)
            if (o.getOrderId() == orderId)
                return o.getParkId();
        return -1;
    }
 
    /**
     * Updates the current number of visitors for a park.
     *
     * @param pId The ID of the park.
     * @param num The new number of visitors.
     */
    public static void changeVisitorsNumber(int pId, int num) {
        String cVisitors = String.valueOf(num);
        String parkId = String.valueOf(pId);
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<String>(Request.UPDATE_CURRENT_VISITORS,
                new ArrayList<String>(Arrays.asList(cVisitors, parkId)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Finds a park by its name.
     *
     * @param parkName The name of the park to find.
     * @return The Park object corresponding to the given name.
     */
    public static Park findParkByName(String parkName) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_PARK_BY_NAME,
                new ArrayList<String>(Arrays.asList(parkName)));
        ClientUI.chat.accept(request);
        return (Park) ChatClient.responseFromServer.getSuccessSet().get(0);
    }
 
    /**
     * Retrieves the names of all parks.
     *
     * @return An ArrayList containing the names of all parks.
     */
    public static ArrayList<String> findParksNames() {
        ArrayList<Park> parks = findAllParks();
        ArrayList<String> parksNames = new ArrayList<String>();
        for (Park park : parks) {
            parksNames.add(park.getParkName());
        }
        return parksNames;
    }
 
    /**
     * Finds the name of a park given its ID.
     *
     * @param parkId The ID of the park.
     * @return The name of the park corresponding to the given ID.
     */
    public static String findParkName(String parkId) {
        Park park = findParkById(parkId);
        if (park != null)
            return park.getParkName();
        return "Park";
    }
 
    /**
     * Sets the full status of a park and logs the date and time when it became full.
     *
     * @param park The Park object representing the park to set the status for.
     */
    public static void setParkFullStatus(Park park) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
        String date = dateAndTime.split(" ")[0];
 
        String comment = park.getParkName() + " was full at: " + dateAndTime;
 
        String parkId = String.valueOf(park.getParkId());
        String maxVisitors = String.valueOf(park.getMaxVisitors());
 
        if (park.getCurrentVisitors() >= park.getMaxVisitors()) {
            RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.INSERT_FULL_PARK_DATE,
                    new ArrayList<String>(Arrays.asList(parkId, date, maxVisitors, comment)));
            ClientUI.chat.accept(request);
        }
    }
 
    /**
     * Finds a park by its ID.
     *
     * @param parkId The ID of the park to find.
     * @return The Park object corresponding to the given ID.
     */
    public static Park findParkById(String parkId) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_PARK_BY_ID,
                new ArrayList<String>(Arrays.asList(parkId)));
        ClientUI.chat.accept(request);
        return (Park) ChatClient.responseFromServer.getSuccessSet().get(0);
    }
 
    /**
     * Retrieves a list of all parks.
     *
     * @return An ArrayList containing all parks.
     */
    public static ArrayList<Park> findAllParks() {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_ALL_PARKS);
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Updates the parameters of a park.
     *
     * @param changeParkParameterList The list of parameters to update.
     */
    public static void updateParkParameters(ArrayList<String> changeParkParameterList) {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.CHANGE_PARK_PARAMS,
                changeParkParameterList);
        ClientUI.chat.accept(request);
    }
 
    /**
     * Checks if a park is at full capacity on a specific date.
     *
     * @param date   The date to check for full capacity.
     * @param parkID The ID of the park to check.
     * @return An ArrayList containing details about the park's full capacity status on the specified date.
     */
    public static ArrayList<String> checkParkFullCapacityAtDate(String date, String parkID) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.CHECK_PARK_FULL_DATE,
                new ArrayList<String>(Arrays.asList(date, parkID)));
        ClientUI.chat.accept(request);
        return (ArrayList<String>) ChatClient.responseFromServer.getSuccessSet();
    }
}