package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Message;
import logic.MessagesTable;
import logic.Traveler;
import Controllers.CommunicationControl;
/**
* Controller class for viewing messages for a traveler in a GUI application.
* Allows users to view a list of their messages and read selected messages in detail.
*/

public class ShowMessagesController implements Initializable {
    
    ObservableList<MessagesTable> observable = FXCollections.observableArrayList(); 
    
    @FXML
    private Label headerLabel;

    @FXML
    private TableView<MessagesTable> messagesTableView;
    @FXML
    private TableColumn<MessagesTable, String> dateTableColumn;

    @FXML
    private TableColumn<MessagesTable, Integer> messageIdTableColumn;

    @FXML
    private TableColumn<MessagesTable, String> subjectTableColumn;
    
    @FXML
    private TextArea messageTextArea;

    /**
     * Initializes the controller after its root element has been completely processed.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTableView();
    }
    /**
     * Loads and displays the messages into the TableView.
     * Fetches messages for the currently logged-in traveler and updates the UI.
     */

    private void loadTableView() {
        String id;
        Traveler traveler = TravelerLoginController.traveler;
        if (traveler != null) {
            id = String.valueOf(traveler.getTravelerId());
        } else {
         
            return;
        }
        ArrayList<Message> messages = CommunicationControl.GetMessages(id);
        ArrayList<MessagesTable> tbMessages = MessagesToMessagesTable(messages);
        init(tbMessages);
        messagesTableView.setItems(getMessages(tbMessages));
    }

    /**
     * Converts a list of Message objects into a list of MessagesTable objects suitable for display in a TableView.
     * 
     * @param messages The list of Message objects to convert.
     * @return A list of MessagesTable objects.
     */


    private ArrayList<MessagesTable> MessagesToMessagesTable(ArrayList<Message> messages) {
        ArrayList<MessagesTable> MessagesTable = new ArrayList<MessagesTable>();
        for (Message message : messages) {
            MessagesTable messageTb = new MessagesTable(message);
            MessagesTable.add(messageTb);
        }
        return MessagesTable;
    }
    
    /**
     * Converts an ArrayList of MessagesTable into an ObservableList for use with a TableView.
     * 
     * @param TableMessages The ArrayList of MessagesTable to convert.
     * @return An ObservableList of MessagesTable objects.
     */
    private ObservableList<MessagesTable> getMessages(ArrayList<MessagesTable> TableMessages) {
        messagesTableView.getItems().clear();
        for (MessagesTable message : TableMessages) {
            observable.add(message);
        }
        return observable;
    }

    /**
     * Initializes the TableView, setting up cell value factories and a row factory for handling row double-clicks.
     * 
     * @param messages The list of MessagesTable objects to display in the TableView.
     */
    private void init(ArrayList<MessagesTable> messages) {
        messageIdTableColumn.setCellValueFactory(new PropertyValueFactory<MessagesTable, Integer>("messageId"));
        subjectTableColumn.setCellValueFactory(new PropertyValueFactory<MessagesTable, String>("subject"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<MessagesTable, String>("sendingDate"));
        messagesTableView.setTooltip(new Tooltip("Double click on a row to open it"));
        messagesTableView.setRowFactory(tv -> {
            TableRow<MessagesTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    MessagesTable clickedRow = row.getItem();
                    messageTextArea.setText(String.valueOf(clickedRow.getmessageContent()));
                }
            });
            return row;
        });
    }


  
}
