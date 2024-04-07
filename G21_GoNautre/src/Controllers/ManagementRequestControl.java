
package Controllers;
 
import java.util.ArrayList;
import client.ClientUI;
import logic.RequestsFromClientToServer;
import logic.RequestsFromClientToServer.Request;
 
public class ManagementRequestControl {
 
    /**
     * Updates the status of a management request.
     *
     * @param idrequest The ID of the request to update.
     * @param con       The confirmation status of the request (true for confirmed, false for not confirmed).
     */
    public static void updateRequestStatus(Integer idrequest, boolean con) {
        ArrayList<Integer> request = new ArrayList<>();
        request.add(idrequest);
        if (con)
            request.add(1);
        else
            request.add(0);
        RequestsFromClientToServer<?> requestConfirm = new RequestsFromClientToServer<>(Request.CONFIRM_REQUEST, request);
        ClientUI.chat.accept(requestConfirm);
    }
 
    /**
     * Displays active management requests.
     */
    public static void displayActiveRequests() {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.VIEW_MANAGER_REQUEST, new ArrayList<>());
        ClientUI.chat.accept(request);
    }
 
    /**
     * Inserts a new management request.
     *
     * @param Requests The list of requests to insert.
     */
    public static void insertRequest(ArrayList<?> Requests) {
        RequestsFromClientToServer<?> request = new RequestsFromClientToServer<>(Request.MANAGER_REQUEST, Requests);
        ClientUI.chat.accept(request);
    }
}