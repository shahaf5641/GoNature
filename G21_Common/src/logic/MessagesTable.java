package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class represents messages in the system, suitable for display in tables.
 */
public class MessagesTable {
    // JavaFX properties for message details
    private SimpleIntegerProperty messageId;
    private SimpleStringProperty recipientId;
    private SimpleStringProperty sendingDate;
    private SimpleStringProperty subject;
    private SimpleStringProperty messageContent;
    private SimpleStringProperty sendingTime;
    private SimpleIntegerProperty relatedOrderId;
    
    /**
     * Constructor to initialize a MessagesTb object with specified details.
     * @param messageId The ID of the message.
     * @param recipientId The ID of the recipient.
     * @param sendingDate The date when the message was sent.
     * @param sendingTime The time when the message was sent.
     * @param subject The subject of the message.
     * @param messageContent The messageContent of the message.
     * @param relatedOrderId The ID of the associated order.
     */
    public MessagesTable(int messageId, String recipientId, String sendingDate, String sendingTime,
            String subject, String messageContent, int relatedOrderId) {
        // Initialize JavaFX properties with provided values
        this.messageId = new SimpleIntegerProperty(messageId);
        this.recipientId = new SimpleStringProperty(recipientId);
        this.sendingDate = new SimpleStringProperty(sendingDate);
        this.subject = new SimpleStringProperty(subject);
        this.messageContent = new SimpleStringProperty(messageContent);
        this.sendingTime = new SimpleStringProperty(sendingTime);
        this.relatedOrderId = new SimpleIntegerProperty(relatedOrderId);
    }
    
    /**
     * Constructor to initialize a MessagesTb object with a Messages object.
     * @param message The Messages object to initialize the MessagesTb object.
     */
    public MessagesTable(Message message) {
        // Initialize JavaFX properties with values from Messages object
        this.messageId = new SimpleIntegerProperty(message.getMessageId());
        this.recipientId = new SimpleStringProperty(message.getRecipientId());
        this.sendingDate = new SimpleStringProperty(message.getSendingDate());
        this.subject = new SimpleStringProperty(message.getSubject());
        this.messageContent = new SimpleStringProperty(message.getMessageContent());
        this.sendingTime = new SimpleStringProperty(message.getSendingDate());
        this.relatedOrderId = new SimpleIntegerProperty(message.getRelatedOrderId());
    }

    // Getter and setter methods for accessing and modifying the JavaFX properties

    public int getMessageId() {
        return messageId.get();
    }

    public void setMessageId(SimpleIntegerProperty messageId) {
        this.messageId = messageId;
    }

    public String getrecipientId() {
        return recipientId.get();
    }

    public void setrecipientId(SimpleStringProperty recipientId) {
        this.recipientId = recipientId;
    }

    public String getSendingDate() {
        return sendingDate.get();
    }



    public void setsendingDate(SimpleStringProperty sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(SimpleStringProperty subject) {
        this.subject = subject;
    }

    public String getmessageContent() {
        return messageContent.get();
    }

    public void setmessageContent(SimpleStringProperty messageContent) {
        this.messageContent = messageContent;
    }
    
    public void setsendingTime(SimpleStringProperty sendingTime) {
        this.sendingTime = sendingTime;
    }
    
    public void setrelatedOrderId(SimpleIntegerProperty relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }
    
    public String getsendingTime() {
        return sendingTime.get();
    }
    
    public int getrelatedOrderId() {
        return relatedOrderId.get();
    }
}
