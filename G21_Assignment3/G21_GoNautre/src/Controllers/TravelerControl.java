package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.ClientToServerRequest;
import logic.Traveler;
import logic.ClientToServerRequest.Request;

/**
 * TravelerControl class handles all the traveler related functionalities
 */
@SuppressWarnings("rawtypes")
public class TravelerControl {

	/**
	 * This function get an id and returns Subscriber object that match the given id.
	 * 
	 * @param id the id of the subscriber
	 * @return Subscriber object, null if not exist
	 */
	

	/**
	 * This function gets an id and check if there is a traveler (regular of subscriber)
	 * that match this id.
	 * 
	 * @param id the id to check
	 * @return true if the traveler exist in the system, false otherwise
	 */
	public static boolean isTravelerExist(String id) {
		/* First we check if the id is subscriber */
		
		
			/*
			 * If there is no subscriber with such id
			 * we check if the id is regular traveler
			 */
			ClientToServerRequest request = new ClientToServerRequest<>(Request.TRAVELER_LOGIN_ID,
					new ArrayList<String>(Arrays.asList(id)));
			ClientUI.chat.accept(request);
			Traveler traveler = (Traveler) ChatClient.responseFromServer.getResultSet().get(0);
			if (traveler != null) // If not null means the traveler exist
				return true;

		
		return false;
	}

	/**
	 * This function deletes a traveler from traveler table by his ID
	 * 
	 * @param id traveler ID
	 */
	public static void deleteFromTravelerTable(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.DELETE_TRAVELER,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
	}

	
	

	/**
	 * This function inserts subscriber's credit cared into card table
	 * 
	 * @param id             subscriebr's ID
	 * @param cardNumber     credit card number
	 * @param cardExpiryDate credit card expiration date
	 * @param ccv            three numbers in the back of the card
	 */
	public static void insertCardToCreditCardTable(String id, String cardNumber, String cardExpiryDate, String ccv) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.INSERT_TO_CREDITCARD,
				new ArrayList<String>(Arrays.asList(id, cardNumber, cardExpiryDate, ccv)));
		ClientUI.chat.accept(request);
	}

	/**
	 * This function get an id and returns Traveler object that match the given id.
	 * 
	 * @param id - the id of the traveler
	 * @return Traveler object
	 */
	public static Traveler getTraveler(String id) {
		ClientToServerRequest request = new ClientToServerRequest<>(Request.TRAVELER_LOGIN_ID,
				new ArrayList<String>(Arrays.asList(id)));
		ClientUI.chat.accept(request);
		Traveler traveler = (Traveler) ChatClient.responseFromServer.getResultSet().get(0);
		return traveler;
	}

}
