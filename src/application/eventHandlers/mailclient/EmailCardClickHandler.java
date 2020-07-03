package application.eventHandlers.mailclient;

import application.components.emailCard.EmailCard;
import application.mainWindows.mailclient.MailClient;
import application.mainWindows.mailclient.windows.EmailReadWindow;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import javax.mail.MessagingException;
import java.io.IOException;


public class EmailCardClickHandler implements EventHandler<MouseEvent> {

    private MailClient mailClient;

    public EmailCardClickHandler(MailClient mailClient) {
        this.mailClient = mailClient;
    }

    @Override
    public void handle(MouseEvent event) {
        try {
            Node target = (Node) event.getTarget();
            while (!(target instanceof EmailCard)) {
                target = target.getParent();
            }
            EmailCard emailCard = ((EmailCard) target);
            emailCard.waitForProcessing();
            emailCard.setSeen();
            new EmailReadWindow(emailCard).display();
        } catch (MessagingException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
