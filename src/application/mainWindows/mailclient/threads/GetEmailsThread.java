package application.mainWindows.mailclient.threads;

import application.components.emailCard.EmailCard;
import application.eventHandlers.mailclient.EmailCardClickHandler;
import application.mainWindows.mailclient.MailClient;
import email.Email;
import javafx.scene.input.MouseEvent;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.concurrent.Executor;

public class GetEmailsThread extends Thread {

    private Message[] messagesList;
    private MailClient mailClient;
    private Boolean stopped = false;

    public GetEmailsThread(Message[] messagesList, MailClient mailClient) {
        this.messagesList = messagesList;
        this.mailClient = mailClient;
    }

    public void run() {
        EmailCardClickHandler handler = new EmailCardClickHandler(mailClient);

        try {
            int i = messagesList.length - 1;
            mailClient.clearEmailLists();
            while (!stopped && i > 0) {
                EmailCard card = null;
                card = new EmailCard(new Email(messagesList[i]), mailClient);
                card.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);

                mailClient.getEmailProcessingPool().execute(new EmailTextProcessingRunnable(card));

                mailClient.addEmailCard(card);
                i--;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        stopped = true;
    }
}
