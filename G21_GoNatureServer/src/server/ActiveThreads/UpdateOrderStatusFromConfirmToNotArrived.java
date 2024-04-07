package server.ActiveThreads;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import controllers.QueriesConnectionSQL.OrderQueries;
import logic.Order;
import logic.OrderStatusName;

/**
 * UpdateTravelerExitStatus class implements Runnable.
 * 
 * This class handle all the automated functionality:
 * Monitor the exiting visitors from the park.
 * Change the order status when the traveler exit.
 * Update the park current visitors parameter.
 *
 */
public class UpdateOrderStatusFromConfirmToNotArrived implements Runnable {

	private final int second = 1000;
	private final int minute = second * 60;

	private OrderQueries orderQueries;

	public UpdateOrderStatusFromConfirmToNotArrived(Connection mysqlconnection) {
		orderQueries = new OrderQueries(mysqlconnection);
	}

	private ArrayList<Order> getRelevantOrders() {
		return orderQueries.getConfirmedNotArrivedOrders();
	}

	/**
	 * This function check if the traveler exited the park, based on estimated exit time.
	 * If the thread did find a traveler that exited, it will change his order status
	 * and update the park current visitors.
	 */
	@Override
	public void run() {

		while (true) {
			ArrayList<Order> orders = getRelevantOrders();	//Confirmed not arrived orders
			String orderId = "";
			for (Order order : orders) {
				orderId = String.valueOf(order.getOrderId());
				orderQueries.setOrderStatusWithIDandStatus(
						new ArrayList<String>(Arrays.asList(OrderStatusName.NOT_ARRIVED_NOT_CANCELED.toString(), orderId)));
			}
			try {
				Thread.sleep(1 * minute);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}