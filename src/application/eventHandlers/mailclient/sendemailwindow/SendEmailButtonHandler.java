package application.eventHandlers.mailclient.sendemailwindow;

import application.windows.controllers.SendEmailWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.apache.commons.validator.routines.EmailValidator;

import javax.mail.MessagingException;

public class SendEmailButtonHandler implements EventHandler<ActionEvent> {

    private SendEmailWindow sendEmailWindow;

    public SendEmailButtonHandler(SendEmailWindow sendEmailWindow) {
        this.sendEmailWindow = sendEmailWindow;
    }

    @Override
    public void handle(ActionEvent event) {
        String address = sendEmailWindow.getForAddress();
        if (!isEmailValid(address)) {
            return;
        }
        String subject = sendEmailWindow.getSubject();
        String emailBody = sendEmailWindow.getEmailBody();
        try {
            sendEmailWindow.getConnection().sendEmail(address, subject, emailBody);
            sendEmailWindow.getStage().close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();

        return  validator.isValid(email);
    }
}
