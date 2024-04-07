package Controllers.CalculatePrice;

import logic.GoNatureConstants;


/**
 * GroupCasualCheckOut calculates casual group order's price
 */
public class GuidePayAtParkCheckOut implements CheckOut {

    private int numOfVisitors;
    private final double fullPrice = GoNatureConstants.FULL_TICKET_PRICE;

    private final double discountForGuidesPayAtPark = 0.75;

    public GuidePayAtParkCheckOut(int numOfVisitors) {
    	this.numOfVisitors = numOfVisitors ;
    }

    /**
     * Overwrite getPrice from CheckOut interface
     * @return return the casual group order's price
     */
    @Override
    public double getPrice() {
        return (numOfVisitors-1) * fullPrice * discountForGuidesPayAtPark;
    }
}
