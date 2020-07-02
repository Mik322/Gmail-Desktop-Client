package application.eventHandlers.mailclient.emailreadwindow;

import application.components.emailCard.EmailCard;
import application.mainWindows.mailclient.windows.EmailReadWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.mail.MessagingException;

public class DeleteMailButtonHandler implements EventHandler<ActionEvent> {

    private EmailReadWindow emailWindow;

    public DeleteMailButtonHandler(EmailReadWindow emailWindow) {
        this.emailWindow = emailWindow;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            EmailCard card = emailWindow.getEmailCard();
            card.getEmail().delete();
            card.removeCard();
            emailWindow.getStage().close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
