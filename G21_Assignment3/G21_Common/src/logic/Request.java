package logic;

import java.io.Serializable;

/**
 * Request class represents a request for change in park parameters.
 */
@SuppressWarnings("serial")
public class Request implements Serializable {
    private int requestId;        
    private String changeName;    
    private String newValue;      
    private String oldValue;      
    private String requestDate;   
    private int parkId;           
    private String requestStatus; 

    /**
     * Constructor to initialize a Request object.
     * @param requestId Unique ID of the request.
     * @param changeName Name of the parameter to be changed.
     * @param newValue New value of the parameter.
     * @param oldValue Old value of the parameter.
     * @param requestDate Date when the request was made.
     * @param parkId ID of the park associated with the request.
     * @param requestStatus Status of the request.
     */
    public Request(int requestId, String changeName, String newValue, String oldValue, String requestDate,
                   int parkId, String requestStatus) {
        this.requestId = requestId;
        this.changeName = changeName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.requestDate = requestDate;
        this.parkId = parkId;
        this.requestStatus = requestStatus;
    }

    // Getter and setter methods for each property

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
