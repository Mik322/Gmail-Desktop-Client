package application.eventHandlers.mailclient;

import application.windows.controllers.Login;
import application.windows.controllers.MailClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class LogOutMenuHandler implements EventHandler<ActionEvent> {

    private MailClient client;

    public LogOutMenuHandler(MailClient client) {
        this.client = client;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            client.safeClose();
            new Login().display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
