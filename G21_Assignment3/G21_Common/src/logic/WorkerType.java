package logic;

/**
 * Represents the enumerated type of workers.
 */
public enum WorkerType {
    ENTRANCE("Entrance"), 
    SERVICE("Service"), 
    PARK_MANAGER("Park Manager"), 
    DEPARTMENT_MANAGER("Department Manager");

    private String str;

    /**
     * Constructs a WorkerType enum with the specified string representation.
     * 
     * @param str The string representation of the worker type.
     */
    WorkerType(String str) {
        this.str = str;
    }

    /**
     * get the string representation of the worker type.
     * 
     * @return The string representation of the worker type.
     */
    public String getStr() {
        return str;
    }

    /**
     * Sets the string representation of the worker type.
     * 
     * @param str The string representation to set.
     */
    public void setStr(String str) {
        this.str = str;
    }
}
