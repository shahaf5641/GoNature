package logic;

/**
 * Creditcard class represents a credit card of a traveler.
 */

public class Creditcard {
	// Instance variables representing credit card details
	private String subscriberId;
	private String cardNumber;
	private String cardExpiryDate;
	private int cvc;

    /**
     * Constructor to initialize a Creditcard object.
     * @param subscriberId The ID of the subscriber to whom the credit card belongs.
     * @param cardNumber The credit card number.
     * @param cardExpiryDate The expiry date of the credit card.
     * @param cvc The Card Verification Code (CVC) of the credit card.
     */
	public Creditcard(String subscriberId, String cardNumber, String cardExpiryDate, int cvc) {
		this.subscriberId = subscriberId;
		this.cardNumber = cardNumber;
		this.cardExpiryDate = cardExpiryDate;
		this.cvc = cvc;
	}

	 // Getter and setter methods for accessing and modifying the instance variables
	public String getSubscriberId() { 
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public int getCvc() {
		return cvc;
	}

	public void setCvc(int cvc) {
		this.cvc = cvc;
	}

}
