import server.data_access.network.NetworkManager;
import utils.MessageEncryptionUtils;

import java.io.IOException;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        testServer();
    }

    public static void testServer() throws IOException {
        try {
            MessageEncryptionUtils.initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new NetworkManager();
    }
}