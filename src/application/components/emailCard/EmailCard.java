package application.components.emailCard;

import application.windows.controllers.MailClient;
import email.Email;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;

public class EmailCard extends AnchorPane {

    @FXML
    private Label fromName, subject;

    private Email email;
    private MailClient mailClient;

    private static final String SEEN_BACKGROUND_STYLE = "-fx-background-color: rgba(220,220,220,0.7)";
    private static final String UNSEEN_BACKGROUND_STYLE = "-fx-background-color: rgb(244, 244, 244)";

    public EmailCard(Email email, MailClient mailClient) {
        try {
            this.email = email;
            this.mailClient = mailClient;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmailCard.fxml"));

            loader.setRoot(this);
            loader.setController(this);

            loader.load();

            fromName.setText(email.getFromName());
            subject.setText(email.getSubject());
            if (email.isRead())
                this.setStyle(SEEN_BACKGROUND_STYLE);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

    }

    public boolean isProcessing() {
        return !email.getHasFinishedProcessing();
    }

    public synchronized void waitForProcessing() throws InterruptedException {
        if (!email.getHasFinishedProcessing()) wait();
    }

    public synchronized void finishedProcessing() {
        email.setHasFinishedProcessing(true);
        notifyAll();
    }

    public void setSeen() throws MessagingException {
        if (!email.isRead()) {
            this.setStyle(SEEN_BACKGROUND_STYLE);
            email.markAsRead();
        }
    }

    public void setUnseen() throws MessagingException {
        if (email.isRead()) {
            this.setStyle(UNSEEN_BACKGROUND_STYLE);
            email.markAsUnread();
        }
    }

    public Boolean isSeen() throws MessagingException {
        return email.isRead();
    }

    public void removeCard() {
        try {
            email.delete();
            mailClient.removeEmailCard(this);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Email getEmail() {
        return email;
    }

    public MailClient getMailClient() {return mailClient;}

    public String getFromName() {
        return fromName.getText();
    }

    public void setFromName(String fromName) {
        this.fromName.setText(fromName);
    }

    public String getSubject() {
        return subject.getText();
    }

    public void setSubject(String subject) {
        this.subject.setText(subject);
    }
}
