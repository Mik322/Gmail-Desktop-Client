package application.mainWindows.mailclient.threads;

import application.components.emailCard.EmailCard;

import javax.mail.MessagingException;
import java.io.IOException;

public class EmailTextProcessingRunnable implements Runnable {

    private EmailCard card;

    public EmailTextProcessingRunnable(EmailCard card) {
        this.card = card;
    }

    @Override
    public void run() {
        try {
            card.getEmail().processPart();
            card.finishedProcessing();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
