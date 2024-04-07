package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * ReportTb class represents a report in the park system, suited for table view.
 */
public class ReportTable {
    private SimpleIntegerProperty reportID;   
    private SimpleStringProperty reportType;  
    private SimpleIntegerProperty parkID;     
    private SimpleIntegerProperty month;     
    private SimpleStringProperty comment;     

    /**
     * Constructor to initialize a ReportTb object.
     * @param reportID ID of the report.
     * @param reportType Type of the report.
     * @param parkID ID of the park associated with the report.
     * @param month Month of the report.
     * @param comment Additional comment for the report.
     */
    public ReportTable(int reportID, String reportType, int parkID, int month, String comment) {
        this.reportID = new SimpleIntegerProperty(reportID);
        this.reportType = new SimpleStringProperty(reportType);
        this.parkID = new SimpleIntegerProperty(parkID);
        this.month = new SimpleIntegerProperty(month);
        this.comment = new SimpleStringProperty(comment);
    }

    /**
     * Constructor to initialize a ReportTb object from a Report object.
     * @param report The Report object from which to initialize.
     */
    public ReportTable(Report report) {
        this.reportID = new SimpleIntegerProperty(report.getReportID());
        this.reportType = new SimpleStringProperty(report.getReportType());
        this.parkID = new SimpleIntegerProperty(report.getParkID());
        this.month = new SimpleIntegerProperty(report.getMonth());
        this.comment = new SimpleStringProperty(report.getComment());
    }

    // Getter and setter methods for each property

    public int getReportID() {
        return reportID.get();
    }

    public void setReportID(SimpleIntegerProperty reportID) {
        this.reportID = reportID;
    }

    public String getReportType() {
        return reportType.get();
    }

    public void setReportType(SimpleStringProperty reportType) {
        this.reportType = reportType;
    }

    public int getParkID() {
        return parkID.get();
    }

    public void setParkID(SimpleIntegerProperty parkID) {
        this.parkID = parkID;
    }

    public int getMonth() {
        return month.get();
    }

    public void setMonth(SimpleIntegerProperty month) {
        this.month = month;
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(SimpleStringProperty comment) {
        this.comment = comment;
    }
}
