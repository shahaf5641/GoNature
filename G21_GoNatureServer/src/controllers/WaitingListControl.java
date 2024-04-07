package controllers;
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
 
import controllers.QueriesConnectionSQL.OrderQueries;
import controllers.QueriesConnectionSQL.ParkQueries;
import controllers.QueriesConnectionSQL.TravelersQueries;
import logic.Order;
import logic.Park;
import logic.OrderStatusName;
import resources.MsgTemplates;
import server.GoNatureServer;
 
/**
 * Manages the waiting list travelers in the system.
 */
public class WaitingListControl {
 
    private static OrderQueries orderQueries = new OrderQueries(GoNatureServer.mysqlconnection);
    private static ParkQueries parkQueries = new ParkQueries(GoNatureServer.mysqlconnection);
    private static TravelersQueries travelerQueries = new TravelersQueries(GoNatureServer.mysqlconnection);
 
    /**
     * Sends a notification to the next traveler in the waiting list.
     * 
     * @param order The order to notify.
     */
    private static void sendWaitingListNotification(Order order) {
        // Get current date and time
        String dateAndTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
 
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
        String travelerId = order.getTravelerId();
        int orderId = order.getOrderId();
        Park park = parkQueries.getParkById(new ArrayList<String>(Arrays.asList(String.valueOf(order.getParkId()))));
        String subject = MsgTemplates.waitingListPlaceInPark[0].toString();
        String content = String.format(MsgTemplates.waitingListPlaceInPark[1].toString(), park.getParkName(),
                order.getOrderDate(), order.getOrderTime());
 
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        travelerQueries.sendMessageToTraveler(parameters);
    }
 
    /**
     * Finds the next order in the waiting list for a specific date and hour.
     * 
     * @param date The date.
     * @param hour The hour.
     * @param park The park.
     * @return The next order in the waiting list, or null if none found.
     */
    private static Order findNextOrderInWaitingList(String date, String hour, Park park) {
        String parkId = String.valueOf(park.getParkId());
        String maxVisitors = String.valueOf(park.getMaxVisitors());
        String estimatedStayTime = String.valueOf(park.getEstimatedStayTime());
        String gap = String.valueOf(park.getGapBetweenMaxAndCapacity());
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(parkId, maxVisitors, estimatedStayTime, date, hour, gap));
        ArrayList<Order> matchingOrders = orderQueries.findMatchingOrdersInWaitingList(parameters);
        return matchingOrders.isEmpty() ? null : matchingOrders.get(0);
    }
 
    /**
     * Notifies the next traveler in the waiting list when an order is canceled.
     * 
     * @param cancelledOrder The cancelled order.
     */
    public static void notifyNextPersonInWaitingList(Order cancelledOrder) {
        Park park = parkQueries.getParkById(new ArrayList<>(Arrays.asList(String.valueOf(cancelledOrder.getParkId()))));
 
        Order toNotify = findNextOrderInWaitingList(cancelledOrder.getOrderDate(), cancelledOrder.getOrderTime(), park);
 
        if (toNotify == null)
            return;
 
        String orderId = String.valueOf(toNotify.getOrderId());
        String status = OrderStatusName.AVAILABLE_SPOT.toString();
        orderQueries.setOrderStatusWithIDandStatus(new ArrayList<>(Arrays.asList(status, orderId)));
        orderQueries.addOrderAlert(toNotify.getOrderId(), LocalDate.now().toString(), LocalTime.now().toString(),
                LocalTime.now().plusHours(1).toString());
        sendWaitingListNotification(toNotify);
    }
}