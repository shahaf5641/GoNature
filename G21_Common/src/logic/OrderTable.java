package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * OrderTb class represents an Order in the park.
 * This class is suitable for table views.
 */
public class OrderTable {
    private SimpleIntegerProperty orderId;
    private SimpleStringProperty travelerId;
    private SimpleIntegerProperty parkId;
    private SimpleStringProperty orderDate;
    private SimpleStringProperty orderTime;
    private SimpleStringProperty orderType;
    private SimpleIntegerProperty numberOfVisitors;
    private SimpleStringProperty travelerEmail;
    private SimpleDoubleProperty totalPrice;
    private SimpleStringProperty orderStatus;

    /**
     * Constructor for creating an OrderTb object with provided parameters.
     * @param orderId The order ID.
     * @param travelerId The traveler ID.
     * @param parkId The park ID.
     * @param orderDate The date of the order.
     * @param orderTime The time of the order.
     * @param orderType The type of the order.
     * @param numberOfVisitors The number of visitors in the order.
     * @param travelerEmail The email associated with the order.
     * @param totalPrice The price of the order.
     * @param orderStatus The status of the order.
     */
    public OrderTable(int orderId, String travelerId, int parkId,
                   String orderDate, String orderTime, String orderType,
                   int numberOfVisitors, String travelerEmail, double totalPrice,
                   String orderStatus) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.travelerId = new SimpleStringProperty(travelerId);
        this.parkId = new SimpleIntegerProperty(parkId);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.orderTime = new SimpleStringProperty(orderTime);
        this.orderType = new SimpleStringProperty(orderType);
        this.numberOfVisitors = new SimpleIntegerProperty(numberOfVisitors);
        this.travelerEmail = new SimpleStringProperty(travelerEmail);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.orderStatus = new SimpleStringProperty(orderStatus);
    }

    /**
     * Constructor for creating an OrderTb object from an existing Order object.
     * @param order The Order object from which to create the OrderTb object.
     */
    public OrderTable(Order order) {
        this.orderId = new SimpleIntegerProperty(order.getOrderId());
        this.travelerId = new SimpleStringProperty(order.getTravelerId());
        this.parkId = new SimpleIntegerProperty(order.getParkId());
        this.orderDate = new SimpleStringProperty(order.getOrderDate());
        this.orderTime = new SimpleStringProperty(order.getOrderTime());
        this.orderType = new SimpleStringProperty(order.getOrderType());
        this.numberOfVisitors = new SimpleIntegerProperty(order.getNumberOfParticipants());
        this.travelerEmail = new SimpleStringProperty(order.getTravelerEmail());
        this.totalPrice = new SimpleDoubleProperty(order.getTotalPrice());
        this.orderStatus = new SimpleStringProperty(order.getOrderStatus());
    }

    // Getters and setters for all properties omitted for brevity.
	public int getOrderId() {
		return orderId.get();
	}

	public void setOrderId(SimpleIntegerProperty orderId) {
		this.orderId = orderId;
	}

	public String getTravelerId() {
		return travelerId.get();
	}

	public void setTravelerId(SimpleStringProperty travelerId) {
		this.travelerId = travelerId;
	}

	public int getParkId() {
		return parkId.get();
	}

	public void setParkId(SimpleIntegerProperty parkId) {
		this.parkId = parkId;
	}

	public String getOrderDate() {
		return orderDate.get();
	}

	public void setOrderDate(SimpleStringProperty orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime.get();
	}

	public void setOrderTime(SimpleStringProperty orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderType() {
		return orderType.get();
	}

	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}

	public int getNumberOfVisitors() {
		return numberOfVisitors.get();
	}

	public void setNumberOfVisitors(SimpleIntegerProperty numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
	}

	public String getTravelerEmail() {
		return travelerEmail.get();
	}

	public void setEmail(SimpleStringProperty travelerEmail) {
		this.travelerEmail = travelerEmail;
	}

	public double getPrice() {
		return totalPrice.get();
	}

	public void setPrice(SimpleDoubleProperty totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus.get();
	}

	public void setOrderStatus(SimpleStringProperty orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
