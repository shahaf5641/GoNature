package logic;

import java.io.Serializable;

/**
 * This class represents messages in the system.
 */
@SuppressWarnings("serial")
public class Message implements Serializable {
    // Instance variables representing message details
    private int messageId;
    private String recipientId;
    private String sendingDate;
    private String sendingTime;
    private String subject;
    private String messageContent;
    private int relatedOrderId;

    /**
     * Constructor to initialize a Message object.
     *
     * @param messageId       The ID of the message.
     * @param recipientId     The ID of the recipient.
     * @param sendingDate     The date when the message was sent.
     * @param sendingTime     The time when the message was sent.
     * @param subject         The subject of the message.
     * @param messageContent  The content of the message.
     * @param relatedOrderId  The ID of the associated order.
     */
    public Message(int messageId, String recipientId, String sendingDate, String sendingTime, String subject, String messageContent, int relatedOrderId) {
        this.messageId = messageId;
        this.recipientId = recipientId;
        this.sendingDate = sendingDate;
        this.sendingTime = sendingTime;
        this.subject = subject;
        this.messageContent = messageContent;
        this.relatedOrderId = relatedOrderId;
    }

    // Getter and setter methods for accessing and modifying the instance variables
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getRelatedOrderId() {
        return relatedOrderId;
    }

    public void setRelatedOrderId(int relatedOrderId) {
        this.relatedOrderId = relatedOrderId;
    }
}
