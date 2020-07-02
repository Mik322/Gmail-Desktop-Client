package application.eventHandlers.mailclient;

import application.mainWindows.mailclient.MailClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javax.mail.MessagingException;
import java.io.IOException;


public class FolderListButtonHandler implements EventHandler<ActionEvent> {

    private MailClient stage;

    public FolderListButtonHandler(MailClient stage) {
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        Button b = (Button) event.getSource();
        try {
            stage.setFolder(b.getText());
        } catch (MessagingException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
