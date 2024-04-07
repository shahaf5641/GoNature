package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a response from the server to the client. 
 * relevant for database operations like INSERT, UPDATE, or DELETE.
 * @param <T> The type of data contained in the response.
 */
@SuppressWarnings("serial")
public class responseFromServerToClient<T> implements Serializable {

    private boolean success;                   // Indicates whether the operation was successful.
    private int numOfRowsAffected;                 // Number of rows affected by the operation.
    private ArrayList<T> successSet = new ArrayList<T>(); // success set containing data of type T.
    private int arrayListSize;                // Size of the result set ArrayList.

    // Getter and setter methods for each attribute

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getNumOfRowsAffected() {
        return numOfRowsAffected;
    }

    public void setNumOfRowsAffected(int numOfRowsAffected) {
        this.numOfRowsAffected = numOfRowsAffected;
    }

    public ArrayList<T> getSuccessSet() {
        return successSet;
    }

    public void setSuccessSet(ArrayList<T> resultSet) {
        this.successSet = resultSet;
    }

    public int getArrayListSize() {
        return arrayListSize;
    }

    public void setArrayListSize(int arrayListSize) {
        this.arrayListSize = arrayListSize;
    }
}
