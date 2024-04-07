 
 
/**
 * The CommunicationControl class manages communication operations between the client and the server.
 * It includes methods for retrieving messages and sending messages to travelers.
 */
package Controllers;
 
import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import logic.RequestsFromClientToServer;
import logic.Message;
import logic.RequestsFromClientToServer.Request;
 
public class CommunicationControl {
 
    /**
     * Retrieves messages associated with a specified ID from the server.
     *
     * @param id The ID associated with the messages to retrieve.
     * @return An ArrayList containing Message objects retrieved from the server.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Message> GetMessages(String id) {
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.GET_MESSAGES_ID,
                new ArrayList<String>(Arrays.asList(id)));
 
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.getSuccessSet();
    }
 
    /**
     * Sends a message to a traveler with specified details.
     *
     * @param toId     The ID of the recipient traveler.
     * @param sendDate The date when the message is sent.
     * @param sendTime The time when the message is sent.
     * @param subject  The subject of the message.
     * @param content  The content of the message.
     * @param orderId  The ID of the order associated with the message.
     * @return True if the message is sent successfully, false otherwise.
     */
    public static boolean sendMessageToTraveler(String toId, String sendDate, String sendTime, String subject,
                                                String content, String orderId) {
 
        RequestsFromClientToServer<String> request = new RequestsFromClientToServer<>(Request.SEND_MSG_TRAVELER,
                new ArrayList<String>(Arrays.asList(toId, sendDate, sendTime, subject, content, orderId)));
        ClientUI.chat.accept(request);
        return ChatClient.responseFromServer.isSuccess();
 
    }
}
 