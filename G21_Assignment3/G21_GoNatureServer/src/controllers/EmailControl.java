package controllers;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import controllers.sqlHandlers.TravelersQueries;
import logic.GoNatureFinals;
import logic.Messages;
import server.GoNatureServer;

/**
 * EmailControl class handles all the email sending requests.
 */
public class EmailControl {
    
    // Access the TravelersQueries object for retrieving email addresses
    private static TravelersQueries travelersQueries = new TravelersQueries(GoNatureServer.mysqlconnection);
    
    // Sender email and password
    private static String senderEmail = GoNatureFinals.GO_NATURE_EMAIL;
    private static String senderPassword = GoNatureFinals.GO_NATURE_EMAIL_PASSWORD;
    
    /**
     * Sends an email to the traveler based on the provided message.
     * 
     * @param msg The message to send.
     * @return True if the email is sent successfully, false otherwise.
     */
    public static boolean sendEmail(Messages msg) {
        String sendTo = travelersQueries.getEmailByOrderID(msg.getOrderId());
        String subject = msg.getSubject();
        String messageToSend = msg.getContent();
        return sendEmail(sendTo, subject, messageToSend);
    }
    
    /**
     * Sends an email to GoNature email based on the provided message.
     * 
     * @param msg The message to send.
     * @return True if the email is sent successfully, false otherwise.
     */
    public static boolean sendEmailToGoNature(Messages msg) {
        String sendTo = GoNatureFinals.GO_NATURE_EMAIL;
        String subject = msg.getSubject();
        String messageToSend = msg.getContent();
        return sendEmail(sendTo, subject, messageToSend);
    }
    
    /**
     * Sends an email to the provided email address based on the provided message.
     * 
     * @param msg The message to send.
     * @param email The email address to send to.
     * @return True if the email is sent successfully, false otherwise.
     */
    public static boolean sendEmailToWithEmailInput(Messages msg, String email) {
        String sendTo = email;
        String subject = msg.getSubject();
        String messageToSend = msg.getContent();
        return sendEmail(sendTo, subject, messageToSend);
    }
    
    /**
     * Sends an email.
     * 
     * @param sendTo The recipient email address.
     * @param subject The email subject.
     * @param messageToSend The content of the email.
     * @return True if the email is sent successfully, false otherwise.
     */
    private static boolean sendEmail(String sendTo, String subject, String messageToSend) {
        Properties props = getProperties();
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        Message msg = prepareMessage(session, senderEmail, sendTo, subject, messageToSend);
        if (msg != null) {
            try {
                // Send message
                Transport.send(msg);
                return true;
            } catch (Exception e) {
                System.out.println("Failed to send email");
                return false;
            }
        }
        return false;
    }
    
    /**
     * Prepares the email message.
     * 
     * @param session The email session.
     * @param from The sender email address.
     * @param to The recipient email address.
     * @param subject The email subject.
     * @param messageToSend The content of the email.
     * @return The prepared email message.
     */
    private static Message prepareMessage(Session session, String from, String to, String subject, String messageToSend) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageToSend);
            message.setContent(messageToSend, "text/plain; charset=\"UTF-8\"");
            return message;
        } catch (MessagingException e) {
            System.out.println("Something went wrong while creating the message object");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Retrieves the email server properties.
     * 
     * @return The email server properties.
     */
    private static Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }
}
