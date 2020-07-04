package application.eventHandlers.login;

import application.windows.controllers.Login;
import email.savedlogin.SavedLogin;
import application.windows.controllers.MailClient;
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
            Connection con = new Connection(stage.getEmail(), stage.getPassword());
            MailClient client = new MailClient(con);
            if (stage.getSaveLoginDataCheckbox().isSelected()) {
                SavedLogin.saveValues(stage.getEmail(), stage.getPassword());
            } else {
                SavedLogin.deleteSavedValues();
            }
            stage.getStage().close();
            client.display();
        } catch (AuthenticationFailedException e) {
            stage.addErrorLabel("Invalid credentials or connection timeout");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
