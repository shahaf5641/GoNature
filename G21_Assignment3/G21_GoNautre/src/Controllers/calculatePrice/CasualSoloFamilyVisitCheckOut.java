package Controllers.calculatePrice;

import logic.GoNatureFinals;

public class CasualSoloFamilyVisitCheckOut  implements CheckOut {
	private int numOfVisitors;
    private final double fullPrice = GoNatureFinals.FULL_PRICE;


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
