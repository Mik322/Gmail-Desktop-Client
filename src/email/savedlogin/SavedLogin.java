package email.savedlogin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SavedLogin {

    private static final String savedLoginDataPath = "loginData.db";

    public static Boolean hasSavedLogin() {
        File file = new File(savedLoginDataPath);
        Boolean answer = file.exists();
        return answer;
    }

    public static LoginCredentials getSavedValues() throws IOException, ClassNotFoundException {
        InputStream in = new FileInputStream(savedLoginDataPath);
        ObjectInputStream objectIn = new ObjectInputStream(in);
        LoginCredentials credentials = (LoginCredentials) objectIn.readObject();
        in.close();
        objectIn.close();
        return credentials;
    }

    public static void saveValues(String email, String password) throws IOException {
        if (!hasSavedLogin()) {
            new File(savedLoginDataPath).createNewFile();
        }
        OutputStream out = new FileOutputStream(savedLoginDataPath);
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(new LoginCredentials(email, password));
    }

    public static void deleteSavedValues() throws IOException {
        Files.deleteIfExists(Paths.get(savedLoginDataPath));
    }
}
