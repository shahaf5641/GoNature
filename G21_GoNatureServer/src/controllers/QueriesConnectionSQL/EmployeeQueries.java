 
package controllers.QueriesConnectionSQL;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
 
import logic.Employee;
import logic.EmployeeType;
 
/**
 * Handles queries related to employees in the database.
 */
public class EmployeeQueries {
 
    private Connection connection;
 
    public EmployeeQueries(Connection connection) {
        this.connection = connection;
    }
 
    /**
     * Retrieves an employee from the database based on the provided employee ID.
     * 
     * @param parameters The employee ID.
     * @return An Employee object.
     */
    public Employee getEmployeeById(ArrayList<?> parameters) {
        Employee employee = null;
        String sql = "SELECT * FROM g21gonature.employees WHERE employeeId = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, (String) parameters.get(0));
            ResultSet resultSet = preparedStatement.executeQuery();
 
            if (resultSet.next()) {
                EmployeeType employeeType;
                switch (resultSet.getString(2)) {
                    case "Entrance":
                        employeeType = EmployeeType.ENTRANCE;
                        break;
                    case "Park Manager":
                        employeeType = EmployeeType.PARK_MANAGER;
                        break;
                    case "Service":
                        employeeType = EmployeeType.SERVICE;
                        break;
                    case "Department Manager":
                        employeeType = EmployeeType.DEPARTMENT_MANAGER;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid employee type!");
                }
                employee = new Employee(
                        Integer.parseInt(resultSet.getString(1)),
                        employeeType,
                        Integer.parseInt(resultSet.getString(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error executing getEmployeeById query");
            e.printStackTrace();
        }
 
        return employee;
    }
 
    /**
     * Retrieves an employee's password based on the provided employee ID.
     * 
     * @param employeeId The employee ID.
     * @return The employee's password.
     */
    public String getEmployeePasswordById(int employeeId) {
        String sql = "SELECT employees.password FROM g21gonature.employees WHERE employeeId = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println("Error executing getEmployeePasswordById");
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * Retrieves an employee's ID based on the provided username and password.
     * 
     * @param parameters The employee's username and password.
     * @return The employee's ID.
     */
    public String getEmployeeIdByUserAndPass(ArrayList<?> parameters) {
        String sql = "SELECT * FROM g21gonature.employees WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, (String) parameters.get(0));
            preparedStatement.setString(2, (String) parameters.get(1));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println("Error executing getEmployeeIdByUserAndPass");
            e.printStackTrace();
        }
        return null;
    }
}
 