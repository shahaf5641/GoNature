package Controllers.calculatePrice;

import logic.GoNatureFinals;


/**
 * GroupCasualCheckOut calculates casual group order's price
 */
public class GroupCasualCheckOut implements CheckOut {

    private int numOfVisitors;
    private final double fullPrice = GoNatureFinals.FULL_PRICE;

    private final double discountForGuidesPayAtPark = 0.9;

    public GroupCasualCheckOut(int numOfVisitors) {
    	this.numOfVisitors = numOfVisitors;
    }

    /**
     * Overwrite getPrice from CheckOut interface
     * @return return the casual group order's price
     */
    @Override
    public double getPrice() {
        return numOfVisitors * fullPrice * discountForGuidesPayAtPark;
    }
}
