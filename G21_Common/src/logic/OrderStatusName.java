package logic;

/**
 * This enum represents the possible statuses of an order in the system.
 */
public enum OrderStatusName {
    CONFIRMED,          // The order is confirmed.
    CANCELED,           // The order is canceled.
    PENDING,            // The order is pending.
    PENDING_24_HOURS_BEFORE, // The order is pending, 24 hours before visit.
    IN_WAITING_LIST,            // The order is IN_WAITING_LIST.
    AVAILABLE_SPOT,   // The order is IN_WAITING_LIST and has a spot.
    ENTERED_THE_PARK,   // The traveler has entered the park.
    COMPLETED,			// The visit associated with the order is completed.
    NOT_ARRIVED_NOT_CANCELED; //The traveler not arrived and not canceled.         

    /**
     * Overrides the toString method to provide custom string representations for each enum value.
     * @return A string representation of the enum value.
     */
    @Override
    public String toString() {
        switch (this) {
            case CONFIRMED:
                return "Confirmed";
            case CANCELED:
                return "Canceled";
            case NOT_ARRIVED_NOT_CANCELED:
            	return "Not arrived";
            case PENDING:
                return "Pending";
            case IN_WAITING_LIST:
                return "In waiting list";
            case ENTERED_THE_PARK:
                return "Entered the park";
            case PENDING_24_HOURS_BEFORE:
                return "Pending 24 hours before";
            case AVAILABLE_SPOT:
                return "Available spot ";
            case COMPLETED:
                return "Visit completed";
            default:
                throw new IllegalArgumentException();
        }
    }
}
