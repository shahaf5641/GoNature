package Controllers.CalculatePrice;

import logic.GoNatureConstants;

public class CasualSoloFamilyVisitCheckOut  implements CheckOut {
	private int numOfVisitors;
    private final double fullPrice = GoNatureConstants.FULL_TICKET_PRICE;


    public CasualSoloFamilyVisitCheckOut(int numOfVisitors) {
        this.numOfVisitors = numOfVisitors;
    }

    /**
     * Overwrite getPrice from CheckOut interface
     * @return return the casual group order's price
     */
    @Override
    public double getPrice() {
        return numOfVisitors * fullPrice ;
    }

}
