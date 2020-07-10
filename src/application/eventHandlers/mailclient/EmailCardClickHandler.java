package application.eventHandlers.mailclient;

import application.components.emailCard.EmailCard;
import application.windows.controllers.MailClient;
import application.windows.controllers.EmailReadWindow;
import javafx.application.Platform;
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
        Node target = (Node) event.getTarget();
        while (!(target instanceof EmailCard)) {
            target = target.getParent();
        }
        EmailCard emailCard = ((EmailCard) target);
        if (emailCard.isProcessing()) {
            new Thread(()->createAndOpenWindow(emailCard)).start();
        } else {
            createAndOpenWindow(emailCard);
        }
    }

    private void createAndOpenWindow(EmailCard emailCard) {
        try {
            emailCard.waitForProcessing();
            emailCard.setSeen();
            EmailReadWindow w = new EmailReadWindow(emailCard);
            mailClient.addOpenWindow(w);
            Platform.runLater(()-> {
                try {
                    w.display();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
