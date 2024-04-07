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
    private int numberOfVisitors;
    private String travelerEmail;
    private double totalPrice;
    private String orderStatus;

    /**
     * Constructor to initialize an Order object with specific details.
     * @param orderId The ID of the order.
     * @param travelerId The ID of the traveler placing the order.
     * @param parkId The ID of the park associated with the order.
     * @param orderDate The date when the order was made.
     * @param orderTime The time when the order was made.
     * @param orderType The type of the order (e.g., group, solo).
     * @param numberOfVisitors The number of participants in the order.
     * @param travelerEmail The email associated with the order.
     * @param totalPrice The price of the order.
     * @param orderStatus The status of the order.
     */
    public Order(int orderId, String travelerId, int parkId, String orderDate, String orderTime, String orderType,
            int numberOfVisitors, String travelerEmail, double totalPrice, String orderStatus) {
        this.orderId = orderId;
        this.travelerId = travelerId;
        this.parkId = parkId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.numberOfVisitors = numberOfVisitors;
        this.travelerEmail = travelerEmail;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
    
    /**
     * Constructor to initialize an Order object with specific details.
     * @param travelerId The ID of the traveler placing the order.
     * @param parkId The ID of the park associated with the order.
     * @param orderDate The date when the order was made.
     * @param orderTime The time when the order was made.
     * @param orderType The type of the order (e.g., group, solo).
     * @param numberOfVisitors The number of participants in the order.
     * @param travelerEmail The email associated with the order.
     * @param totalPrice The price of the order.
     * @param orderStatus The status of the order.
     */
    public Order(String travelerId, int parkId, String orderDate, String orderTime, String orderType,
            int numberOfVisitors, String travelerEmail, double totalPrice, String orderStatus) {
        // Use a default value of 0 for orderId
        this.orderId = 0;
        this.travelerId = travelerId;
        this.parkId = parkId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.numberOfVisitors = numberOfVisitors;
        this.travelerEmail = travelerEmail;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }
    
    /**
     * Constructor to initialize an Order object with values from an OrderTb object.
     * @param o The OrderTb object to initialize the Order object.
     */
    public Order(OrderTable o) {
        // Use a default value of 0 for orderId
        this.orderId = 0;
        this.travelerId = o.getTravelerId();
        this.parkId = o.getParkId();
        this.orderDate = o.getOrderDate();
        this.orderTime = o.getOrderTime();
        this.orderType = o.getOrderType();
        this.numberOfVisitors = o.getNumberOfVisitors();
        this.travelerEmail = o.getTravelerEmail();
        this.totalPrice = o.getPrice();
        this.orderStatus = o.getOrderStatus();
    }

    // Getter and setter methods for accessing and modifying the instance variables

    public Order(int orderId2, String travelerId2, String orderDate2, String orderTime2, String orderStatus2,
			String orderType2, int numberOfParticipants2, double price2, int parkId2) {
		// TODO Auto-generated constructor stub
	}

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
        return numberOfVisitors;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfVisitors = numberOfParticipants;
    }

    public String getTravelerEmail() {
        return travelerEmail;
    }

    public void setTravelerEmail(String travelerEmail) {
        this.travelerEmail = travelerEmail;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
