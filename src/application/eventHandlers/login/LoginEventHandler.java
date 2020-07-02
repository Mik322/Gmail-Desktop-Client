package application.eventHandlers.login;

import application.mainWindows.login.Login;
import application.mainWindows.login.savedlogin.SavedLogin;
import application.mainWindows.mailclient.MailClient;
import email.connection.Connection;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import java.io.IOException;

public class LoginEventHandler implements EventHandler<Event> {
    private Login stage;

    public LoginEventHandler(Login stage) {
        this.stage = stage;
    }

    @Override
    public void handle(Event event) {
        //Stops the event if the key pressed was not an enter
        if ((event.getEventType() == KeyEvent.KEY_PRESSED) && !(((KeyEvent) event).getCode() == KeyCode.ENTER)) {
            return;
        }

        try {
            if (stage.getSaveLoginDataCheckbox().isSelected()) {
                SavedLogin.saveValues(stage.getEmail(), stage.getPassword());
            }
            Connection con = new Connection(stage.getEmail(), stage.getPassword());
            MailClient client = new MailClient(con);
            stage.getStage().close();
            client.display();
        } catch (AuthenticationFailedException e) {
            System.out.println("Auth failed");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
