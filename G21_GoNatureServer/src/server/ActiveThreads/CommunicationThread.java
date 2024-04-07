 
package server.ActiveThreads;
 
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
 
import controllers.WaitingListControl;
import controllers.QueriesConnectionSQL.OrderQueries;
import controllers.QueriesConnectionSQL.ParkQueries;
import controllers.QueriesConnectionSQL.TravelersQueries;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import resources.MsgTemplates;
 
/**
 * The NotifyThread class implements Runnable and handles automated functionality:
 * sending reminders 24 hours before visit,
 * canceling visits if travelers do not confirm within two hours,
 * notifying the next person in the waiting list.
 */
public class CommunicationThread implements Runnable {
 
    private final int second = 1000;
    private final int minute = second * 60;
    private OrderQueries orderQueries;
    private ParkQueries parkQueries;
    private TravelersQueries travelerQueries;
 
    /**
     * Constructs a NotifyThread object.
     * @param mysqlconnection The connection to the MySQL database.
     */
    public CommunicationThread(Connection mysqlconnection) {
        orderQueries = new OrderQueries(mysqlconnection);
        parkQueries = new ParkQueries(mysqlconnection);
        travelerQueries = new TravelersQueries(mysqlconnection);
    }
 
    /**
     * Handles all the automated functionality:
     * sending reminders 24 hours before visit,
     * canceling visits if travelers do not confirm within two hours,
     * notifying the next person in the waiting list.
     */
    @Override
    public void run() {
 
        while (true) {
 
            ArrayList<Order> pendingOrders = getPendingOrders();
            for (Order order : pendingOrders) {
                if (isDateLessThan24Hours(order.getOrderDate(), order.getOrderTime())) {
                    String status = OrderStatusName.PENDING_24_HOURS_BEFORE.toString();
                    String orderId = String.valueOf(order.getOrderId());
                    orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
                    sendConfirmationMessage(order);
                    orderQueries.addOrderAlert(order.getOrderId(), LocalDate.now().toString(),
                            LocalTime.now().toString(), LocalTime.now().plusHours(2).toString());
                }
            }
            cancelOrderAndNotify();
            deleteAlerts();
            try {
                Thread.sleep(1 * minute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * Deletes expired alerts from the database.
     */
    private void deleteAlerts() {
        ArrayList<Integer> alertToDelete = orderQueries.getOrderAlertIdWithExpiryAlertTime(LocalDate.now().toString(),
                LocalTime.now().toString());
        for (Integer alertId : alertToDelete)
            orderQueries.deleteOrderAlert(alertId);
    }
 
    /**
     * Cancels orders that have expired and notifies the next person in the waiting list.
     */
    private void cancelOrderAndNotify() {
 
        ArrayList<Order> ordersToChangeStatus = orderQueries.getOrderWithExpiryAlertTime(LocalDate.now().toString(),
                LocalTime.now().toString());
        for (Order order : ordersToChangeStatus) {
            ArrayList<String> parameters = new ArrayList<>(
                    Arrays.asList(OrderStatusName.CANCELED.toString(), String.valueOf(order.getOrderId())));
            orderQueries.setOrderStatusWithIDandStatus(parameters);
            sendCancelMessage(order);
            WaitingListControl.notifyNextPersonInWaitingList(order);
        }
    }
 
    /**
     * Sends a reminder message 24 hours before the visit.
     * @param order The order for which the reminder message is sent.
     */
    private void sendConfirmationMessage(Order order) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
 
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
        String travelerId = order.getTravelerId();
        int orderId = order.getOrderId();
 
        Park park = parkQueries.getParkById(new ArrayList<String>(Arrays.asList(String.valueOf(order.getParkId()))));
        String subject = MsgTemplates.ConfirmOrder24hoursBeforeVisit[0];
        String content = String.format(MsgTemplates.ConfirmOrder24hoursBeforeVisit[1], park.getParkName(),
                order.getOrderDate(), order.getOrderTime());
 
        /* Add message to DB */
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        travelerQueries.sendMessageToTraveler(parameters);
 
    }
 
    /**
     * Sends a cancellation message for an expired order.
     * @param order The order to be canceled.
     */
    private void sendCancelMessage(Order order) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
 
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
        String travelerId = order.getTravelerId();
        int orderId = order.getOrderId();
 
        Park park = parkQueries.getParkById(new ArrayList<String>(Arrays.asList(String.valueOf(order.getParkId()))));
        String subject = MsgTemplates.orderCancel[0];
        String content = String.format(MsgTemplates.orderCancel[1], park.getParkName(), order.getOrderDate(),
                order.getOrderTime());
 
        /* Add message to DB */
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        travelerQueries.sendMessageToTraveler(parameters);
    }
 
    /**
     * Retrieves pending orders from the database.
     * @return The list of pending orders.
     */
    private ArrayList<Order> getPendingOrders() {
        return orderQueries.getPendingOrders();
    }
 
    /**
     * Checks if the given date and time are less than 24 hours from the current date and time.
     * @param date The order date.
     * @param time The order time.
     * @return True if the order date and time are less than 24 hours from the current date and time, false otherwise.
     */
    private boolean isDateLessThan24Hours(String date, String time) {
 
        String combinedVisit = date + " " + time;
        String combinedToday = LocalDate.now().toString() + " " + LocalTime.now().toString();
 
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
        Date visitDate = new Date();
        Date todayDate = new Date();
 
        try {
            todayDate = sdfDate.parse(combinedToday);
            visitDate = sdfDate.parse(combinedVisit);
        } catch (ParseException e) {
            System.out.println("Failed to parse dates");
            e.printStackTrace();
            return false;
        }
 
        long diffInMills = visitDate.getTime() - todayDate.getTime();
        long diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMills);
 
        if (diffInHour < 24 && diffInHour > 22)
            return true;
        return false;
    }
}
 