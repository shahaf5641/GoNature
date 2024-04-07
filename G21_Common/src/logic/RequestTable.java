package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * RequestTb class represents a request for change in park parameters suited for table view.
 */
public class RequestTable {
    private SimpleIntegerProperty requestId;    
    private SimpleStringProperty changeParamName;    
    private SimpleStringProperty newParamValue;      
    private SimpleStringProperty oldParamValue;      
    private SimpleStringProperty requestDate;   
    private SimpleIntegerProperty parkId;       
    private SimpleStringProperty requestStatus; 

    /**
     * Constructor to initialize a RequestTb object.
     * @param requestId ID of the request.
     * @param changeParamName Name of the parameter to be changed.
     * @param newParamValue New value of the parameter.
     * @param oldParamValue Old value of the parameter.
     * @param requestDate Date when the request was made.
     * @param parkId ID of the park associated with the request.
     * @param requestStatus Status of the request.
     */
    public RequestTable(int requestId, String changeParamName, String newParamValue, String oldParamValue, String requestDate, int parkId, String requestStatus) {
        this.requestId = new SimpleIntegerProperty(requestId);
        this.changeParamName = new SimpleStringProperty(changeParamName);
        this.newParamValue = new SimpleStringProperty(newParamValue);
        this.oldParamValue = new SimpleStringProperty(oldParamValue);
        this.requestDate = new SimpleStringProperty(requestDate);
        this.parkId = new SimpleIntegerProperty(parkId);
        this.requestStatus = new SimpleStringProperty(requestStatus);
    }

    // Getter and setter methods for each property

    public int getRequestId() {
        return requestId.get();
    }

    public void setRequestId(SimpleIntegerProperty requestId) {
        this.requestId = requestId;
    }

    public String getChangeParamName() {
        return changeParamName.get();
    }

    public void setChangeParamName(SimpleStringProperty changeParamName) {
        this.changeParamName = changeParamName;
    }

    public String getNewParamValue() {
        return newParamValue.get();
    }

    public void setNewValue(SimpleStringProperty newParamValue) {
        this.newParamValue = newParamValue;
    }

    public String getOldParamValue() {
        return oldParamValue.get();
    }

    public void setOldValue(SimpleStringProperty oldParamValue) {
        this.oldParamValue = oldParamValue;
    }

    public String getRequestDate() {
        return requestDate.get();
    }

    public void setRequestDate(SimpleStringProperty requestDate) {
        this.requestDate = requestDate;
    }

    public int getParkId() {
        return parkId.get();
    }

    public void setParkId(SimpleIntegerProperty parkId) {
        this.parkId = parkId;
    }

    public String getRequestStatus() {
        return requestStatus.get();
    }

    public void setRequestStatus(SimpleStringProperty requestStatus) {
        this.requestStatus = requestStatus;
    }
}
