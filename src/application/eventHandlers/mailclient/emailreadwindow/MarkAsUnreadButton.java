package application.eventHandlers.mailclient.emailreadwindow;

import application.components.emailCard.EmailCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javax.mail.MessagingException;

public class MarkAsUnreadButton implements EventHandler<ActionEvent> {

    private EmailCard emailCard;

    public MarkAsUnreadButton(EmailCard emailCard) {
        this.emailCard = emailCard;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            if (emailCard.isSeen()) {
                ((Button) event.getTarget()).setText("Mark as Read");
                emailCard.setUnseen();
            } else {
                ((Button) event.getTarget()).setText("Mark as Unread");
                emailCard.setSeen();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
