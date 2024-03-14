package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * DiscountTb represents a discount in the park.
 * This class is suited for use in a table view.
 */
public class DiscountTb {
    // Instance variables representing discount details as JavaFX properties
    private SimpleStringProperty discountId;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty startDate;
    private SimpleStringProperty endDate;
    private SimpleIntegerProperty parkId;
    private SimpleStringProperty status;

    /**
     * Constructor to initialize a DiscountTb object.
     * @param discountId The ID of the discount.
     * @param amount The amount of the discount.
     * @param startDate The start date of the discount.
     * @param endDate The end date of the discount.
     * @param parkId The ID of the park associated with the discount.
     * @param status The status of the discount (e.g., active, inactive).
     */
    public DiscountTb(String discountId, Double amount, String startDate,
            String endDate, int parkId, String status) {
        // Initialize JavaFX properties with provided values
        this.discountId = new SimpleStringProperty(discountId);
        this.amount = new SimpleDoubleProperty(amount);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.parkId = new SimpleIntegerProperty(parkId);
        this.status = new SimpleStringProperty(status);
    }

    // Getter and setter methods for accessing and modifying the instance variables

    public String getDiscountId() {
        return discountId.get();
    }

    public void setDiscountId(SimpleStringProperty discountId) {
        this.discountId = discountId;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(SimpleStringProperty status) {
        this.status = status;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(SimpleDoubleProperty amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(SimpleStringProperty startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate.get();
    }

    public void setEndDate(SimpleStringProperty endDate) {
        this.endDate = endDate;
    }

    public int getParkId() {
        return parkId.get();
    }

    public void setParkId(SimpleIntegerProperty parkId) {
        this.parkId = parkId;
    }
}
