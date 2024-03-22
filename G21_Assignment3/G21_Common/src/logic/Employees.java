package logic;

import java.io.Serializable;

/**
 * This class represents an employee in the park.
 */
@SuppressWarnings("serial")
public class Employees implements Serializable {
    // Instance variables representing employee details
    private int employeeId;
    private WorkerType role;
    private int parkId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    /**
     * Constructor to initialize an Employees object.
     * @param employeeId The ID of the employee.
     * @param role The role of the employee (WorkerType enum).
     * @param parkId The ID of the park associated with the employee.
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param email The email address of the employee.
     * @param username The username of the employee.
     * @param password The password of the employee.
     */
    public Employees(int employeeId, WorkerType role, int parkId, String firstName, String lastName,
    		String email,String username, String password) {
        this.employeeId = employeeId;
        this.role = role;
        this.parkId = parkId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username= username;
        this.password = password;
    }

    // Getter and setter methods for accessing and modifying the instance variables

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public WorkerType getRole() {
        return role;
    }

    public void setRole(WorkerType role) {
        this.role = role;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
