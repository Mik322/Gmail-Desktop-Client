package application.eventHandlers.mailclient.emailreadwindow;


import application.windows.controllers.EmailReadWindow;
import application.windows.controllers.MailClient;
import application.windows.controllers.SendEmailWindow;
import email.connection.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import java.io.IOException;

public class ReplyHandler implements EventHandler<ActionEvent> {

    private Connection connection;
    private EmailReadWindow readWindow;
    private MailClient client;

    public ReplyHandler(EmailReadWindow readWindow) {
        this.client = readWindow.getEmailCard().getMailClient();
        this.connection = client.getConnection();
        this.readWindow = readWindow;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            SendEmailWindow window = new SendEmailWindow(client);
            client.removeOpenWindow(readWindow);
            this.readWindow.close();
            client.addOpenWindow(window);
            window.display();
            window.setSubject("RE: ".concat(readWindow.getSubjectLabel().getText()));
            window.setForAddress(readWindow.getFromLabel().getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
