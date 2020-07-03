package application.eventHandlers.mailclient;

import application.windows.controllers.SendEmailWindow;
import email.connection.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class OpenSendEmailButtonHandler implements EventHandler<ActionEvent> {

    private Connection connection;

    public OpenSendEmailButtonHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            new SendEmailWindow(connection).display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
