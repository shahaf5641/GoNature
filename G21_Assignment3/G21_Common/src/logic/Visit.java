package logic;

/**
 * Represents a visit in the park.
 */
public class Visit {
    private String visitId;
    private String travelerId;
    private int parkId;
    private String entranceTime;
    private String exitTime;
    private String visitDate;

    /**
     * Constructs a new Visit object with the specified details.
     * 
     * @param visitId      The unique identifier for the visit.
     * @param travelerId   The unique identifier for the traveler associated with the visit.
     * @param parkId       The unique identifier for the park visited.
     * @param entranceTime The time when the visit started.
     * @param exitTime     The time when the visit ended.
     * @param visitDate    The date of the visit.
     */
    public Visit(String visitId, String travelerId, int parkId, String entranceTime, String exitTime, String visitDate) {
        this.visitId = visitId;
        this.travelerId = travelerId;
        this.parkId = parkId;
        this.entranceTime = entranceTime;
        this.exitTime = exitTime;
        this.visitDate = visitDate;
    }

    // Getters and setters for Visit attributes

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(String entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
