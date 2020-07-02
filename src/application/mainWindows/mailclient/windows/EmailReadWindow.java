package application.mainWindows.mailclient.windows;

import application.Main;
import application.components.emailCard.EmailCard;
import application.eventHandlers.mailclient.emailreadwindow.DeleteMailButtonHandler;
import application.eventHandlers.mailclient.emailreadwindow.MarkAsUnreadButton;
import email.Email;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;

public class EmailReadWindow {

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
        stage.getIcons().add(Main.PAGE_ICON);

        //Main Layouts
        BorderPane root = new BorderPane();
        HBox menu = new HBox();
        BorderPane content = new BorderPane();
        root.setCenter(content);
        root.setTop(menu);

        //Header Layout that includes sender and subject
        GridPane header = new GridPane();
        header.setHgap(20);
        content.setTop(header);

        //Menu content
        Button deleteMail = new Button("Delete Email");
        deleteMail.setOnAction(new DeleteMailButtonHandler(this));
        Button markAsUnread = new Button("Mark as Unread");
        markAsUnread.setOnAction(new MarkAsUnreadButton(emailCard));
        menu.getChildren().addAll(deleteMail, markAsUnread);


        //Header Content
        //From field
        Label fromLabelHeader = new Label("From:");
        Label fromLabel = new Label(email.getFromEmail());
        header.add(fromLabelHeader, 0, 0 );
        header.add(fromLabel, 1, 0);

        //Subject Field
        Label subjectLabelHeader = new Label("Subject:");
        Label subjectLabel = new Label(email.getSubject());
        header.add(subjectLabelHeader, 0, 1);
        header.add(subjectLabel, 1, 1);

        //Message Body
        Label emailText = new Label(email.getText());
        ScrollPane textScroll = new ScrollPane(emailText);
        content.setCenter(textScroll);

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
