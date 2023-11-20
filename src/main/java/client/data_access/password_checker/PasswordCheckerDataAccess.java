package client.data_access.password_checker;

import client.use_case.PasswordChecker.PasswordCheckerDataAccessInterface;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PasswordCheckerDataAccess implements PasswordCheckerDataAccessInterface {
    @Override
    public boolean isPasswordValid(String password) {
        String sha1Hash = null;
        try {
            sha1Hash = PasswordCheck.toSHA1(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String hashPrefix = sha1Hash.substring(0, 5);
        String response = null;
        try {
            response = PasswordCheck.checkPwnedAPI(hashPrefix);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (response.toUpperCase().contains(sha1Hash.substring(5).toUpperCase())) {
            return false;

        }
        return true;
    }

    public class PasswordCheck {
        private static String toSHA1(String password) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }

        private static String checkPwnedAPI(String hashPrefix) throws Exception {
            URL url = new URL("https://api.pwnedpasswords.com/range/" + hashPrefix);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        }
    }
}