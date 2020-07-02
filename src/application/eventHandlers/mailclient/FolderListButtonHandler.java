package application.eventHandlers.mailclient;

import application.components.MailBoxesCard.MailBoxCard;
import application.mainWindows.mailclient.MailClient;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.mail.MessagingException;
import java.io.IOException;


public class FolderListButtonHandler implements EventHandler<MouseEvent> {

    private MailClient stage;

    public FolderListButtonHandler(MailClient stage) {
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        Node target = (Node) event.getTarget();
        while (!(target instanceof MailBoxCard)) {
            target = target.getParent();
        }

        String boxName = ((MailBoxCard) target).getBoxName();

        try {
            stage.setFolder(boxName);
        } catch (MessagingException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
