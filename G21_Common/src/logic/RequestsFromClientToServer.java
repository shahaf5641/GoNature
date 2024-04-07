package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClientToServerRequest class is a request from the client to the server
 */
@SuppressWarnings("serial")
public class RequestsFromClientToServer<T> implements Serializable {

	public enum Request {
		CONNECTED, TRAVELER_LOGIN_ID, INSERT_LOGGEDIN, GUIDE_LOGIN_SUBID, GET_PARK_BY_ID, GET_GUIDE,
        GET_ALL_PARKS, GET_PARK_BY_NAME, GET_ORDERS_BETWEEN_DATES, ADD_ORDER, TRAVELER_GUIDE,
        ADD_TRAVELER, GET_RECENT_ORDER, EMPLOYEE_LOGIN, LOGOUT, SEND_MSG_TRAVELER, GET_ALL_ORDER_FOR_ID,
        GET_ALL_ORDERS, CHANGE_ORDER_STATUS_ID, ORDERS_MATCH_AFTER_CANCEL, INSERT_GUIDE,
        DELETE_TRAVELER, INSERT_CREDITCARD, MANAGER_REQUEST, GET_MESSAGES_ID, VIEW_MANAGER_REQUEST,
        GET_EMPLOYEE, GET_EMPLOYEE_PASSWORD, ADD_VISIT, UPDATE_CURRENT_VISITORS,
        ADD_CASUAL_ORDER, GET_ALL_ORDERS_PARK, GET_ALL_ORDERS_PARK_TRAVELER, CONFIRM_REQUEST,
        CHANGE_PARK_PARAMS, CHECK_PARK_FULL_DATE, DELETE_REPORT, INSERT_REPORT, COUNT_ENTER_SOLO,
        COUNT_ENTER_GUIDES, COUNT_ENTER_GROUP, COUNT_VISIT_SOLO, COUNT_VISIT_GUIDES,
        COUNT_VISIT_GROUP, GET_REPORTS, GET_CANCELS, MANAGER_REPORT,
        ADD_REPORT_DB, INSERT_FULL_PARK_DATE, CHECK_WAITING_LIST, GET_SIMULATOR_TRAVELERS,
        GET_RELEVANT_ORDER_EXIT, GET_RELEVANT_ORDER_ENTER, UPDATE_EXIT, GET_PENDING_AFTER_DATE,
        CHANGE_ORDER_VISITORS_ID, CHANGE_ORDER_PRICE_ID, GET_SOLO_ORDERS, GET_GUIDES_ORDERS,
        GET_GROUPS_ORDERS, COUNT_ENTER_GUIDES_DAYS, COUNT_ENTER_SOLOS_DAYS,
        COUNT_ENTER_GROUPS_DAYS, COUNT_VISIT_SOLOS_DAYS,
        COUNT_VISIT_GUIDE_DAYS, COUNT_VISIT_GROUPS_DAYS, GET_EMPLOYEE_ID, EXPORT_DATA, GET_NOTARRIVED, GET_CANCELSDAY,GET_NOTARRIVEDDAY


	}

	private Request requestType;
	private ArrayList<T> parameters = new ArrayList<>();
	private T obj;
	private String input;

	public RequestsFromClientToServer(Request requestType) {
		this.requestType = requestType;
	}

	public RequestsFromClientToServer(Request requestType, ArrayList<T> parameters) {
		this.requestType = requestType;
		this.parameters = parameters;
	}

	public ArrayList<?> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<T> parameters) {
		this.parameters = parameters;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Request getRequestType() {
		return requestType;
	}

	public void setRequestType(Request requestType) {
		this.requestType = requestType;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
