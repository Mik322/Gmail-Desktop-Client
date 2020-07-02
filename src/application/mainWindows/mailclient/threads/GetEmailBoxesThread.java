package application.mainWindows.mailclient.threads;

import application.components.MailBoxesCard.MailBoxCard;
import application.mainWindows.mailclient.MailClient;

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
        for (String folder : folders) {
            if (folder.equals("[Gmail]")) break;
            try {
                MailBoxCard card = new MailBoxCard(folder);
                mailClient.addBoxCard(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
