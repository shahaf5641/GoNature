package logic;

import java.io.Serializable;

/**
 * Represents the visit report for each visit.
 */
@SuppressWarnings("serial")
public class VisitReport implements Serializable {
    private int sum;
    private String data;

    /**
     * Constructs a new VisitReport object with the specified sum and data.
     * 
     * @param sum  The sum associated with the visit report.
     * @param data The data of the visit report.
     */
    public VisitReport(int sum, String data) {
        this.sum = sum;
        this.data = data;
    }

    /**
     * get the sum associated with the visit report.
     * 
     * @return The sum associated with the visit report.
     */
    public int getSum() {
        return sum;
    }

    /**
     * Sets the sum associated with the visit report.
     * 
     * @param sum The sum to set.
     */
    public void setSum(int sum) {
        this.sum = sum;
    }

    /**
     * get the data of the visit report.
     * 
     * @return The data of the visit report.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data of the visit report.
     * 
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
}
