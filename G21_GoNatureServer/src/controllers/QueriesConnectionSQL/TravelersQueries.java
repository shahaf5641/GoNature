package controllers.QueriesConnectionSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logic.Employee;
import logic.Message;
import logic.Guide;
import logic.Traveler;
import logic.EmployeeType;

/**
 * This class handles all the queries which are related to travelers
 * 
 */
public class TravelersQueries {
	private Connection conn;

	public TravelersQueries(Connection conn) {
		this.conn = conn;
	}

	/**
	 * This function gets a traveler's id and retrieve the traveler from the
	 * database
	 * 
	 * @param parameters The traveler's id
	 * @return Traveler object
	 */
	public Traveler isTravelerExist(ArrayList<?> parameters) {
		Traveler traveler = null;
		String sql = "SELECT * FROM g21gonature.traveler WHERE travelerId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();

			if (res.next())
				traveler = new Traveler(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5));
		} catch (SQLException e) {
			System.out.println("Could not execute isTravelerExist query");
			e.printStackTrace();
		}

		return traveler;
	}

	/**
	 * This function INSERT a new traveler to 'traveler' table in the database
	 * 
	 * @param obj Traveler object to insert
	 * @return true on success, false otherwise
	 */
	public boolean addTraveler(Object obj) {
		Traveler travelerToAdd = (Traveler) obj;
		int result = 0;
		String sql = "INSERT INTO g21gonature.traveler (travelerId, firstName, lastName, email, phoneNumber) "
				+ "values (?, ?, ?, ?, ?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, travelerToAdd.getTravelerId());
			query.setString(2, travelerToAdd.getFirstName());
			query.setString(3, travelerToAdd.getLastName());
			query.setString(4, travelerToAdd.getEmail());
			query.setString(5, travelerToAdd.getPhoneNumber());
			result = query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute AddTraveler query");
			e.printStackTrace();
		}
		return result > 0;
	}

	/**
	 * This function removes the user from logedin table.
	 * 
	 * @param parameters The user's id
	 *
	 */
	public void removeFromLoggedInTable(ArrayList<?> parameters) {
		String sql = "DELETE FROM g21gonature.loggedin WHERE id = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute removeFromLoggedInTable query");
			e.printStackTrace();
		}
	}

	/**
	 * This function INSERT row to loggedin table in the database
	 * 
	 * @param parameters id
	 */
	public void insertToLoggedInTable(ArrayList<?> parameters) {
		String sql = "INSERT INTO g21gonature.loggedin (id) values (?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute insertToLoggedInTable query");
			e.printStackTrace();
		}
	}

	/**
	 * This function checks if a given id is already login
	 * 
	 * @param parameters the id to check
	 * @return true if connected, false if not
	 */
	public boolean checkIfConnected(ArrayList<?> parameters) {
		String sql = "SELECT * FROM g21gonature.loggedin WHERE id = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));

			ResultSet res = query.executeQuery();
			if (res.next())
				return true;
		} catch (SQLException e) {
			System.out.println("Could not execute checkIfConnected query");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * This function gets guide's id a retrieve the guide from the database
	 * 
	 * @param parameters The guide's id
	 * @return Guide object
	 */
	public Guide getGuideById(ArrayList<?> parameters) {
		Guide guide = null;
		String sql = "SELECT * FROM g21gonature.guide WHERE travelerId = ? ";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();
			if (res.next())
				guide = new Guide(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5));
		} catch (SQLException e) {
			System.out.println("Could not execute getGuideById query");
			e.printStackTrace();
		}

		return guide;
	}

	/**
	 * This function checks if traveler is registered in the system with provided id
	 * and password.
	 * 
	 * @param parameters The traveler's id and password
	 * @return Traveler object
	 */
	public Employee isMemberExist(ArrayList<?> parameters) {
		EmployeeType wt;
		Employee member = null;
		String sql = "SELECT * FROM g21gonature.employees WHERE username = ? AND password = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			ResultSet res = query.executeQuery();
			if (res.next()) {
				switch (res.getString(2)) {
				case "Entrance":
					wt = EmployeeType.ENTRANCE;
					break;
				case "Park Manager":
					wt = EmployeeType.PARK_MANAGER;
					break;
				case "Service":
					wt = EmployeeType.SERVICE;
					break;
				case "Department Manager":
					wt = EmployeeType.DEPARTMENT_MANAGER;
					break;
				default:
					throw new IllegalArgumentException("Wrong role type!");
				}
				member = new Employee(Integer.parseInt(res.getString(1)), wt, Integer.parseInt(res.getString(3)),
						res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8));
			}

		} catch (SQLException e) {
			System.out.println("Could not execute isMemberExist query");
			e.printStackTrace();
		}
		return member;
	}

	/**
	 * This query deletes traveler from traveler table with given traveler ID
	 * 
	 * @param parameters The traveler id
	 */
	public void deleteFromTravelerTable(ArrayList<?> parameters) {
		String sql = "DELETE FROM g21gonature.traveler WHERE travelerId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute deleteFromTravelerTable query");
			e.printStackTrace();
		}
	}

	/**
	 * This function inserts a new guide into guide table with given guide details
	 * 
	 * @param parameters ArrayList with travelerId firstName lastName email
	 *                   phoneNumber
	 */
	public void insertGuideToGuideTable(ArrayList<?> parameters) {
		String sql = "INSERT INTO g21gonature.guide (travelerId, firstName, lastName, email, phoneNumber) values (?,?,?,?,?)";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, (String) parameters.get(4));
			query.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not execute insertGuideToGuideTable query");
			e.printStackTrace();
		}
	}

	/**
	 * This function gets an order id and return the email related to this order
	 * from the database
	 * 
	 * @param orderId the order id
	 * @return The email in this order as String
	 */
	public String getEmailByOrderID(int orderId) {
		String sql = "SELECT order.email FROM g21gonature.order WHERE orderId = ?";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setInt(1, orderId);
			ResultSet res = query.executeQuery();

			if (res.next())
				return res.getString(1);

		} catch (SQLException e) {
			System.out.println("Could not execute getEmailByOrderID");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This function add a new message for a specific traveler in the DB.
	 * 
	 * @param parameters ArrayList containing: toId, sendDate, sendTime, subject,
	 *                   content, orderId
	 * @return true on success, false otherwise
	 */
	public boolean sendMessageToTraveler(ArrayList<?> parameters) {
		String sql = "INSERT INTO g21gonature.message (recipientId,sendingDate,sendingTime,subject,messageContent,relatedOrderId) "
				+ "VALUES (?,?,?,?,?,?)";
		PreparedStatement query;
		int res = 0;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			query.setString(2, (String) parameters.get(1));
			query.setString(3, (String) parameters.get(2));
			query.setString(4, (String) parameters.get(3));
			query.setString(5, (String) parameters.get(4));
			query.setInt(6, Integer.parseInt((String) parameters.get(5)));
			res = query.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Could not execute sendMessageToTraveler query");
			e.printStackTrace();
		}
		return res == 1;
	}

	/**
	 * This function gets traveler/guide messages from message table by
	 * traveler/guide's ID
	 * 
	 * @param parameters toId traveler/guide's ID
	 * @return ArrayList of messages
	 */
	public ArrayList<Message> getMessages(ArrayList<?> parameters) {
		ArrayList<Message> messeges = new ArrayList<Message>();
		String sql = "SELECT * FROM g21gonature.message WHERE recipientId = ? ORDER BY messageId DESC";
		PreparedStatement query;
		try {
			query = conn.prepareStatement(sql);
			query.setString(1, (String) parameters.get(0));
			ResultSet res = query.executeQuery();
			/* getting all messages from query into array list */
			while (res.next()) {
				Message message = new Message(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7));
				messeges.add(message);
			}
		} catch (SQLException e) {
			System.out.println("Could not execute getMessage");
			e.printStackTrace();
		}
		return messeges;
	}


}
