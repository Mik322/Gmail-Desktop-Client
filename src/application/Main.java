package application;

import application.windows.controllers.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String PAGE_ICON_PATH = "images/icons/GmailIcon.png";

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Login().display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
