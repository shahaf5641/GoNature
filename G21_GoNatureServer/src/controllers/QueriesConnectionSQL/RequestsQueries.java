package controllers.QueriesConnectionSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import logic.OrderStatusName;
import logic.Request;

/**
 * This class handles all the queries which are related to requests
 * 
 */
public class RequestsQueries {
	private Connection conn;

	public RequestsQueries(Connection conn) {
		this.conn = conn;
	}

	/**
	 * this method gets data from request table and put it in a Request object.
	 * 
	 * @return ArrayList of Request objects - all the requests from the database
	 */
	public ArrayList<?> GetRequestsFromDB() {
		ArrayList<Request> requests = new ArrayList<>();
		int i = 0;
		String sql = "SELECT * FROM g21gonature.request ORDER BY requestId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();

			while (res.next()) {
				requests.add(i,
						new Request(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), null,
								res.getInt(6), // added parkId
								res.getString(7)));
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return requests;

	}

	/**
	 * This function gets the old value of specific parameter
	 * 
	 * @param nameOfColumn The name of the parameter
	 * @param parkID       The park id
	 * @return value from park table
	 */
	public int getOldValFromParkParameters(String nameOfColumn, int parkID) {

		String sql = "SELECT g21gonature.park." + nameOfColumn + " FROM g21gonature.park WHERE g21gonature.park.parkId="
				+ parkID + "";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();

			if (res.next()) {
				return res.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	/**
	 * this method is called after the Park Manager sent his request
	 * 
	 * @param managerRequests -type of request, value, old value, date,parkID,status
	 * 
	 */
	public void insertAllNewRequestsFromParkManager(ArrayList<?> managerRequests) {

		String sql = "INSERT INTO g21gonature.request (changeParamName,newParamValue,oldParamValue,requestDate,parkId,requestStatus) values (?,?,?,?,?,?)";

		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String parkID = (String) managerRequests.get(3); // arrayOfTextRequests = [200,30,90,1]
		PreparedStatement query;

		try {
			query = conn.prepareStatement(sql); // handles updates

			if (managerRequests.get(0) != null && !(managerRequests.get(0)).equals("")) {

				query.setString(1, "UPDATE MAX VISITORS");
				query.setString(2, (String) managerRequests.get(0));
				query.setInt(3, getOldValFromParkParameters("maxVisitors", Integer.parseInt(parkID))); // edited
				query.setDate(4, date);
				query.setInt(5, Integer.parseInt(parkID));
				query.setString(6, OrderStatusName.PENDING.toString());
				query.executeUpdate();
			}
			if (managerRequests.get(1) != null && !(managerRequests.get(1)).equals("")) {

				query.setString(1, "UPDATE ESTIMATED STAY TIME");
				query.setString(2, (String) managerRequests.get(1));
				query.setInt(3, getOldValFromParkParameters("estimatedStayTime", Integer.parseInt(parkID)));// edited
				query.setDate(4, date);
				query.setInt(5, Integer.parseInt(parkID));
				query.setString(6, OrderStatusName.PENDING.toString());
				query.executeUpdate();

			}

			if (managerRequests.get(2) != null && !(managerRequests.get(2)).equals("")) {

				query.setString(1, "UPDATE GAP");
				query.setString(2, (String) managerRequests.get(2));
				query.setInt(3, getOldValFromParkParameters("gapBetweenMaxAndCapacity", Integer.parseInt(parkID)));
				query.setDate(4, date);
				query.setInt(5, Integer.parseInt(parkID));
				query.setString(6, OrderStatusName.PENDING.toString());
				query.executeUpdate();

			}

		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

	}

	/**
	 * this method changes the status of a request according to Department Manager
	 * decision
	 * 
	 * @param bool       'true' to confirm, 'false' to cancel
	 * @param requestsID The request id to change
	 */
	public void changeStatusOfRequest(boolean bool, int requestsID) {
		String sql;

		if (bool)
			sql = "UPDATE g21gonature.request SET requestStatus='Confirmed' WHERE requestId=" + requestsID;

		else {
			sql = "UPDATE g21gonature.request SET requestStatus='declined' WHERE requestId=" + requestsID;
		}

		PreparedStatement query;

		try {
			query = conn.prepareStatement(sql);
			query.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
