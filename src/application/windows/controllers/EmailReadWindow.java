package application.windows.controllers;

import application.Main;
import application.components.emailCard.EmailCard;
import application.eventHandlers.mailclient.emailreadwindow.DeleteMailButtonHandler;
import application.eventHandlers.mailclient.emailreadwindow.MarkAsUnreadButton;
import email.Email;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;

public class EmailReadWindow {

    @FXML
    private Label fromLabel, subjectLabel, emailText;
    @FXML
    private Button deleteMail, markAsUnread;

    private Email email;
    private EmailCard emailCard;

    Stage stage;

    public EmailReadWindow(EmailCard emailCard) {
        this.email = emailCard.getEmail();
        this.emailCard = emailCard;
    }

    public void display() throws IOException, MessagingException {
        stage = new Stage();
        stage.setTitle(email.getSubject());
        stage.initModality(Modality.NONE);
        stage.setHeight(750);
        stage.setWidth(1000);
        stage.getIcons().add(new Image(Main.PAGE_ICON_PATH));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EmailReadWindow.fxml"));
        loader.setController(this);

        BorderPane root = loader.load();

        //Header Layout that includes sender and subject

        //Menu content
        deleteMail.setOnAction(new DeleteMailButtonHandler(this));
        markAsUnread.setOnAction(new MarkAsUnreadButton(emailCard));

        //Setting the label values
        fromLabel.setText(email.getFromEmail());
        subjectLabel.setText(email.getSubject());
        emailText.setText(email.getText());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public EmailCard getEmailCard() {
        return emailCard;
    }

    public Stage getStage() {
        return stage;
    }
}
