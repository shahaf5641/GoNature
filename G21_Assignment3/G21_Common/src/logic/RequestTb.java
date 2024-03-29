package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * RequestTb class represents a request for change in park parameters suited for table view.
 */
public class RequestTb {
    private SimpleIntegerProperty requestId;    
    private SimpleStringProperty changeName;    
    private SimpleStringProperty newValue;      
    private SimpleStringProperty oldValue;      
    private SimpleStringProperty requestDate;   
    private SimpleIntegerProperty parkId;       
    private SimpleStringProperty requestStatus; 

    /**
     * Constructor to initialize a RequestTb object.
     * @param requestId ID of the request.
     * @param changeName Name of the parameter to be changed.
     * @param newValue New value of the parameter.
     * @param oldValue Old value of the parameter.
     * @param requestDate Date when the request was made.
     * @param parkId ID of the park associated with the request.
     * @param requestStatus Status of the request.
     */
    public RequestTb(int requestId, String changeName, String newValue, String oldValue, String requestDate, int parkId, String requestStatus) {
        this.requestId = new SimpleIntegerProperty(requestId);
        this.changeName = new SimpleStringProperty(changeName);
        this.newValue = new SimpleStringProperty(newValue);
        this.oldValue = new SimpleStringProperty(oldValue);
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

    public String getChangeName() {
        return changeName.get();
    }

    public void setChangeName(SimpleStringProperty changeName) {
        this.changeName = changeName;
    }

    public String getNewValue() {
        return newValue.get();
    }

    public void setNewValue(SimpleStringProperty newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue.get();
    }

    public void setOldValue(SimpleStringProperty oldValue) {
        this.oldValue = oldValue;
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
