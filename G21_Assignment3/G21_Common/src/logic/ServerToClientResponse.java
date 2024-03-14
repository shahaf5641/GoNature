package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a response from the server to the client. 
 * relevant for database operations like INSERT, UPDATE, or DELETE.
 * @param <T> The type of data contained in the response.
 */
@SuppressWarnings("serial")
public class ServerToClientResponse<T> implements Serializable {

    private boolean result;                   // Indicates whether the operation was successful.
    private int rowsAffected;                 // Number of rows affected by the operation.
    private ArrayList<T> resultSet = new ArrayList<T>(); // Result set containing data of type T.
    private int arrayListSize;                // Size of the result set ArrayList.

    // Getter and setter methods for each attribute

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public ArrayList<T> getResultSet() {
        return resultSet;
    }

    public void setResultSet(ArrayList<T> resultSet) {
        this.resultSet = resultSet;
    }

    public int getArrayListSize() {
        return arrayListSize;
    }

    public void setArrayListSize(int arrayListSize) {
        this.arrayListSize = arrayListSize;
    }
}
