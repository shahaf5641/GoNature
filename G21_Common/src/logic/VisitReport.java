package logic;

import java.io.Serializable;

/**
 * Represents the visit report for each visit.
 */
@SuppressWarnings("serial")
public class VisitReport implements Serializable {
    private int sumVisitors;
    private String dataReport;

    /**
     * Constructs a new VisitReport object with the specified sum and data.
     * 
     * @param sumVisitors  The sum associated with the visit report.
     * @param data The data of the visit report.
     */
    public VisitReport(int sumVisitors, String dataReport) {
        this.sumVisitors = sumVisitors;
        this.dataReport = dataReport;
    }

    /**
     * get the sum associated with the visit report.
     * 
     * @return The sum associated with the visit report.
     */
    public int getSumVisitors() {
        return sumVisitors;
    }

    /**
     * Sets the sum associated with the visit report.
     * 
     * @param sum The sum to set.
     */
    public void setSumVisitors(int sumVisitors) {
        this.sumVisitors = sumVisitors;
    }

    /**
     * get the data of the visit report.
     * 
     * @return The data of the visit report.
     */
    public String getDataReport() {
        return dataReport;
    }

    /**
     * Sets the data of the visit report.
     * 
     * @param data The data to set.
     */
    public void setData(String dataReport) {
        this.dataReport = dataReport;
    }
}
