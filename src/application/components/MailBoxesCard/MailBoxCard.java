package application.components.MailBoxesCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MailBoxCard extends AnchorPane {

    @FXML
    private Label boxName;

    public MailBoxCard(String boxName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MailBoxCard.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        this.getStylesheets().add("application/components/MailBoxesCard/MailBoxCard.css");

        loader.load();

        this.boxName.setText(boxName);
    }
}
