package server.ActiveThreads;
 
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
 
import controllers.QueriesConnectionSQL.OrderQueries;
import controllers.QueriesConnectionSQL.ParkQueries;
import controllers.QueriesConnectionSQL.TravelersQueries;
import controllers.QueriesConnectionSQL.MysqlConnection;
import logic.Order;
import logic.OrderStatusName;
import logic.Park;
import resources.MsgTemplates;
 
/**
 * The NotifyWaitingList class implements Runnable and handles automated functionality:
 * notifying a person from the waiting list that they have a spot in the park for their order,
 * monitoring the order status for 2 hour,
 * notifying the next person in the waiting list if the traveler did not confirm their order.
 */
public class AutomatedWaitingList implements Runnable {
 
    private String date, hour;
    private Park park;
    private Connection mysqlconnection;
    private OrderQueries orderQueries;
    private ParkQueries parkQueries;
    private TravelersQueries travelerQueries;
    private Order order;
 
    private final int second = 1000;
    private final int minute = second * 60;
 
    /**
     * Constructs a AutomatedWaitingList object.
     * @param order The order for which to notify the waiting list.
     */
    public AutomatedWaitingList(Order order) {
        try {
            mysqlconnection = MysqlConnection.getInstance().getConnection();
            orderQueries = new OrderQueries(mysqlconnection);
            parkQueries = new ParkQueries(mysqlconnection);
            travelerQueries = new TravelersQueries(mysqlconnection);
            this.date = order.getOrderDate();
            this.hour = order.getOrderTime();
            ArrayList<String> parameters = new ArrayList<>(Arrays.asList(String.valueOf(order.getParkId())));
            this.park = parkQueries.getParkById(parameters);
            this.order = order;
        } catch (Exception e) {
            System.out.println("Exception was thrown - notify waiting list");
            e.printStackTrace();
        }
    }
 
    /**
     * Checks if the traveler confirmed or canceled their order.
     * If they did not confirm their order within two hours, the order is canceled automatically,
     * and we notify the next person in the waiting list (if there is someone).
     */
    @Override
    public void run() {
        Order order = notifyPersonFromWaitingList(date, hour, park);
 
        System.out.println("Entered notify waiting list");
        if (order == null) 
            return;
 
 
        String orderId = String.valueOf(order.getOrderId());
        String status = OrderStatusName.IN_WAITING_LIST.toString();
        orderQueries.setOrderStatusWithIDandStatus(new ArrayList<String>(Arrays.asList(status, orderId)));
        sendMessages(order);
 
        int totalSleep = 0;
        Order updatedOrder = null;
 
        while (totalSleep != 120) {
            System.out.println("Entred while");
            updatedOrder = orderQueries.getOrderByID(order.getOrderId());
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
 
            // Passing the original order that was canceled.
            AutomatedWaitingList notifyWaitingList = new AutomatedWaitingList(this.order);
            new Thread(notifyWaitingList).start();
        }
 
    }
 
    /**
     * Sends messages to the traveler.
     * @param order The order for which to send messages.
     */
    private void sendMessages(Order order) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
 
        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];
        String travelerId = order.getTravelerId();
        int orderId = order.getOrderId();
 
        Park park = parkQueries.getParkById(new ArrayList<String>(Arrays.asList(String.valueOf(order.getParkId()))));
        String subject = MsgTemplates.waitingListPlaceInPark[0];
        String content = String.format(MsgTemplates.waitingListPlaceInPark[1], park.getParkName(),
                order.getOrderDate(), order.getOrderTime());
 
        /* Add message to DB */
 
        ArrayList<String> parameters = new ArrayList<>(
                Arrays.asList(travelerId, date, time, subject, content, String.valueOf(orderId)));
        System.out.println(parameters);
        travelerQueries.sendMessageToTraveler(parameters);
 
    }
 
    /**
     * Notifies a person from the waiting list that they have a spot in the park for their order.
     * @param date The date of the visit.
     * @param hour The hour of the visit.
     * @param park The park where the visit is scheduled.
     * @return The order of the person from the waiting list.
     */
    private Order notifyPersonFromWaitingList(String date, String hour, Park park) {
        String parkId = String.valueOf(park.getParkId());
        String maxVisitors = String.valueOf(park.getMaxVisitors());
        String estimatedStayTime = String.valueOf(park.getEstimatedStayTime());
        String gap = String.valueOf(park.getGapBetweenMaxAndCapacity());
        ArrayList<String> parameters = new ArrayList<String>(
                Arrays.asList(parkId, maxVisitors, estimatedStayTime, date, hour, gap));
        ArrayList<Order> ordersThatMatchWaitingList = orderQueries.findMatchingOrdersInWaitingList(parameters);
        return ordersThatMatchWaitingList.size() == 0 ? null : ordersThatMatchWaitingList.get(0);
    }
 
}
 