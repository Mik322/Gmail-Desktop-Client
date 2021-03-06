package application.windows.controllers;

import application.Main;
import application.eventHandlers.mailclient.sendemailwindow.SendEmailButtonHandler;
import application.windows.Window;
import email.connection.Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SendEmailWindow implements Window {

    @FXML
    private TextField forAddress, subject;
    @FXML
    private Button sendEmailButton;
    @FXML
    private TextArea emailBody;

    private Stage stage;
    private Connection connection;
    private MailClient client;

    public SendEmailWindow(MailClient client) {
        this.client = client;
        this.connection = client.getConnection();
    }

    public void display() throws IOException {
        //Get the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/SendEmailWindow.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        //Event Handlers
        sendEmailButton.setOnAction(new SendEmailButtonHandler(this));

        //Set the scene and stage
        Scene scene = new Scene(root, 1000, 500);
        stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.PAGE_ICON_PATH));
        stage.setTitle("Send Email");
        stage.setOnCloseRequest(e -> client.removeOpenWindow(this));

        //Show the stage
        stage.show();
    }

    @Override
    public void close() {
        stage.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public String getEmailBody() {
        return emailBody.getText();
    }

    public Stage getStage() {
        return stage;
    }

    public String getForAddress() {
        return forAddress.getText();
    }

    public String getSubject() {
        return subject.getText();
    }

    public void setForAddress(String forAddress) {
        this.forAddress.setText(forAddress);
    }

    public void setSubject(String subject) {
        this.subject.setText(subject);
    }
}
