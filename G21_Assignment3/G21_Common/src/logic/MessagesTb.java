package logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class represents messages in the system, suitable for display in tables.
 */
public class MessagesTb {
    // JavaFX properties for message details
    private SimpleIntegerProperty messageId;
    private SimpleStringProperty toId;
    private SimpleStringProperty sendDate;
    private SimpleStringProperty subject;
    private SimpleStringProperty content;
    private SimpleStringProperty sendTime;
    private SimpleIntegerProperty orderId;
    
    /**
     * Constructor to initialize a MessagesTb object with specified details.
     * @param messageId The ID of the message.
     * @param toId The ID of the recipient.
     * @param sendDate The date when the message was sent.
     * @param sendTime The time when the message was sent.
     * @param subject The subject of the message.
     * @param content The content of the message.
     * @param orderId The ID of the associated order.
     */
    public MessagesTb(int messageId, String toId, String sendDate, String sendTime,
            String subject, String content, int orderId) {
        // Initialize JavaFX properties with provided values
        this.messageId = new SimpleIntegerProperty(messageId);
        this.toId = new SimpleStringProperty(toId);
        this.sendDate = new SimpleStringProperty(sendDate);
        this.subject = new SimpleStringProperty(subject);
        this.content = new SimpleStringProperty(content);
        this.sendTime = new SimpleStringProperty(sendTime);
        this.orderId = new SimpleIntegerProperty(orderId);
    }
    
    /**
     * Constructor to initialize a MessagesTb object with a Messages object.
     * @param message The Messages object to initialize the MessagesTb object.
     */
    public MessagesTb(Messages message) {
        // Initialize JavaFX properties with values from Messages object
        this.messageId = new SimpleIntegerProperty(message.getMessageId());
        this.toId = new SimpleStringProperty(message.getToId());
        this.sendDate = new SimpleStringProperty(message.getSendDate());
        this.subject = new SimpleStringProperty(message.getSubject());
        this.content = new SimpleStringProperty(message.getContent());
        this.sendTime = new SimpleStringProperty(message.getSendTime());
        this.orderId = new SimpleIntegerProperty(message.getOrderId());
    }

    // Getter and setter methods for accessing and modifying the JavaFX properties

    public int getMessageId() {
        return messageId.get();
    }

    public void setMessageId(SimpleIntegerProperty messageId) {
        this.messageId = messageId;
    }

    public String getToId() {
        return toId.get();
    }

    public void setToId(SimpleStringProperty toId) {
        this.toId = toId;
    }

    public String getSendDate() {
        return sendDate.get();
    }

    public void setSendDate(SimpleStringProperty sendDate) {
        this.sendDate = sendDate;
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(SimpleStringProperty subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(SimpleStringProperty content) {
        this.content = content;
    }
    
    public void setSendTime(SimpleStringProperty sendTime) {
        this.sendTime = sendTime;
    }
    
    public void setOrderId(SimpleIntegerProperty orderId) {
        this.orderId = orderId;
    }
    
    public String getSendTime() {
        return sendTime.get();
    }
    
    public int getOrderId() {
        return orderId.get();
    }
}
