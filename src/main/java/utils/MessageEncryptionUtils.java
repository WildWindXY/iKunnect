package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MessageEncryptionUtils {
    public static String md5Java(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8")); //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return digest;
    }
}
