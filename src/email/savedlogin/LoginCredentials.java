package email.savedlogin;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
