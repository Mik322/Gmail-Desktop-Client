package application.mainWindows.login.savedlogin;

import java.io.Serializable;

public class LoginCredentialStruct implements Serializable {
    private String email;
    private String password;

    public LoginCredentialStruct(String email, String password) {
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
