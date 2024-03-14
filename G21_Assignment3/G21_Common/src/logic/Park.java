package logic;

import java.io.Serializable;

/**
 * Park class represents a park in the system.
 */
@SuppressWarnings("serial")
public class Park implements Serializable {
    private int parkId;                         
    private String parkName;                    
    private int maxVisitors;                    
    private int estimatedStayTime;              
    private int gapBetweenMaxAndCapacity;       
    private int currentVisitors;                

    /**
     * Constructor to initialize Park object.
     * @param parkId Unique identifier for the park.
     * @param parkName Name of the park.
     * @param maxVisitors Maximum number of visitors allowed in the park.
     * @param estimatedStayTime Estimated stay time for visitors in minutes.
     * @param gapBetweenMaxAndCapacity Gap between maximum capacity and actual capacity.
     * @param currentVisitors Current number of visitors in the park.
     */
    public Park(int parkId, String parkName, int maxVisitors, int estimatedStayTime,
                int gapBetweenMaxAndCapacity, int currentVisitors) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.maxVisitors = maxVisitors;
        this.estimatedStayTime = estimatedStayTime;
        this.gapBetweenMaxAndCapacity = gapBetweenMaxAndCapacity;
        this.currentVisitors = currentVisitors;
    }

    /**
     * Getter for parkId.
     * @return The unique identifier for the park.
     */
    public int getParkId() {
        return parkId;
    }

    /**
     * Setter for parkId.
     * @param parkId The unique identifier for the park.
     */
    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    /**
     * Getter for parkName.
     * @return The name of the park.
     */
    public String getParkName() {
        return parkName;
    }

    /**
     * Setter for parkName.
     * @param parkName The name of the park.
     */
    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    /**
     * Getter for maxVisitors.
     * @return The maximum number of visitors allowed in the park.
     */
    public int getMaxVisitors() {
        return maxVisitors;
    }

    /**
     * Setter for maxVisitors.
     * @param maxVisitors The maximum number of visitors allowed in the park.
     */
    public void setMaxVisitors(int maxVisitors) {
        this.maxVisitors = maxVisitors;
    }

    /**
     * Getter for estimatedStayTime.
     * @return The estimated stay time for visitors in minutes.
     */
    public int getEstimatedStayTime() {
        return estimatedStayTime;
    }

    /**
     * Setter for estimatedStayTime.
     * @param estimatedStayTime The estimated stay time for visitors in minutes.
     */
    public void setEstimatedStayTime(int estimatedStayTime) {
        this.estimatedStayTime = estimatedStayTime;
    }

    /**
     * Getter for gapBetweenMaxAndCapacity.
     * @return The gap between maximum capacity and actual capacity.
     */
    public int getGapBetweenMaxAndCapacity() {
        return gapBetweenMaxAndCapacity;
    }

    /**
     * Setter for gapBetweenMaxAndCapacity.
     * @param gapBetweenMaxAndCapacity The gap between maximum capacity and actual capacity.
     */
    public void setGapBetweenMaxAndCapacity(int gapBetweenMaxAndCapacity) {
        this.gapBetweenMaxAndCapacity = gapBetweenMaxAndCapacity;
    }

    /**
     * Getter for currentVisitors.
     * @return The current number of visitors in the park.
     */
    public int getCurrentVisitors() {
        return currentVisitors;
    }

    /**
     * Setter for currentVisitors.
     * @param currentVisitors The current number of visitors in the park.
     */
    public void setCurrentVisitors(int currentVisitors) {
        this.currentVisitors = currentVisitors;
    }
}
