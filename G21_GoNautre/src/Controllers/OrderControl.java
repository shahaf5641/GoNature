 
/**
 * The OrderControl class manages operations related to orders in the system.
 * It includes methods for finding orders, adding new orders, updating order status, and handling order-related functionalities.
 */
package Controllers;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
 
import client.ChatClient;
import client.ClientUI;
import logic.Order;
import logic.OrderStatusName;
import logic.OrderTable;
import logic.Park;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
import logic.Traveler;
import resources.MsgTemplates;
 
@SuppressWarnings("unchecked")
public class OrderControl {
 
    /**
     * Finds orders associated with the given ID.
     *
     * @param id The ID of the orders to find.
     * @return An ArrayList containing the found orders.
     */
    public static ArrayList<Order> findOrders(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_ALL_ORDER_FOR_ID,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Finds all orders in the system.
     *
     * @return An ArrayList containing all orders.
     */
    public static ArrayList<Order> findAllOrders() {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_ALL_ORDERS);
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Adds a new order for a traveler and sends a notification.
     *
     * @param order    The order to be added.
     * @param traveler The traveler associated with the order.
     * @return The recently added order.
     */
    public static Order addNewOrderAndSendNotification(Order order, Traveler traveler) {
        Order recentOrder = null;
        if (addNewOrder(order, traveler)) {
            recentOrder = findTravelerRecentOrder(traveler.getTravelerId());
 
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateAndTime = dtf.format(now);
            String date = dateAndTime.split(" ")[0];
            String time = dateAndTime.split(" ")[1];
            if (recentOrder != null) {
                String Message = String.format(MsgTemplates.orderConfirmation[1].toString(),
                        String.valueOf(recentOrder.getOrderId()),
                        ParkControl.findParkName(String.valueOf(recentOrder.getParkId())), recentOrder.getOrderDate(),
                        recentOrder.getOrderTime(), recentOrder.getOrderType(),
                        String.valueOf(recentOrder.getNumberOfParticipants()), String.valueOf(recentOrder.getTotalPrice()));
                CommunicationControl.sendMessageToTraveler(traveler.getTravelerId(), date, time,
                        MsgTemplates.orderConfirmation[0], Message, String.valueOf(recentOrder.getOrderId()));
            }
        }
        return recentOrder;
    }
 
    /**
     * Adds a new order for a traveler.
     *
     * @param order    The order to be added.
     * @param traveler The traveler associated with the order.
     * @return True if the order is added successfully, false otherwise.
     */
    public static boolean addNewOrder(Order order, Traveler traveler) {
        if ((order.getOrderStatus().equals(OrderStatusName.PENDING.toString()) && checkDateAvailability(order))
                || order.getOrderStatus().equals(OrderStatusName.IN_WAITING_LIST.toString())) {
            RequestsFromClientToServer<Order> request = new RequestsFromClientToServer<>(Request.ADD_ORDER);
            request.setObj(order);
            ClientUI.chat.accept(request);
            if (ChatClient.responseFromServer.isSuccess()) {
                if (TravelerControl.checkTravelerPresence(traveler.getTravelerId())) {
                    return true;
                } else {
                    RequestsFromClientToServer<Traveler> requestOfTraveler = new RequestsFromClientToServer<>(
                            Request.ADD_TRAVELER);
                    requestOfTraveler.setObj(traveler);
                    ClientUI.chat.accept(requestOfTraveler);
                    if (ChatClient.responseFromServer.isSuccess()) {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }
 
    /**
     * Finds the most recent order associated with the given traveler ID.
     *
     * @param travelerId The ID of the traveler.
     * @return The most recent order of the traveler.
     */
	public static Order findTravelerRecentOrder(String travelerId) {
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_RECENT_ORDER,
				new ArrayList<String>(Arrays.asList(travelerId)));
		ClientUI.chat.accept(request);
		Order order = (Order) ChatClient.responseFromServer.getSuccessSet().get(0);
		return order;
 
	}
 
	/**
     * Checks the availability of a given order date and time based on the park's capacity.
     *
     * @param order The order to be checked for availability.
     * @return True if the order date and time are available, false otherwise.
     */
	public static boolean checkDateAvailability(Order order) {
		String parkId = String.valueOf(order.getParkId());
		Park park = ParkControl.findParkById(parkId);
		String orderTime = order.getOrderTime();
		int hour = Integer.parseInt(orderTime.split(":")[0]);
		int estimatedStayTime = park.getEstimatedStayTime();
 
		String date = order.getOrderDate();
		String startTime = (hour - estimatedStayTime) + ":00";
		String endTime = (hour + estimatedStayTime) + ":00";
 
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_ORDERS_BETWEEN_DATES,
				new ArrayList<String>(Arrays.asList(parkId, date, startTime, endTime)));
		ClientUI.chat.accept(request);
 
		ArrayList<Order> orders = ChatClient.responseFromServer.getSuccessSet();
 
		int VisitorsNum = 0;
		for (Order ord : orders) {
			VisitorsNum += ord.getNumberOfParticipants();
		}
 
		if (VisitorsNum + order.getNumberOfParticipants() >= park.getMaxVisitors()
				- park.getGapBetweenMaxAndCapacity()) {
 
			return false;
		}
 
		return true;
	}
 
 
	/**
     * Converts a list of orders into a list of order tables for display purposes.
     *
     * @param ordersArrayList The list of orders to be converted.
     * @return An ArrayList containing the converted OrderTable objects.
     */
	public static ArrayList<OrderTable> changeOrderToOrderTable(ArrayList<Order> ordersArrayList) {
		ArrayList<OrderTable> OrderTableArrayList = new ArrayList<OrderTable>();
		for (Order order : ordersArrayList) {
			OrderTable OrderTable = new OrderTable(order);
			OrderTableArrayList.add(OrderTable);
		}
		return OrderTableArrayList;
	}
	/**
     * Finds all orders of a traveler in a specific park.
     *
     * @param parkId The ID of the park.
     * @param id     The ID of the traveler.
     * @return An ArrayList containing the traveler's orders in the specified park.
     */
	public static ArrayList<Order> findTravelerOrdersInPark(String parkId, String id) {
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(
				Request.GET_ALL_ORDERS_PARK_TRAVELER, new ArrayList<String>(Arrays.asList(parkId, id)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getSuccessSet();
	}
 
	/**
     * Reviews the waiting list for a specific order.
     *
     * @param orderId The ID of the order.
     */
	public static void reviewWaitingList(int orderId) {
		RequestsFromClientToServer<Integer> request = new RequestsFromClientToServer<>(Request.CHECK_WAITING_LIST,
				new ArrayList<Integer>(Arrays.asList(orderId)));
		ClientUI.chat.accept(request);
	}
 
 
	/**
     * Updates the status of a specific order.
     *
     * @param orderId    The ID of the order to update.
     * @param statusName The new status of the order.
     * @return True if the update was successful, false otherwise.
     */
	public static boolean updateOrderStatus(String orderId, OrderStatusName statusName) {
		String status = statusName.toString();
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.CHANGE_ORDER_STATUS_ID,
				new ArrayList<String>(Arrays.asList(status, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isSuccess();
	}
	/**
     * Adds a visit based on the information provided in the OrderTable object.
     *
     * @param order The OrderTable object containing visit information.
     * @return True if the visit addition was successful, false otherwise.
     */
	public static boolean AddVisit(OrderTable order) {
		String travelerId = order.getTravelerId();
		String parkId = String.valueOf(order.getParkId());
		String date = order.getOrderDate();
		String enterTime = order.getOrderTime();
 
		Park park = ParkControl.findParkById(parkId);
		String estimated = String.valueOf(park.getEstimatedStayTime());
 
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.ADD_VISIT,
				new ArrayList<String>(Arrays.asList(travelerId, parkId, enterTime, estimated, date)));
		ClientUI.chat.accept(request);
 
		return ChatClient.responseFromServer.isSuccess();
	}
 
	/**
     * Adds a visit based on the information provided in the Order object.
     *
     * @param order The Order object containing visit information.
     * @return True if the visit addition was successful, false otherwise.
     */
	public static boolean AddVisit(Order order) {
		String travelerId = order.getTravelerId();
		String parkId = String.valueOf(order.getParkId());
		String date = order.getOrderDate();
		String enterTime = order.getOrderTime();
 
		Park park = ParkControl.findParkById(parkId);
		String estimated = String.valueOf(park.getEstimatedStayTime());
 
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.ADD_VISIT,
				new ArrayList<String>(Arrays.asList(travelerId, parkId, enterTime, estimated, date)));
		ClientUI.chat.accept(request);
 
		return ChatClient.responseFromServer.isSuccess();
	}
 
	/**
     * Adds a casual order to the system.
     *
     * @param order The casual order to be added.
     * @return True if the addition was successful, false otherwise.
     */
	public static boolean AddCasualOrder(Order order) {
		RequestsFromClientToServer<Order> request = new RequestsFromClientToServer<>(Request.ADD_CASUAL_ORDER);
		request.setObj(order);
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isSuccess();
	}
 
	/**
     * Finds all orders associated with a specific park ID.
     *
     * @param parkId The ID of the park.
     * @return An ArrayList containing orders associated with the specified park.
     */
	public static ArrayList<Order> findOrdersByParkId(int parkId) {
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_ALL_ORDERS_PARK,
				new ArrayList<String>(Arrays.asList(String.valueOf(parkId))));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getSuccessSet();
	}
 
 
 
	/**
     * Finds the order associated with the exit of a park based on the provided ID.
     *
     * @param id The ID of the order for park exit.
     * @return The order associated with the park exit.
     */
	public static Order findOrderForParkExit(String id) {
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_RELEVANT_ORDER_EXIT,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Order order = (Order) ChatClient.responseFromServer.getSuccessSet().get(0);
		return order;
	}
 
	/**
     * Updates the price of a specific order.
     *
     * @param id    The ID of the order.
     * @param price The new price for the order.
     * @return True if the update was successful, false otherwise.
     */
	public static boolean updatePriceForOrder(String id, double price) {
		String p = String.valueOf(price);
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.CHANGE_ORDER_PRICE_ID,
				new ArrayList<String>(Arrays.asList(p, id)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isSuccess();
 
	}
 
	/**
     * Finds the order associated with a traveler's entrance to the park.
     *
     * @param id The ID of the traveler.
     * @return The order associated with the traveler's entrance to the park.
     */
	public static Order findOrderForTravelerAtParkEntrance(String id) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		String dateAndTime = dtf.format(now);
 
		String date = dateAndTime.split(" ")[0];
		String time = dateAndTime.split(" ")[1];
		int hour = Integer.parseInt(time.split(":")[0]);
		String startTime = String.valueOf(hour - 2) + ":" + time.split(":")[1];
		String endTime = String.valueOf(hour + 2) + ":" + time.split(":")[1];
 
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_RELEVANT_ORDER_ENTER,
				new ArrayList<String>(Arrays.asList(id, date, startTime, endTime)));
		ClientUI.chat.accept(request);
		Order order = (Order) ChatClient.responseFromServer.getSuccessSet().get(0);
		return order;
 
	}
	/**
     * Updates the number of visitors in a specific order.
     *
     * @param orderId                      The ID of the order.
     * @param participantCountInCurrentOrder The new number of participants in the order.
     * @return True if the update was successful, false otherwise.
     */
	public static boolean updateVisitorsAmountInOrder(String orderId,
			int participantCountInCurrentOrder) {
		String number = String.valueOf(participantCountInCurrentOrder);
		RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(
				Request.CHANGE_ORDER_VISITORS_ID, new ArrayList<String>(Arrays.asList(number, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isSuccess();
	}
 
}