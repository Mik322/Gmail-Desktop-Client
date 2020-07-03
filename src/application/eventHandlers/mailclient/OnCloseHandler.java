package application.eventHandlers.mailclient;

import application.mainWindows.mailclient.MailClient;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class OnCloseHandler implements EventHandler<WindowEvent> {

    private MailClient client;

    public OnCloseHandler(MailClient client) {
        this.client = client;
    }

    @Override
    public void handle(WindowEvent event) {
        try {
            client.stopEmailThread();
            client.getEmailProcessingPool().shutdown();
            client.closeStage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
