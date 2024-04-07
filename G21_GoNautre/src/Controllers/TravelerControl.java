package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
import client.ClientUI;
import logic.RequestsFromClientToServer;
import logic.Traveler;
import logic.RequestsFromClientToServer.Request;
import logic.Guide;

@SuppressWarnings("rawtypes")
public class TravelerControl {
    
    /**
     * Deletes a traveler from the database.
     *
     * @param id The ID of the traveler to delete.
     */
    public static void travelerDeleteFromDB(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.DELETE_TRAVELER,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
    }

    /**
     * Checks if a traveler is present in the system.
     *
     * @param id The ID of the traveler to check.
     * @return True if the traveler is present, false otherwise.
     */
    public static boolean checkTravelerPresence(String id) {
        Guide guide = findGuide(id);
        if (guide != null) {
            return true;
        } else {
            RequestsFromClientToServer request = new RequestsFromClientToServer<>(Request.TRAVELER_LOGIN_ID,
                    new ArrayList<String>(Arrays.asList(id)));
            ClientUI.chat.accept(request);
            Traveler traveler = (Traveler) ChatClient.responseFromServer.getSuccessSet().get(0);
            return traveler != null;
        }
    }
    
    /**
     * Finds a traveler by ID.
     *
     * @param id The ID of the traveler to find.
     * @return The traveler object if found, otherwise null.
     */
    public static Traveler findTraveler(String id) {
        RequestsFromClientToServer request = new RequestsFromClientToServer<>(Request.TRAVELER_LOGIN_ID,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return (Traveler) ChatClient.responseFromServer.getSuccessSet().get(0);
    }

    /**
     * Adds a guide to the database.
     *
     * @param id          The ID of the guide.
     * @param firstName   The first name of the guide.
     * @param lastName    The last name of the guide.
     * @param email       The email of the guide.
     * @param phoneNumber The phone number of the guide.
     */
    public static void addGuideToDB(String id, String firstName, String lastName, String email, String phoneNumber) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.INSERT_GUIDE,
                new ArrayList<String>(Arrays.asList(id, firstName, lastName, email, phoneNumber)));
        ClientUI.chat.accept(request);
    }

    /**
     * Finds a guide by ID.
     *
     * @param id The ID of the guide to find.
     * @return The guide object if found, otherwise null.
     */
    public static Guide findGuide(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_GUIDE,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return (Guide) ChatClient.responseFromServer.getSuccessSet().get(0);
    }
}