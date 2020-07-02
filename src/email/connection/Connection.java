package email.connection;

import email.connection.conectionoperations.ImapOperations;
import email.connection.conectionoperations.SmtpOperations;

import javax.mail.*;
import java.io.IOException;
import java.util.LinkedList;

public class Connection {

    private Store imapStore;
    private Session smtpSession;
    private String userEmail;
    private Folder openFolder;

    public Connection(String userEmail, String password) throws MessagingException {
        if (userEmail == null || password == null) {
            throw new NullPointerException();
        }

        this.userEmail = userEmail;

        imapStore = ImapOperations.connectIMAP(userEmail, password);
        smtpSession = SmtpOperations.connectSMTP(userEmail, password);
    }

    public LinkedList<String> getFoldersNames() throws MessagingException {
        return ImapOperations.getFoldersNames(imapStore);
    }

    public Message[] getMessagesFromFolder(String folderName) throws MessagingException, IOException {
        if (openFolder != null) openFolder.close();
        return ImapOperations.getEmailsFromFolder(folderName, this);
    }

    public String getUserEmail() {return userEmail;}

    public Store getImapStore() {
        return imapStore;
    }

    public Session getSmtpSession() {
        return smtpSession;
    }

    public void setOpenFolder(Folder openFolder) {
        this.openFolder = openFolder;
    }

    public void sendEmail(String toAddress, String subject, String body) throws MessagingException {
        SmtpOperations.sendEmail(smtpSession, toAddress, subject, body, userEmail);
    }
}
