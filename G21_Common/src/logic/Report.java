package logic;

import java.io.Serializable;

/**
 * Report class represents a report in the park system.
 */
@SuppressWarnings("serial")
public class Report implements Serializable {
    private int reportID;       
    private String reportType;  
    private int parkID;         
    private int month;          
    private String comment;     

    /**
     * Constructor to initialize a Report object.
     * @param reportID Unique identifier for the report.
     * @param reportType Type of the report.
     * @param parkID ID of the park associated with the report.
     * @param month Month of the report.
     * @param comment Additional comment for the report.
     */
    public Report(int reportID, String reportType, int parkID, int month, String comment) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.parkID = parkID;
        this.month = month;
        this.comment = comment;
    }

    /**
     * Getter for reportID.
     * @return The unique identifier for the report.
     */
    public int getReportID() {
        return reportID;
    }

    /**
     * Setter for reportID.
     * @param reportID The unique identifier for the report.
     */
    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    /**
     * Getter for reportType.
     * @return The type of the report.
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Setter for reportType.
     * @param reportType The type of the report.
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Getter for parkID.
     * @return The ID of the park associated with the report.
     */
    public int getParkID() {
        return parkID;
    }

    /**
     * Setter for parkID.
     * @param parkID The ID of the park associated with the report.
     */
    public void setParkID(int parkID) {
        this.parkID = parkID;
    }

    /**
     * Getter for month.
     * @return The month of the report.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for month.
     * @param month The month of the report.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Getter for comment.
     * @return The additional comment for the report.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for comment.
     * @param comment The additional comment for the report.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
