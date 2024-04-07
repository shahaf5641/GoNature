package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import controllers.WaitingListControl;
import controllers.QueriesConnectionSQL.EmployeeQueries;
import controllers.QueriesConnectionSQL.MysqlConnection;
import controllers.QueriesConnectionSQL.OrderQueries;
import controllers.QueriesConnectionSQL.ParkQueries;
import controllers.QueriesConnectionSQL.ReportsQueries;
import controllers.QueriesConnectionSQL.RequestsQueries;
import controllers.QueriesConnectionSQL.TravelersQueries;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
import logic.Employee;
import logic.Message;
import logic.Order;
import logic.Park;
import logic.Report;
import logic.responseFromServerToClient;
import logic.Guide;
import logic.Traveler;
import ocsf.server.ConnectionToClient;
import util.sendToClient;

/**
 * HandleClientRequest handles all the requests from the clients. This class is
 * a thread that create on every request from the server.
 * 
 * @author Shahaf Israel
 * @author Shai Osmo
 * @author Yuval Sabato
 * @author Sara Saleh
 * 
 * @version March 2024
 */
public class MatchRequestFromClient implements Runnable {

	private ConnectionToClient client = null;
	private Object msg = null;

	private Connection mysqlconnection;
	private OrderQueries orderQueries;
	private ParkQueries parkQueries;
	private ReportsQueries reportsQueries;
	private TravelersQueries travelerQueriesl;
	private RequestsQueries requestsQueries;
	private EmployeeQueries employeeQueries;

	public MatchRequestFromClient(ConnectionToClient client, Object msg) {
		this.client = client;
		this.msg = msg;

		try {
			mysqlconnection = MysqlConnection.getInstance().getConnection();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			sendToClient.sendToClientMsg(client, "DB Error");
			e.printStackTrace();
		}
		employeeQueries = new EmployeeQueries(mysqlconnection);
		orderQueries = new OrderQueries(mysqlconnection);
		parkQueries = new ParkQueries(mysqlconnection);
		reportsQueries = new ReportsQueries(mysqlconnection);
		travelerQueriesl = new TravelersQueries(mysqlconnection);
		requestsQueries = new RequestsQueries(mysqlconnection);
	}

	/**
	 * This function check which command the server need to do.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		responseFromServerToClient response;
		if (msg instanceof RequestsFromClientToServer)
			try {
				RequestsFromClientToServer<?> request = (RequestsFromClientToServer<?>) msg;
				if (request.getRequestType().equals(Request.TRAVELER_LOGIN_ID)) {
					Traveler traveler = travelerQueriesl.isTravelerExist(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Traveler>(Arrays.asList(traveler)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.INSERT_LOGGEDIN)) {
					travelerQueriesl.insertToLoggedInTable(request.getParameters());
					client.sendToClient("Finished Insert");
				}
				if (request.getRequestType().equals(Request.CONNECTED)) {
					boolean res = travelerQueriesl.checkIfConnected(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccess(res);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_PARK_BY_ID)) {
					Park park = parkQueries.getParkById(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_PARK_BY_NAME)) {
					Park park = parkQueries.getParkByName(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Park>(Arrays.asList(park)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_ALL_PARKS)) {
					ArrayList<Park> parks = parkQueries.getAllParks();
					response = new responseFromServerToClient();
					response.setSuccessSet(parks);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.ADD_ORDER)) {
					boolean result = orderQueries.addNewOrder(request.getObj());
					response = new responseFromServerToClient();
					response.setSuccess(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_ORDERS_BETWEEN_DATES)) {
					ArrayList<Order> orders = orderQueries.getOrderBetweenTimes(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(orders);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.ADD_TRAVELER)) {
					boolean result = travelerQueriesl.addTraveler(request.getObj());
					response = new responseFromServerToClient();
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_RECENT_ORDER)) {
					Order order = orderQueries.getRecentOrder(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Order>(Arrays.asList(order)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.INSERT_GUIDE)) {
					travelerQueriesl.insertGuideToGuideTable(request.getParameters());
					client.sendToClient("Finished Insert");
					client.sendToClient("");
				}

				if (request.getRequestType().equals(Request.EMPLOYEE_LOGIN)) {
					Employee member = travelerQueriesl.isMemberExist(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Employee>(Arrays.asList(member)));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.LOGOUT)) {
					travelerQueriesl.removeFromLoggedInTable(request.getParameters());
					client.sendToClient("User logedout.");
				}
				if (request.getRequestType().equals(Request.GET_ALL_ORDERS)) {
					ArrayList<Order> orders = orderQueries.getAllOrders();
					response = new responseFromServerToClient();
					response.setSuccessSet(orders);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_ALL_ORDER_FOR_ID)) {
					ArrayList<Order> orders = orderQueries.getAllOrdersForID(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(orders);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHANGE_ORDER_STATUS_ID)) {
					boolean res = orderQueries.setOrderStatusWithIDandStatus(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccess(res);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.ORDERS_MATCH_AFTER_CANCEL)) {
					ArrayList<Order> orders = orderQueries.findMatchingOrdersInWaitingList(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(orders);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.SEND_MSG_TRAVELER)) {
					boolean result = travelerQueriesl.sendMessageToTraveler(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.DELETE_TRAVELER)) {
					travelerQueriesl.deleteFromTravelerTable(request.getParameters());
					client.sendToClient("Finished Insert");
					client.sendToClient("");
				}


				if (request.getRequestType().equals(Request.MANAGER_REQUEST)) {
					requestsQueries.insertAllNewRequestsFromParkManager(request.getParameters());
					response = new responseFromServerToClient();
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.GET_GUIDE)) {
					Guide guide = travelerQueriesl.getGuideById(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Guide>(Arrays.asList(guide)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.VIEW_MANAGER_REQUEST)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(requestsQueries.GetRequestsFromDB());
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_EMPLOYEE)) {

					Employee employee = employeeQueries.getEmployeeById(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<Employee>(Arrays.asList(employee)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_EMPLOYEE_ID)) {
					String id = employeeQueries.getEmployeeIdByUserAndPass(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<String>(Arrays.asList(id)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_EMPLOYEE_PASSWORD)) {
					Employee employee = employeeQueries.getEmployeeById(request.getParameters());
					String password;
					String email;
					if (employee == null) {
						password = "";
						email = "";
					} else {
						password = employeeQueries.getEmployeePasswordById(employee.getEmployeeId());
						email = employee.getEmail();
					}
					response = new responseFromServerToClient();
					response.setSuccessSet(new ArrayList<String>(Arrays.asList(email, password)));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_MESSAGES_ID)) {
					ArrayList<Message> messages = travelerQueriesl.getMessages(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(messages);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.ADD_VISIT)) {
					response = new responseFromServerToClient();
					boolean result = parkQueries.addVisit(request.getParameters());
					response.setSuccess(result);
					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.UPDATE_CURRENT_VISITORS)) {
					response = new responseFromServerToClient();
					boolean result = parkQueries.updateNumberOfVisitors(request.getParameters());
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.ADD_CASUAL_ORDER)) {
					response = new responseFromServerToClient();
					boolean result = orderQueries.addNewOrder(request.getObj());
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_ALL_ORDERS_PARK)) {
					response = new responseFromServerToClient();
					ArrayList<Order> result = orderQueries.getOrdersForPark(request.getParameters());
					response.setSuccessSet(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_ALL_ORDERS_PARK_TRAVELER)) {
					response = new responseFromServerToClient();
					ArrayList<Order> result = orderQueries.getOrderForTravelerInPark(request.getParameters());
					response.setSuccessSet(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CONFIRM_REQUEST)) {
					response = new responseFromServerToClient();
					if ((int) request.getParameters().get(1) == 1)
						requestsQueries.changeStatusOfRequest(true, (int) request.getParameters().get(0));
					else
						requestsQueries.changeStatusOfRequest(false, (int) request.getParameters().get(0));

					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.MANAGER_REPORT)) {
					response = new responseFromServerToClient();
					int month = Integer.parseInt((String) request.getParameters().get(0));
					int parkID = Integer.parseInt((String) request.getParameters().get(2));
					if (request.getParameters().get(1).equals("Total Visitors"))
						response.setSuccessSet(reportsQueries.createNumberOfVisitorsReport(month, parkID));
					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.ADD_REPORT_DB)) {

					response = new responseFromServerToClient();
					reportsQueries.createNewReportInDB(request.getParameters());

					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.CHANGE_PARK_PARAMS)) {
					response = new responseFromServerToClient();
					parkQueries.changeParkParametersInDB(request.getParameters());
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHECK_PARK_FULL_DATE)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(parkQueries.isParkIsFullAtDate(request.getParameters()));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.INSERT_REPORT)) {
					response = new responseFromServerToClient();
					response.setSuccess(reportsQueries.insertReport(request.getParameters()));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.DELETE_REPORT)) {
					response = new responseFromServerToClient();
					reportsQueries.deleteReport(request.getParameters());
					client.sendToClient("Finished");
				}

				if (request.getRequestType().equals(Request.COUNT_ENTER_GROUP)) {

					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountGroupsEnterTime(request.getParameters()));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_GROUP)) {

					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountGroupsVisitTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_ENTER_SOLO)) {

					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountSolosEnterTime(request.getParameters()));
					client.sendToClient(response);

				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_SOLO)) {

					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountSolosVisitTime(request.getParameters()));
					client.sendToClient(response);

				}

				if (request.getRequestType().equals(Request.INSERT_FULL_PARK_DATE)) {
					response = new responseFromServerToClient();
					response.setSuccess(parkQueries.insertToFullParkDate(request.getParameters()));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_CANCELS)) {
					ArrayList<Integer> cancels = reportsQueries.getParkCancels(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(cancels);
					client.sendToClient(response);
				}
				
				if (request.getRequestType().equals(Request.GET_NOTARRIVED)) {
					ArrayList<Integer> notarrived = reportsQueries.getParkNotArrived(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(notarrived);
					client.sendToClient(response);
				}
				
				if (request.getRequestType().equals(Request.CHECK_WAITING_LIST)) {
					int orderId = (Integer) request.getParameters().get(0);
					Order order = orderQueries.getOrderByID(orderId);
					if (order != null)
						WaitingListControl.notifyNextPersonInWaitingList(order);
					client.sendToClient("Finished");
				}

				if (request.getRequestType().equals(Request.GET_REPORTS)) {
					ArrayList<Report> reports = reportsQueries.getReports(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(reports);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_RELEVANT_ORDER_ENTER)) {
					ArrayList<Order> result = orderQueries.getRelevantOrderForParkEntrance(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(result);
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.UPDATE_EXIT)) {
					parkQueries.updateVisitExitTime((Order) request.getObj(), request.getInput());
					client.sendToClient("Finished");
				}
				if (request.getRequestType().equals(Request.GET_RELEVANT_ORDER_EXIT)) {
					ArrayList<Order> result = orderQueries.getRelevantOrderForParkExit(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_PENDING_AFTER_DATE)) {
					ArrayList<Integer> pending = reportsQueries.getParkPendingDatePassed(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccessSet(pending);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHANGE_ORDER_VISITORS_ID)) {
					boolean result = orderQueries.UpdateNumberOfVisitorsForOrder(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.CHANGE_ORDER_PRICE_ID)) {
					boolean result = orderQueries.UpdatePriceForOrder(request.getParameters());
					response = new responseFromServerToClient();
					response.setSuccess(result);
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_SOLO_ORDERS)) {
					int month = (int) request.getParameters().get(0);
					int parkID = (int) request.getParameters().get(1);
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.getSolosOrdersVisitorsReport(month, parkID));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.GET_GROUPS_ORDERS)) {
					int month = (int) request.getParameters().get(0);
					int parkID = (int) request.getParameters().get(1);
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.getGroupsOrdersVisitorsReport(month, parkID));
					client.sendToClient(response);
				}

				if (request.getRequestType().equals(Request.COUNT_ENTER_SOLOS_DAYS)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountSolosEnterTimeWithDays(request.getParameters()));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.COUNT_ENTER_GROUPS_DAYS)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountGroupsEnterTimeWithDays(request.getParameters()));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_SOLOS_DAYS)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountSolosVisitTimeWithDays(request.getParameters()));
					client.sendToClient(response);
				}
				if (request.getRequestType().equals(Request.COUNT_VISIT_GROUPS_DAYS)) {
					response = new responseFromServerToClient();
					response.setSuccessSet(reportsQueries.CountGroupsVisitTimeWithDays(request.getParameters()));
					client.sendToClient(response);
				}

			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
	}

}
