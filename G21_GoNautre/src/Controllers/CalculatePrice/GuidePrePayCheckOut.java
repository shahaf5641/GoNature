package Controllers.CalculatePrice;

import logic.GoNatureConstants;


/**
 * GroupCasualCheckOut calculates casual group order's price
 */
public class GuidePrePayCheckOut implements CheckOut {

    private int numOfVisitors;
    private final double fullPrice = GoNatureConstants.FULL_TICKET_PRICE;

    private final double discountForGuidesPayAtPark = 0.75;
    private final double discountForGuidesPrePayAtPark = 0.88;


    public GuidePrePayCheckOut(int numOfVisitors) {
    	this.numOfVisitors = numOfVisitors ;
    }

    /**
     * Overwrite getPrice from CheckOut interface
     * @return return the casual group order's price
     */
    @Override
    public double getPrice() {
        return (numOfVisitors-1) * fullPrice * discountForGuidesPayAtPark * discountForGuidesPrePayAtPark;
    }
}
