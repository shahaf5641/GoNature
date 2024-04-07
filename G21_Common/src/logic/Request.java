package logic;

import java.io.Serializable;

/**
 * Request class represents a request for change in park parameters.
 */
@SuppressWarnings("serial")
public class Request implements Serializable {
    private int requestId;        
    private String changeParamName;    
    private String newParamValue;      
    private String oldParamValue;      
    private String requestDate;   
    private int parkId;           
    private String requestStatus; 

    /**
     * Constructor to initialize a Request object.
     * @param requestId Unique ID of the request.
     * @param changeParamName Name of the parameter to be changed.
     * @param newParamValue New value of the parameter.
     * @param oldParamValue Old value of the parameter.
     * @param requestDate Date when the request was made.
     * @param parkId ID of the park associated with the request.
     * @param requestStatus Status of the request.
     */
    public Request(int requestId, String changeParamName, String newParamValue, String oldParamValue, String requestDate,
                   int parkId, String requestStatus) {
        this.requestId = requestId;
        this.changeParamName = changeParamName;
        this.newParamValue = newParamValue;
        this.oldParamValue = oldParamValue;
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

    public String getChangeParamName() {
        return changeParamName;
    }

    public void setChangeParamName(String changeParamName) {
        this.changeParamName = changeParamName;
    }

    public String getNewParamValue() {
        return newParamValue;
    }

    public void setNewParamValue(String newParamValue) {
        this.newParamValue = newParamValue;
    }

    public String getOldParamValue() {
        return oldParamValue;
    }

    public void setOldParamValue(String oldParamValue) {
        this.oldParamValue = oldParamValue;
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
