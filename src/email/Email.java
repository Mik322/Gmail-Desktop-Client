package email;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.IOException;

public class Email {

    private String fromEmail, fromName, text, subject;
    private Message message;

    private Boolean hasFinishedProcessing = false;

    public Email(Message message) throws MessagingException {
        this.message = message;
        fromEmail = ((InternetAddress) message.getFrom()[0]).getAddress();
        subject = message.getSubject();
        fromName = ((InternetAddress) message.getFrom()[0]).getPersonal();
    }

    public String getText() {
        return text;
    }

    public void processPart() throws IOException, MessagingException {
        if (message.isMimeType("text/plain")) {
            text = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            text = "";
            extractText(multipart);
        }
    }

    private void extractText(Multipart multipart) throws MessagingException, IOException {
        int count = multipart.getCount();
        for (int i=0;i<count;i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                text = text + bodyPart.getContent() + "\n";
            } else if (bodyPart.isMimeType("text/html")) {
                text = text + Jsoup.parse((String) bodyPart.getContent()).text() + "\n";
            } else if (bodyPart.getContent() instanceof Multipart) {
                extractText((Multipart) bodyPart.getContent());
            }
        }
        return;
    }

    public Boolean getHasFinishedProcessing() {
        return hasFinishedProcessing;
    }

    public void setHasFinishedProcessing(Boolean hasFinishedProcessing) {
        this.hasFinishedProcessing = hasFinishedProcessing;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFlag(Flags.Flag flag, Boolean status) throws MessagingException {
        message.setFlag(flag, status);
    }

    public void delete() throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
    }

    public void markAsRead() throws MessagingException {
        message.setFlag(Flags.Flag.SEEN, true);
    }

    public void markAsUnread() throws MessagingException {
        message.setFlag(Flags.Flag.SEEN, false);
    }

    public Boolean isRead() throws MessagingException {
        return message.getFlags().contains(Flags.Flag.SEEN);
    }
}
