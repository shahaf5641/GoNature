package Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import client.ChatClient;
import client.ClientUI;
import javafx.concurrent.Task;
import logic.ClientToServerRequest;
import logic.Messages;
import logic.ClientToServerRequest.Request;

/**
 * NotificationControl class handles all the notification related functionalities
 */
public class NotificationControl {

	/**
	 * This function gets and message and ask ask the server to add it to travelers messages.
	 * 
	 * @param toId     String object, travelerId
	 * @param sendDate String object, sending date
	 * @param sendTime String object, sending time
	 * @param subject  String object, subject of the message
	 * @param content  String object, subject of the message
	 * @param orderId  String object, regarding which order the message is.
	 * @return true on success, false otherwise.
	 */
	public static boolean sendMessageToTraveler(String toId, String sendDate, String sendTime, String subject,
			String content, String orderId) {

		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.SEND_MSG_TO_TRAVELER,
				new ArrayList<String>(Arrays.asList(toId, sendDate, sendTime, subject, content, orderId)));
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.isResult();

	}


	/**
	 * This function gets traveler messages by his ID
	 * 
	 * @param id - the traveler's id
	 * @return ArrayList of messages
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Messages> getMessages(String id) {
		ClientToServerRequest<String> request = new ClientToServerRequest<>(Request.GET_MESSAGES_BY_ID,
				new ArrayList<String>(Arrays.asList(id)));
		
		ClientUI.chat.accept(request);
		return ChatClient.responseFromServer.getResultSet();
	}

}
