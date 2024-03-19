package Controllers.calculatePrice;

import Controllers.OrderControl;
import logic.Discount;
import logic.GoNatureFinals;

/**
 * RegularCheckOut implements CheckOut interface
 * RegularCheckOut calculates the regular order's price
 *
 */
public class RegularCheckOut implements CheckOut {

	private int numberOfVisitors;


	private double fullPrice = GoNatureFinals.FULL_PRICE;

	public RegularCheckOut(int numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
		
	}

	/**
	 * implements getPrice from CheckOut interface
	 * 
	 * @return return the regular order's price
	 */
	@Override
	public double getPrice() {
		
		double basePrice = (this.numberOfVisitors * this.fullPrice);
		
		return basePrice;
	}

	
	

}
