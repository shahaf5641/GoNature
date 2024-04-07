 
package Controllers;
 
import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.RequestsFromClientToServer;
import logic.Report;
import logic.RequestsFromClientToServer.Request;
import logic.Order;
 
@SuppressWarnings("unchecked")
public class ManagementReportControl {
 
    /**
     * Deletes the specified report.
     *
     * @param r The report to delete.
     */
    private static void deleteReport(Report r) {
        RequestsFromClientToServer<Report> request = new RequestsFromClientToServer<>(Request.DELETE_REPORT,
                new ArrayList<Report>(Arrays.asList(r)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves and displays the count of visitors in groups for the specified month.
     *
     * @param month The month for which to count group visits.
     */
    public static void visitTimeGroupCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_VISIT_GROUP,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves all reports.
     *
     * @return An ArrayList containing all reports.
     */
    public static ArrayList<Report> findReports() {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_REPORTS,
                new ArrayList<String>(Arrays.asList()));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Retrieves and displays the count of solo visitors for the specified month.
     *
     * @param month The month for which to count solo visits.
     */
    public static void enterTimeSoloCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_ENTER_SOLO,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves and displays the count of guided visitors for the specified month.
     *
     * @param month The month for which to count guided visits.
     */
    public static void enterTimeGuideCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_ENTER_GUIDES,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves and displays the count of visitors in groups for the specified month.
     *
     * @param month The month for which to count group visits.
     */
    public static void enterTimeGroupCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_ENTER_GROUP,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves and displays the count of solo visitors for the specified month.
     *
     * @param month The month for which to count solo visits.
     */
    public static void visitTimeSoloCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_VISIT_SOLO,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves and displays the count of guided visitors for the specified month.
     *
     * @param month The month for which to count guided visits.
     */
    public static void visitTimeGuideCount(int month) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_VISIT_GUIDES,
                new ArrayList<String>(Arrays.asList(String.valueOf(month))));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Displays the report based on the provided list of requests.
     *
     * @param arrayOfRequests The list of requests to generate the report.
     */
    public static void DisplayReport(ArrayList<String> arrayOfRequests) {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.MANAGER_REPORT, arrayOfRequests);
        ClientUI.chat.accept(request);
    }
 
    /**
     * Inserts a report into the database.
     *
     * @param monthAndType The month and type of report to insert.
     */
    public static void InsertReportDB(ArrayList<String> monthAndType) {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.ADD_REPORT_DB, monthAndType);
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves pending orders after the specified date for the specified park and month.
     *
     * @param parkID The ID of the park.
     * @param month  The month for which to retrieve pending orders.
     * @return An ArrayList containing the IDs of pending orders.
     */
    public static ArrayList<Integer> getParkPendingDatePassed(int parkID, int month) {
        RequestsFromClientToServer<Integer> request = new RequestsFromClientToServer<>(Request.GET_PENDING_AFTER_DATE,
                new ArrayList<Integer>(Arrays.asList(parkID, month)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Retrieves the count of solo visitors for the specified day and month.
     *
     * @param month The month for which to count solo visits.
     * @param day   The day for which to count solo visits.
     */
    public static void visitTimeSoloCountDays(int month, String day) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_VISIT_SOLOS_DAYS,
                new ArrayList<String>(Arrays.asList(String.valueOf(month), day)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves the count of group visitors for the specified day and month.
     *
     * @param month The month for which to count group visits.
     * @param day   The day for which to count group visits.
     */
    public static void visitTimeGroupsCountDays(int month, String day) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_VISIT_GROUPS_DAYS,
                new ArrayList<String>(Arrays.asList(String.valueOf(month), day)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves orders for solo visitors based on the specified month and park ID.
     *
     * @param monthNumber The month for which to retrieve orders.
     * @param parkID      The ID of the park.
     * @return An ArrayList containing orders for solo visitors.
     */
    public static ArrayList<Order> findSoloVisitorsReportOrders(int monthNumber, int parkID) {
        RequestsFromClientToServer<Integer> request = new RequestsFromClientToServer<>(Request.GET_SOLO_ORDERS,
                new ArrayList<Integer>(Arrays.asList(monthNumber, parkID)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Retrieves orders for group visitors based on the specified month and park ID.
     *
     * @param monthNumber The month for which to retrieve orders.
     * @param parkID      The ID of the park.
     * @return An ArrayList containing orders for group visitors.
     */
    public static ArrayList<Order> findGroupVisitorsReportOrders(int monthNumber, int parkID) {
        RequestsFromClientToServer<Integer> request = new RequestsFromClientToServer<>(Request.GET_GROUPS_ORDERS,
                new ArrayList<Integer>(Arrays.asList(monthNumber, parkID)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Retrieves the count of solo visitors for the specified day and month.
     *
     * @param month The month for which to count solo visits.
     * @param day   The day for which to count solo visits.
     */
    public static void enterTimeSoloCountDays(int month, String day) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(
                Request.COUNT_ENTER_SOLOS_DAYS,
                new ArrayList<String>(Arrays.asList(String.valueOf(month), day)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves the count of guided visitors for the specified day and month.
     *
     * @param month The month for which to count guided visits.
     * @param day   The day for which to count guided visits.
     */
    public static void enterTimeGuideCountDays(int month, String day) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.COUNT_ENTER_GUIDES,
                new ArrayList<String>(Arrays.asList(String.valueOf(month), day)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves the count of group visitors for the specified day and month.
     *
     * @param month The month for which to count group visits.
     * @param day   The day for which to count group visits.
     */
    public static void enterTimeGroupsCountDays(int month, String day) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(
                Request.COUNT_ENTER_GROUP,
                new ArrayList<String>(Arrays.asList(String.valueOf(month), day)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Retrieves canceled orders for the specified park, start date, and end date.
     *
     * @param parkID    The ID of the park.
     * @param startdate The start date.
     * @param enddate   The end date.
     * @return An ArrayList containing the IDs of canceled orders.
     */
    public static ArrayList<Integer> findParkCancels(String parkID, String startdate, String enddate) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_CANCELS,
                new ArrayList<String>(Arrays.asList(parkID, startdate, enddate)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Inserts a report into the database.
     *
     * @param r The report to insert.
     * @return True if the report is successfully inserted, false otherwise.
     */
    public static boolean InsertReport(Report r) {
        deleteReport(r);
        RequestsFromClientToServer<Report> request = new RequestsFromClientToServer<>(Request.INSERT_REPORT,
                new ArrayList<Report>(Arrays.asList(r)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.isSuccess();
    }
 
    /**
     * Retrieves orders for visitors who haven't arrived for the specified park, start date, and end date.
     *
     * @param parkID    The ID of the park.
     * @param startdate The start date.
     * @param enddate   The end date.
     * @return An ArrayList containing the IDs of orders for visitors who haven't arrived.
     */
    public static ArrayList<Integer> findParkNotArrived(String parkID, String startdate, String enddate) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_NOTARRIVED,
                new ArrayList<String>(Arrays.asList(parkID, startdate, enddate)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
}
 