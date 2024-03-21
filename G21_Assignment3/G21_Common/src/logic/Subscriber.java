package logic;

/**
 * This class represents a subscriber
 */
@SuppressWarnings("serial")
public class Subscriber extends Traveler {

	public Subscriber(String travelerId, String firstName, String lastName, String email,
			String phoneNumber) {
		super(travelerId, firstName, lastName, email, phoneNumber);
	}
}
