package application.mainWindows.mailclient;

import application.Main;
import application.components.MailBoxesCard.MailBoxCard;
import application.components.emailCard.EmailCard;
import application.eventHandlers.mailclient.FolderListButtonHandler;
import application.eventHandlers.mailclient.OpenSendEmailButtonHandler;
import application.mainWindows.mailclient.threads.GetEmailBoxesThread;
import application.mainWindows.mailclient.threads.GetEmailsThread;
import email.connection.Connection;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.LinkedList;

public class MailClient {

    //Email Connection Objects
    private Connection connection;

    //FX Layouts from mail client
    private VBox mailsList;
    private VBox inboxList;
    private HBox menuBar;

    private GetEmailsThread emailThread;

    public void display() {
        //Creating and setting up the stage
        Stage stage = new Stage();
        stage.setTitle("Gmail Desktop");
        stage.getIcons().add(Main.PAGE_ICON);
        stage.setWidth(1250);
        stage.setHeight(750);

        //Main Layout
        BorderPane root = new BorderPane();

        //Side bar with Boxes
        inboxList = new VBox();
        root.setLeft(inboxList);

        //List of emails layout
        mailsList = new VBox();
        ScrollPane mailsListScroll = new ScrollPane(mailsList);
        root.setCenter(mailsListScroll);
        mailsListScroll.setFitToHeight(true);

        //Menu Layout
        menuBar = new HBox();
        root.setTop(menuBar);

        //Menu Buttons
        Button sendMail = new Button("Send Email");
        sendMail.setOnAction(new OpenSendEmailButtonHandler(connection));

        menuBar.getChildren().addAll(sendMail);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        try {
            getMailBoxes();
            setFolder("INBOX");
        } catch (MessagingException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmailCard(EmailCard card) {
        Platform.runLater(()->mailsList.getChildren().add(card));
    }

    public void removeEmailCard(EmailCard card) {
        Platform.runLater(() -> mailsList.getChildren().remove(card));
    }

    public void clearEmailLists() {
        Platform.runLater(()->mailsList.getChildren().clear());
    }

    private void getMailBoxes() throws MessagingException {
        LinkedList<String> folders = connection.getFoldersNames();
        FolderListButtonHandler buttonHandler = new FolderListButtonHandler(this);
        new GetEmailBoxesThread(this, folders).start();
    }

    public void setFolder(String folderName) throws MessagingException, IOException, InterruptedException {
        //Thread that runs the message list and adds it to the mail List
        if (emailThread != null) {
            emailThread.stopThread();
            emailThread.join();
        }

        Message[] messages = connection.getMessagesFromFolder(folderName);

        emailThread = new GetEmailsThread(messages, this);
        emailThread.setDaemon(true);
        emailThread.start();
    }

    public MailClient(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {return connection;}

    public void addBoxCard(MailBoxCard card) {
        Platform.runLater(()->inboxList.getChildren().add(card));
    }

}
