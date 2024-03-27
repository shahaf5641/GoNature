package logic;

/**
 * This enum represents the possible statuses of an order in the system.
 */
public enum OrderStatusName {
    CONFIRMED,          // The order is confirmed.
    CANCELED,           // The order is canceled.
    PENDING,            // The order is pending.
    PENDING_EMAIL_SENT, // The order is pending, and an email has been sent.
    WAITING,            // The order is waiting.
    WAITING_HAS_SPOT,   // The order is waiting and has a spot.
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
            case WAITING:
                return "Waiting";
            case ENTERED_THE_PARK:
                return "Entered the park";
            case PENDING_EMAIL_SENT:
                return "Pending email sent";
            case WAITING_HAS_SPOT:
                return "Waiting has spot";
            case COMPLETED:
                return "Visit completed";
            default:
                throw new IllegalArgumentException();
        }
    }
}
