package controllers.sqlHandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logic.Employees;
import logic.WorkerType;

/**
 * This class handles all the queries which are related to employees
 *
 */
public class EmployeeQueries {

	private Connection conn;

	public EmployeeQueries(Connection conn) {
		this.conn = conn;
	}

		/**
		 * This function gets an id as parameter 
		 * and retrieve the relevant employee from the database
		 * 
		 * @param parameters The employee id
		 * @return Employees object
		 */
		public Employees getEmployeeById(ArrayList<?> parameters) {
			Employees employee = null;
			String sql = "SELECT * FROM g21gonature.employees WHERE employeeId = ? ";
			PreparedStatement query;
			try {
				query = conn.prepareStatement(sql);
				query.setString(1, (String) parameters.get(0));
				ResultSet res = query.executeQuery();

				if (res.next()) {
					WorkerType wt;
					switch (res.getString(2)) {
					case "Entrance":
						wt = WorkerType.ENTRANCE;
						break;
					case "Park Manager":
						wt = WorkerType.PARK_MANAGER;
						break;
					case "Service":
						wt = WorkerType.SERVICE;
						break;
					case "Department Manager":
						wt = WorkerType.DEPARTMENT_MANAGER;
						break;
					default:
						throw new IllegalArgumentException("Wrong role type!");
					}
					employee = new Employees(Integer.parseInt(res.getString(1)), wt, Integer.parseInt(res.getString(3)),
							res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8));
				}
			} catch (SQLException e) {
				System.out.println("Could not execute getEmployeeById query");
				e.printStackTrace();
			}

			return employee;
		}

		
		
		public String getEmployeeIdByPassUser(ArrayList<?> parameters) {
			String sql = "SELECT * FROM g21gonature.employees WHERE username = ? AND password = ?";
			PreparedStatement query;
			try {
				query = conn.prepareStatement(sql);
				query.setString(1, (String) parameters.get(0));
				query.setString(2, (String) parameters.get(1));
				ResultSet res = query.executeQuery();
				if (res.next())
					return res.getString(1);

			} catch (SQLException e) {
				System.out.println("Could not execute getEmployeeIdByPassUser");
				e.printStackTrace();
			}
			return null;
		}
		
		
		
		
		/**
		 * This function gets an id as parameter 
		 * and retrieve employee's password from the database
		 * 
		 * @param employeeId The employee id
		 * @return The employee's password as string
		 */
		
		
		
		public String getEmployeePasswordById(int employeeId) {
			String sql = "SELECT employees.password FROM g21gonature.employees WHERE employeeId = ?";
			PreparedStatement query;
			try {
				query = conn.prepareStatement(sql);
				query.setInt(1, employeeId);
				ResultSet res = query.executeQuery();

				if (res.next())
					return res.getString(1);

			} catch (SQLException e) {
				System.out.println("Could not execute getEmployeePasswordById");
				e.printStackTrace();
			}
			return null;
		}
	
}
