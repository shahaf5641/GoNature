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
 * This class represents a background thread responsible for canceling orders that have not been confirmed within 24 hours.
 */
public class AutomatedCancelOrders implements Runnable {
 
    private final int second = 1000;
    private final int minute = second * 60;
    private OrderQueries orderQueries;
    private ParkQueries parkQueries;
    private TravelersQueries travelerQueries;
 
    /**
     * Constructs a CancelOrders object.
     * @param mysqlconnection The connection to the MySQL database.
     */
    public AutomatedCancelOrders(Connection mysqlconnection) {
        orderQueries = new OrderQueries(mysqlconnection);
        parkQueries = new ParkQueries(mysqlconnection);
        travelerQueries = new TravelersQueries(mysqlconnection);
    }
 
    @Override
    public void run() {
 
        while (true) {
            System.out.println("Looking for relevant orders to cancel");
            ArrayList<Order> pendingOrders = getRelevantOrders();
 
            for (Order order : pendingOrders) {
                if (isDateLessThan24Hours(order.getOrderDate(), order.getOrderTime())) {
                    String status = OrderStatusName.CANCELED.toString();
                    String orderId = String.valueOf(order.getOrderId());
                    orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
 
                    /* Need to Send cancel order msg */
                    sendCancelMessage(order);
 
                    AutomatedWaitingList notifyWaitingList = new AutomatedWaitingList(order);
                    new Thread(notifyWaitingList).start();
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
     * Sends a cancellation message to the traveler.
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
        String content = String.format(MsgTemplates.orderCancel[1], park.getParkName(),
                order.getOrderDate(), order.getOrderTime());
        /* Add message to DB */
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        travelerQueries.sendMessageToTraveler(parameters);
    }
 
    /**
     * Retrieves relevant pending orders from the database.
     * @return The list of relevant pending orders.
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
 
        return diffInHour <= 24 && diffInHour > 0;
    }
}