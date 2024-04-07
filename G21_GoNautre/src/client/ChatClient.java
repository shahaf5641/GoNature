package client;

import common.*;
import logic.RequestsFromClientToServer;
import logic.Request;
import logic.responseFromServerToClient;
import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;


@SuppressWarnings("rawtypes")
public class ChatClient extends AbstractClient {
	
	ChatIF clientUI;
	public static boolean awaitResponse = false;
	public static responseFromServerToClient responseFromServer;
	public static ArrayList<Request> requestsWaitingForApproval=new ArrayList<>();


	
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); 
		this.clientUI = clientUI;
		openConnection();
	}

	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		if (msg instanceof responseFromServerToClient) {
			responseFromServerToClient response = (responseFromServerToClient) msg;
			responseFromServer = response;	
		}
		
		else if (msg instanceof String) {
			String serverMsg = (String) msg;
			if (serverMsg.equals("Finished")) {
				//System.out.println("Finished handle client request");
			}
			if (serverMsg.equals("Server closing")) {
			}
				
		}
	}

	public void handleMessageFromClientUI(String message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;

			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}


	 
	public void handleMessageFromClientUI(RequestsFromClientToServer<?> message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);

			// wait for response
			while (awaitResponse) {

				try {
					Thread.sleep(100);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}


	public void quit() {
		try {
			this.closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}

