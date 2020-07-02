package application.mainWindows.mailclient.threads;

import application.components.MailBoxesCard.MailBoxCard;
import application.eventHandlers.mailclient.FolderListButtonHandler;
import application.mainWindows.mailclient.MailClient;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.LinkedList;

public class GetEmailBoxesThread extends Thread {

    private LinkedList<String> folders;
    private MailClient mailClient;

    public GetEmailBoxesThread(MailClient mailClient, LinkedList<String> folders) {
        this.folders = folders;
        this.mailClient = mailClient;
    }

    public void run() {
        FolderListButtonHandler handler = new FolderListButtonHandler(mailClient);
        for (String folder : folders) {
            if (folder.equals("[Gmail]")) break;
            try {
                MailBoxCard card = new MailBoxCard(folder);
                card.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
                mailClient.addBoxCard(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
