package application.mainWindows.login;

import application.Main;
import application.eventHandlers.login.LoginEventHandler;
import application.mainWindows.login.savedlogin.LoginCredentialStruct;
import application.mainWindows.login.savedlogin.SavedLogin;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Login {

    Stage stage;
    TextField email;
    PasswordField password;
    CheckBox saveLoginDataCheckbox;

    public void display() {
        //Configuring main stage
        stage = new Stage();
        stage.setTitle("Login");
        stage.initStyle(StageStyle.DECORATED);
        stage.setHeight(500);
        stage.setWidth(350);
        stage.getIcons().add(Main.PAGE_ICON);

        //Login Event Handler
        LoginEventHandler loginEventHandler = new LoginEventHandler(this);

        //Main Layout
        VBox root = new VBox(50);
        root.setAlignment(Pos.CENTER);

        //Email and Username default values
        String defaultEmail = "";
        String defaultPassword = "";

        try {
            if (SavedLogin.hasSavedLogin()) {
                LoginCredentialStruct savedValues = SavedLogin.getSavedValues();
                defaultEmail = savedValues.getEmail();
                defaultPassword = savedValues.getPassword();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Labels and controllers
        Label loginLabel = new Label("Enter your Gmail credentials");
        Label emailLabel = new Label("Email: ");
        email = new TextField();
        email.setText(defaultEmail);
        Label passwordLabel = new Label("Password: ");
        password = new PasswordField();
        password.setText(defaultPassword);
        saveLoginDataCheckbox = new CheckBox("Save login credentials");
        saveLoginDataCheckbox.fire();
        Button loginButton = new Button("Login");

        //Grid with email and password input
        GridPane emailAndPasswordGrid = new GridPane();
        emailAndPasswordGrid.add(emailLabel, 0, 0);
        emailAndPasswordGrid.add(email, 1, 0);
        emailAndPasswordGrid.add(passwordLabel, 0, 1);
        emailAndPasswordGrid.add(password, 1, 1);

        //Adding Event Listeners
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, loginEventHandler);
        root.addEventHandler(KeyEvent.KEY_PRESSED, loginEventHandler);

        //Adding controllers to main layout
        root.getChildren().addAll(loginLabel, emailAndPasswordGrid, saveLoginDataCheckbox, loginButton);

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
