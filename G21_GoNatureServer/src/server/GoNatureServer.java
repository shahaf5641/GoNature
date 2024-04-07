package server;

import java.sql.Connection;
import java.sql.SQLException;

import controllers.QueriesConnectionSQL.MysqlConnection;
import gui.ServerGUIController;
import javafx.scene.paint.Color;
import ocsf.server.*;
import server.ActiveThreads.AutomatedCancelOrders;
import server.ActiveThreads.CommunicationThread;
import server.ActiveThreads.SendNotificationToTravelers;
import server.ActiveThreads.UpdateOrderStatusFromConfirmToNotArrived;
import server.ActiveThreads.UpdateOrderStatusFromWaitingToCancel;
import server.ActiveThreads.UpdateOrderStatusVisitCompleted;
import server.ActiveThreads.UpdateTravelerExitStatus;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Shahaf Israel	
 * @author Sara Saleh
 * @author Shai Osmo
 * @author Yuval Sabato
 * 
 * @version March 2024
 */
public class GoNatureServer extends AbstractServer {

	private ServerGUIController serverGUIController;
	public static Connection mysqlconnection;

	/**
	 * Constructs an instance of the GoNature Server.
	 *
	 * @param port                The port number to connect on.
	 * @param serverGUIController The GUI Controller of the server
	 * 
	 * @throws Exception If failed to load the server
	 */
	public GoNatureServer(int port, ServerGUIController serverGUIController) throws Exception {
		super(port);
		this.serverGUIController = serverGUIController;
		try {
			mysqlconnection = MysqlConnection.getInstance().getConnection();
			serverGUIController.updateTextAreaLog("Server Connected");
			serverGUIController.updateTextAreaLog("DB Connected");
		} catch (Exception e) {
			serverGUIController.updateTextAreaLog("Failed to load DB");
			throw e;
		}

		CommunicationThread notifyThread = new CommunicationThread(mysqlconnection);
		new Thread(notifyThread).start();

		UpdateOrderStatusFromWaitingToCancel wtc = new UpdateOrderStatusFromWaitingToCancel(mysqlconnection);
		new Thread(wtc).start();
		
		UpdateTravelerExitStatus tex = new UpdateTravelerExitStatus(mysqlconnection);
		new Thread(tex).start();
		
		UpdateOrderStatusFromConfirmToNotArrived cna = new UpdateOrderStatusFromConfirmToNotArrived(mysqlconnection);
		new Thread(cna).start();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 * For each client's request a new thread is created
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		MatchRequestFromClient thread = new MatchRequestFromClient(client, msg);
		new Thread(thread).start();
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.
	 * Called when a client connect to the server.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		serverGUIController.updateTextAreaLog(client.toString() + " Connected");
		serverGUIController.updateTextAreaLog("Total connections to the server: " + this.getNumberOfClients());
	}

	/**
	 * This method overrides the one in the superclass.
	 * Called when a client disconnect from the server.
	 */
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		serverGUIController.updateTextAreaLog(client.toString() + " Disonnected");
	}

	/**
	 * This method overrides the one in the superclass.
	 * Called when a client disconnect and throw exception from the server.
	 */
	synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
		serverGUIController.updateTextAreaLog("Client Disonnected");
		serverGUIController.updateTextAreaLog("Total connections to the server: " + (this.getNumberOfClients() - 1));
	}

	/**
	 * Hook method.
	 * Called when The server closed. After 'serverStopped' method.
	 */
	@Override
	final protected void serverClosed() {
		try {
			if (mysqlconnection != null)
				mysqlconnection.close();
			System.out.println("Server has been closed");
		} catch (SQLException e) {
			System.out.println("Faild to close JDBC connection");
		}
		System.exit(0);
	}

}
