package application.windows.controllers;

import application.Main;
import application.eventHandlers.mailclient.sendemailwindow.SendEmailButtonHandler;
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

public class SendEmailWindow {

    @FXML
    private TextField forAddress, subject;
    @FXML
    private Button sendEmailButton;
    @FXML
    private TextArea emailBody;

    private Stage stage;
    private Connection connection;

    public SendEmailWindow(Connection connection) {
        this.connection = connection;
    }

    public void display() throws IOException {
        //Get the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SendEmailWindow.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        //Event Handlers
        sendEmailButton.setOnAction(new SendEmailButtonHandler(this));

        //Set the scene and stage
        Scene scene = new Scene(root, 1000, 500);
        stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.PAGE_ICON_PATH));

        //Show the stage
        stage.show();
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
}
