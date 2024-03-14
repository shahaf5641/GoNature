package logic;

import java.io.Serializable;

/**
 * This class represents orders in the system.
 */
@SuppressWarnings("serial")
public class Order implements Serializable {
    // Instance variables representing order details
    private int orderId;
    private String travelerId;
    private int parkId;
    private String orderDate;
    private String orderTime;
    private String orderType;
    private int numberOfParticipants;
    private String email;
    private double price;
    private String orderStatus;

    /**
     * Constructor to initialize an Order object with specific details.
     * @param orderId The ID of the order.
     * @param travelerId The ID of the traveler placing the order.
     * @param parkId The ID of the park associated with the order.
     * @param orderDate The date when the order was made.
     * @param orderTime The time when the order was made.
     * @param orderType The type of the order (e.g., group, solo).
     * @param numberOfParticipants The number of participants in the order.
     * @param email The email associated with the order.
     * @param price The price of the order.
     * @param orderStatus The status of the order.
     */
    public Order(int orderId, String travelerId, int parkId, String orderDate, String orderTime, String orderType,
            int numberOfParticipants, String email, double price, String orderStatus) {
        this.orderId = orderId;
        this.travelerId = travelerId;
        this.parkId = parkId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.numberOfParticipants = numberOfParticipants;
        this.email = email;
        this.price = price;
        this.orderStatus = orderStatus;
    }
    
    /**
     * Constructor to initialize an Order object with specific details.
     * @param travelerId The ID of the traveler placing the order.
     * @param parkId The ID of the park associated with the order.
     * @param orderDate The date when the order was made.
     * @param orderTime The time when the order was made.
     * @param orderType The type of the order (e.g., group, solo).
     * @param numberOfParticipants The number of participants in the order.
     * @param email The email associated with the order.
     * @param price The price of the order.
     * @param orderStatus The status of the order.
     */
    public Order(String travelerId, int parkId, String orderDate, String orderTime, String orderType,
            int numberOfParticipants, String email, double price, String orderStatus) {
        // Use a default value of 0 for orderId
        this.orderId = 0;
        this.travelerId = travelerId;
        this.parkId = parkId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.numberOfParticipants = numberOfParticipants;
        this.email = email;
        this.price = price;
        this.orderStatus = orderStatus;
    }
    
    /**
     * Constructor to initialize an Order object with values from an OrderTb object.
     * @param o The OrderTb object to initialize the Order object.
     */
    public Order(OrderTb o) {
        // Use a default value of 0 for orderId
        this.orderId = 0;
        this.travelerId = o.getTravelerId();
        this.parkId = o.getParkId();
        this.orderDate = o.getOrderDate();
        this.orderTime = o.getOrderTime();
        this.orderType = o.getOrderType();
        this.numberOfParticipants = o.getNumberOfParticipants();
        this.email = o.getEmail();
        this.price = o.getPrice();
        this.orderStatus = o.getOrderStatus();
    }

    // Getter and setter methods for accessing and modifying the instance variables

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
