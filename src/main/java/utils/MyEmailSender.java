package utils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MyEmailSender {

    final String FROM = "feriel.bouguecha@gmail.com";
    final String PWD = "pfin bnnc rxxb kkij ";


    public static void send(String to,String sub,String msg){
    //Get properties object
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    //get Session
    Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("feriel.bouguecha@gmail.com","pfin bnnc rxxb kkij");
                }
            });
    //compose message
    try {
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject(sub);
        message.setText(msg);
        //send message
        Transport.send(message);
        System.out.println("message sent successfully");
    } catch (MessagingException e) {throw new RuntimeException(e);}
    }

}
class SendMailSSL{
    public static void main(String[] args) {
        int code= 12344;
        //from,password,to,subject,message
        MyEmailSender.send("feriel.bouguecha@gmail.com","hello rayen bouguecha","How r u? \n Sent by Intellij.\nThis is your code"+code);
        //change from, password and to
    }
}