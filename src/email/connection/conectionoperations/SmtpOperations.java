package email.connection.conectionoperations;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SmtpOperations {

    public static Session connectSMTP(String userEmail, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail, password);
            }
        });
        return session;
    }

    public static void sendEmail(Session session, String toAddress, String subject, String body, String fromEmail) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        message.setSubject(subject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(body, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
