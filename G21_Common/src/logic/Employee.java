package logic;

import java.io.Serializable;

/**
 * This class represents an employee in the park.
 */
@SuppressWarnings("serial")
public class Employee implements Serializable {
    // Instance variables representing employee details
    private int employeeId;
    private EmployeeType job;
    private int parkId;
    private String FirstName;
    private String LastName;
    private String email;
    private String username;
    private String password;

    /**
     * Constructor to initialize an Employee object.
     * @param employeeId The ID of the employee.
     * @param job The job of the employee (EmployeeType enum).
     * @param parkId The ID of the park associated with the employee.
     * @param FirstName The first name of the employee.
     * @param LastName The last name of the employee.
     * @param email The email address of the employee.
     * @param username The username of the employee.
     * @param password The password of the employee.
     */
    public Employee(int employeeId, EmployeeType job, int parkId, String FirstName, String LastName,
                    String email, String username, String password) {
        this.employeeId = employeeId;
        this.job = job;
        this.parkId = parkId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getter and setter methods for accessing and modifying the instance variables

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeType getJob() {
        return job;
    }

    public void setJob(EmployeeType job) {
        this.job = job;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
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