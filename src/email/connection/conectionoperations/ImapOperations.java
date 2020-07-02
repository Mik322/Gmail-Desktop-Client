package email.connection.conectionoperations;

import email.Email;
import email.connection.Connection;

import javax.mail.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class ImapOperations {

    public static Store connectIMAP(String userEmail, String password) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);

        Store imapStore;

        imapStore = session.getStore("imaps");
        imapStore.connect("imap.gmail.com", 993, userEmail, password);
        return imapStore;
    }

    public static LinkedList<String> getFoldersNames(Store imapStore) throws MessagingException {
        Folder[] folders = imapStore.getDefaultFolder().list();
        LinkedList<String> foldersNames = new LinkedList<>();

        for (Folder folder: folders) {
            foldersNames.add(folder.getName());
        }

        return foldersNames;
    }

    public static Message[] getEmailsFromFolder(String folderName, Connection connection) throws MessagingException, IOException {
        Folder folder = connection.getImapStore().getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        connection.setOpenFolder(folder);
        return folder.getMessages();
    }
}
