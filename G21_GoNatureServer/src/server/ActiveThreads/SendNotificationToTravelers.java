
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
 
import controllers.QueriesConnectionSQL.OrderQueries;
import controllers.QueriesConnectionSQL.ParkQueries;
import controllers.QueriesConnectionSQL.TravelersQueries;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import resources.MsgTemplates;
 
/**
 * The NotifyTravelers class implements Runnable and handles automated functionality:
 * sending reminders 24 hours before the visit,
 * canceling the visit if the traveler did not confirm within two hours,
 * notifying the next person in the waiting list.
 */
public class SendNotificationToTravelers implements Runnable {
 
    private final int second = 1000;
    private final int minute = second * 60;
    private OrderQueries orderQueries;
    private ParkQueries parkQueries;
    private TravelersQueries travelerQueries;
 
    /**
     * Constructs a NotifyTravelers object.
     * @param mysqlconnection The connection to the MySQL database.
     */
    public SendNotificationToTravelers(Connection mysqlconnection) {
        orderQueries = new OrderQueries(mysqlconnection);
        parkQueries = new ParkQueries(mysqlconnection);
        travelerQueries = new TravelersQueries(mysqlconnection);
    }
 
    /**
     * Runs the automated functionality:
     * sending reminders 24 hours before the visit,
     * canceling the visit if the traveler did not confirm within two hours,
     * notifying the next person in the waiting list.
     */
    @Override
    public void run() {
 
        while (true) {
            System.out.println("Looking for relevant orders");
            ArrayList<Order> orders = getRelevantOrders();
 
            for (Order order : orders) {
                if (isDateLessThan24Hours(order.getOrderDate(), order.getOrderTime())) {
                    System.out.println(order.getOrderId());
                    runNotifySent(order);
                }
            }
 
            try {
                Thread.sleep(1 * minute);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
    }
 
    /**
     * This function creates a thread that checks if the traveler confirmed his order.
     * If he did not confirm his order within two hours, the order is automatically canceled,
     * and we notify the first person in the waiting list (if there is someone).
     * @param order The order to be checked.
     */
    private void runNotifySent(Order order) {
        Thread notifySent = new Thread(new Runnable() {
 
            @Override
            public void run() {
                String status = OrderStatusName.PENDING_24_HOURS_BEFORE.toString();
                String orderId = String.valueOf(order.getOrderId());
                orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
 
                sendConfirmationMessage(order);
 
                int totalSleep = 0;
                Order updatedOrder = null;
 
                while (totalSleep != 120) {
                    updatedOrder = orderQueries.getOrderByID(order.getOrderId());
                    /* NEED TO CHECK STATUS */
                    if (updatedOrder.getOrderStatus().equals(OrderStatusName.CANCELED.toString())
                            || updatedOrder.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString()))
                        break;
 
                    try {
                        Thread.sleep(1 * minute);
                        totalSleep += 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
 
                if (!updatedOrder.getOrderStatus().equals(OrderStatusName.CONFIRMED.toString())) {
                    status = OrderStatusName.CANCELED.toString();
                    orderId = String.valueOf(updatedOrder.getOrderId());
                    orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
 
                    /* Need to Send cancel order msg */
                    sendCancelMessage(updatedOrder);
 
                    AutomatedWaitingList notifyWaitingList = new AutomatedWaitingList(order);
                    new Thread(notifyWaitingList).start();
                }
            }
 
        });
        notifySent.start();
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
    private void sendCancelMessage(Order order){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
 
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
        String travelerId = order.getTravelerId();
        int orderId = order.getOrderId();
 
        Park park = parkQueries.getParkById(new ArrayList<String>(Arrays.asList(String.valueOf(order.getParkId()))));
        String subject = MsgTemplates.orderCancel[0];
        String content = String.format(MsgTemplates.orderCancel[1], park.getParkName(),
                order.getOrderDate(), order.getOrderTime());
 
 
        /* Add message to DB */
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        travelerQueries.sendMessageToTraveler(parameters);
    }
 
    /**
     * Retrieves pending orders from the database.
     * @return The list of pending orders.
     */
    private ArrayList<Order> getRelevantOrders() {
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
 
        return diffInHour < 24 && diffInHour > 22;
    }
}