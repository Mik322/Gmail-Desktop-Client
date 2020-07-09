package application.windows.controllers;

import application.Main;
import application.components.MailBoxesCard.MailBoxCard;
import application.components.emailCard.EmailCard;
import application.eventHandlers.mailclient.FolderListButtonHandler;
import application.eventHandlers.mailclient.LogOutMenuHandler;
import application.eventHandlers.mailclient.OpenSendEmailButtonHandler;
import application.threads.GetEmailBoxesThread;
import application.threads.GetEmailsThread;
import application.windows.Window;
import email.connection.Connection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailClient implements Window {

    //Email Connection Objects
    private Connection connection;

    private ArrayList<Window> openWindows = new ArrayList<>();

    //FX Layouts from mail client
    @FXML
    private VBox mailsList, inboxList;
    @FXML
    private HBox menuBar;
    @FXML
    private Button sendMail;
    @FXML
    private MenuItem closeMenu, logOutMenu;

    private Stage stage;

    private GetEmailsThread emailThread;

    private ExecutorService emailProcessingPool = Executors.newFixedThreadPool(10);

    public void display() throws IOException {
        //Creating and setting up the stage
        stage = new Stage();
        stage.setTitle("Gmail Desktop");
        stage.getIcons().add(new Image(Main.PAGE_ICON_PATH));
        stage.setWidth(1250);
        stage.setHeight(750);

        stage.setOnCloseRequest(e -> safeClose());

        //Main Layout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MailClient.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        //Menu Items
        closeMenu.setOnAction(e -> safeClose());
        logOutMenu.setOnAction(new LogOutMenuHandler(this));

        //Email functionality buttons
        sendMail.setOnAction(new OpenSendEmailButtonHandler(this));

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

    @Override
    public void close() {
        stage.close();
    }

    public void closeOpenWindows() {
        for (Window w: openWindows) {
            w.close();
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
        stopEmailThread();

        Message[] messages = connection.getMessagesFromFolder(folderName);

        emailThread = new GetEmailsThread(messages, this);
        emailThread.setDaemon(true);
        emailThread.start();
    }

    public ExecutorService getEmailProcessingPool() {
        return emailProcessingPool;
    }

    private void stopEmailThread() throws InterruptedException {
        if (emailThread != null) {
            emailThread.stopThread();
            emailThread.join();
        }
    }

    public void safeClose() {
        try {
            stopEmailThread();
            emailProcessingPool.shutdown();
            openWindows.forEach(w -> w.close());
            stage.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addOpenWindow(Window w) {
        openWindows.add(w);
    }

    public void removeOpenWindow(Window w) {
        openWindows.remove(w);
    }

    public MailClient(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {return connection;}

    public void addBoxCard(MailBoxCard card) {
        Platform.runLater(()->inboxList.getChildren().add(card));
    }

}
