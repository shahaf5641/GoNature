 
package Controllers;
 
import java.util.ArrayList;
import java.util.Arrays;
 
import client.ChatClient;
import client.ClientUI;
import logic.Employee;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
import logic.Traveler;
 
/**
 * The VerificationControl class provides methods for user verification and authentication.
 * It includes functionality to check employee and traveler presence, manage user connections, and handle logouts.
 */
public class VerificationControl {
 
    /**
     * Checks if an employee is present in the system with the given username and password.
     *
     * @param username The username of the employee.
     * @param pass     The password of the employee.
     * @return True if the employee is present, false otherwise.
     */
    public static boolean checkEmployeePresence(String username, String pass) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.EMPLOYEE_LOGIN,
                new ArrayList<String>(Arrays.asList(username, pass)));
        ClientUI.chat.accept(request);
        Employee member = (Employee) ChatClient.responseFromServer.getSuccessSet().get(0);
        return member != null;
    }
 
    /**
     * Checks if a traveler is present in the system with the given ID.
     *
     * @param id The ID of the traveler.
     * @return True if the traveler is present, false otherwise.
     */
    public static boolean checkTravelerPresence(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.TRAVELER_LOGIN_ID,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        Traveler traveler = (Traveler) ChatClient.responseFromServer.getSuccessSet().get(0);
        return traveler != null;
    }
 
    /**
     * Checks if a user is connected.
     *
     * @param id The ID of the user.
     * @return True if the user is connected, false otherwise.
     */
    public static boolean Connected(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.CONNECTED,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.isSuccess();
    }
 
    /**
     * Logs out a user.
     *
     * @param id The ID of the user to logout.
     */
    public static void Logout(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.LOGOUT,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
    }
 
    /**
     * Handles traveler login based on ID.
     *
     * @param id The ID of the traveler.
     * @return An integer representing the login status:
     * - 0 if logged in successfully,
     * - 1 if already connected,
     * - 2 if not connected and traveler does not exist.
     */
    public static int travelerLoginById(String id) {
        if (Connected(id))
            return 1;
        else {
            if (TravelerControl.checkTravelerPresence(id)) {
                enterToLoggedInDB(id);
                return 0;
            }
            return 2;
        }
    }
 
    /**
     * Handles user authentication based on username, password, and ID.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param id       The ID of the user.
     * @return An integer representing the authentication status:
     * - 0 if authenticated and logged in,
     * - 1 if already connected and authenticated,
     * - 2 if not connected and authentication fails.
     */
    public static int userAuthenticationhandler(String username, String password, String id) {
        boolean connected = Connected(id);
        boolean memExist = checkEmployeePresence(username, password);
 
        if (connected && memExist)
            return 1;
        if (!connected && memExist) {
            enterToLoggedInDB(id);
            return 0;
        }
        return 2;
    }
 
    /**
     * Inserts the user into the logged-in database.
     *
     * @param id The ID of the user to insert.
     */
    private static void enterToLoggedInDB(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.INSERT_LOGGEDIN,
                new ArrayList<String>(Arrays.asList(id)));
        ClientUI.chat.accept(request);
    }
}