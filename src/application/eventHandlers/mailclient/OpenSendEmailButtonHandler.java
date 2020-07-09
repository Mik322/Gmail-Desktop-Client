package application.eventHandlers.mailclient;

import application.windows.controllers.MailClient;
import application.windows.controllers.SendEmailWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class OpenSendEmailButtonHandler implements EventHandler<ActionEvent> {

    private MailClient client;

    public OpenSendEmailButtonHandler(MailClient client) {
        this.client = client;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            SendEmailWindow w = new SendEmailWindow(client);
            client.addOpenWindow(w);
            w.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
