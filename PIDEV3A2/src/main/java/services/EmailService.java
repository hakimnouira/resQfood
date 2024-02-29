package services;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailService {

    public static void sendEmailWithAttachment(String recipientEmail, String subject, String messageBody, String attachmentFilePath) throws MessagingException, IOException {
        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Sender's credentials
        String senderEmail = "taktak.siwar.ttt@gmail.com";
        String senderPassword = "yyvi mebr vljq geni";

        // Create session
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Create email message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail,"MAIL"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);

        // Create multipart content
        Multipart multipart = new MimeMultipart();

        // Add message body
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(messageBody);
        multipart.addBodyPart(messageBodyPart);

        // Add attachment
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File(attachmentFilePath));
        multipart.addBodyPart(attachmentBodyPart);

        // Set content
        message.setContent(multipart);

        // Send email
        Transport.send(message);
    }
}
