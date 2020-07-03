package application.mainWindows.login;

import application.Main;
import application.eventHandlers.login.LoginEventHandler;
import application.mainWindows.login.savedlogin.LoginCredentials;
import application.mainWindows.login.savedlogin.SavedLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Login {

    Stage stage;

    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    CheckBox saveLoginDataCheckbox;
    @FXML
    Button loginButton;

    public void display() throws IOException {
        //Configuring main stage
        stage = new Stage();
        stage.setTitle("Login");
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image(Main.PAGE_ICON_PATH));

        //Loading the fxml VBox
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        loader.setController(this);
        VBox root = loader.load();

        //Login Event Handler
        LoginEventHandler loginEventHandler = new LoginEventHandler(this);

        //Email and Username default values
        String defaultEmail = "";
        String defaultPassword = "";

        try {
            if (SavedLogin.hasSavedLogin()) {
                LoginCredentials savedValues = SavedLogin.getSavedValues();
                defaultEmail = savedValues.getEmail();
                defaultPassword = savedValues.getPassword();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Setting values to the inputs
        email.setText(defaultEmail);
        password.setText(defaultPassword);
        saveLoginDataCheckbox.fire();


        //Adding Event Listeners
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, loginEventHandler);
        root.addEventHandler(KeyEvent.KEY_PRESSED, loginEventHandler);

        //Creating the scene
        Scene scene = new Scene(root);

        //Setting and showing scene
        stage.setScene(scene);
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public CheckBox getSaveLoginDataCheckbox() {
        return saveLoginDataCheckbox;
    }
}
