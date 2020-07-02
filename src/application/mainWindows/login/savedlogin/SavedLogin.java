package application.mainWindows.login.savedlogin;

import java.io.*;

public class SavedLogin {

    private static final String savedLoginDataPath = "loginData.db";

    public static Boolean hasSavedLogin() {
        File file = new File(savedLoginDataPath);
        Boolean answer = file.exists();
        return answer;
    }

    public static LoginCredentialStruct getSavedValues() throws IOException, ClassNotFoundException {
        InputStream in = new FileInputStream(savedLoginDataPath);
        ObjectInputStream objectIn = new ObjectInputStream(in);
        return (LoginCredentialStruct) objectIn.readObject();
    }

    public static void saveValues(String email, String password) throws IOException {
        if (!hasSavedLogin()) {
            new File(savedLoginDataPath).createNewFile();
        }
        OutputStream out = new FileOutputStream(savedLoginDataPath);
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(new LoginCredentialStruct(email, password));
    }
}
