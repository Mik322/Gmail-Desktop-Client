package application.eventHandlers.mailclient.emailreadwindow;

import application.components.emailCard.EmailCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import javax.mail.MessagingException;

public class MarkAsUnreadButton implements EventHandler<ActionEvent> {

    private EmailCard emailCard;

    public MarkAsUnreadButton(EmailCard emailCard) {
        this.emailCard = emailCard;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Button button = (Button) event.getTarget();
            if (emailCard.isSeen()) {
                button.setTooltip(new Tooltip("Mark as Read"));
                button.setStyle("-fx-background-image: url(/images/icons/openEmail.png)");
                emailCard.setUnseen();
            } else {
                button.setTooltip(new Tooltip("Mark as Unread"));
                button.setStyle("-fx-background-image: url(/images/icons/closedEmail.png)");
                emailCard.setSeen();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
