package logic;

import java.io.Serializable;

/**
 * Discount class represents a discount in the park.
 */

@SuppressWarnings("serial")
public class Discount implements Serializable {
	// Instance variables representing discount details
	private int discountId;
	private Double amount;
	private String startDate;
	private String endDate;
	private int parkId;
	private String status;

	/**
     * Constructor to initialize a Discount object.
     * @param discountId The ID of the discount.
     * @param amount The amount of discount.
     * @param startDate The start date of the discount.
     * @param endDate The end date of the discount.
     * @param parkId The ID of the park associated with the discount.
     * @param status The status of the discount (e.g., active, inactive).
     */
	public Discount(int discountId, Double amount, String startDate, String endDate, int parkId, String status) { 
		
		this.discountId = discountId;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parkId = parkId;
		this.status = status;
	}

	// Getter and setter methods for accessing and modifying the instance variables

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}