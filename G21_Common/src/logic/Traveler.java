package logic;

import java.io.Serializable;

/**
 * Represents a traveler in the system.
 */
@SuppressWarnings("serial")
public class Traveler implements Serializable {
    private String travelerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new Traveler object with the specified details.
     * 
     * @param travelerId   The unique identifier for the traveler.
     * @param firstName    The first name of the traveler.
     * @param lastName     The last name of the traveler.
     * @param email        The email address of the traveler.
     * @param phoneNumber  The phone number of the traveler.
     */
    public Traveler(String travelerId, String firstName, String lastName, String email, String phoneNumber) {
        this.travelerId = travelerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * get the traveler's unique identifier.
     * 
     * @return The traveler's unique identifier.
     */
    public String getTravelerId() {
        return travelerId;
    }

    /**
     * Sets the traveler's unique identifier.
     * 
     * @param travelerId The traveler's unique identifier.
     */
    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

    /**
     * get the traveler's first name.
     * 
     * @return The traveler's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the traveler's first name.
     * 
     * @param firstName The traveler's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get the traveler's last name.
     * 
     * @return The traveler's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the traveler's last name.
     * 
     * @param lastName The traveler's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get the traveler's email address.
     * 
     * @return The traveler's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the traveler's email address.
     * 
     * @param email The traveler's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get the traveler's phone number.
     * 
     * @return The traveler's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the traveler's phone number.
     * 
     * @param phoneNumber The traveler's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
