package Controllers.calculatePrice;

import logic.GoNatureFinals;

/**
 * GroupCasualCheckOut extends CheckOutDecorator
 * GroupCasualCheckOut calculates casual group order's price
 *
 */
public class GroupCasualCheckOut extends CheckOutDecorator {


	public GroupCasualCheckOut(CheckOut tempCheckOut) {
		super(tempCheckOut);
	}

	/**
	 * Overrite getPrice from CheckOutDecorator
	 * 
	 * @return return the casual group order's price
	 */
	public double getPrice() {
		return GoNatureFinals.FULL_PRICE;
	}
}
