package logic;

/**
 * Represents the enumerated type of employees.
 */
public enum EmployeeType {
    ENTRANCE("Entrance"), 
    SERVICE("Service"), 
    PARK_MANAGER("Park Manager"), 
    DEPARTMENT_MANAGER("Department Manager");

    private String type;

    /**
     * Constructs a EmployeeType enum with the specified string representation.
     * 
     * @param type The string representation of the employee type.
     */
    EmployeeType(String type) {
        this.type = type;
    }

    /**
     * get the string representation of the employee type.
     * 
     * @return The string representation of the employee type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the string representation of the employee type.
     * 
     * @param str The string representation to set.
     */
    public void setStr(String type) {
        this.type = type;
    }
}
