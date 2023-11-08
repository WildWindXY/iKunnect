import server.data_access.network.NetworkManager;

import java.io.IOException;

import static utils.MessageEncryptionUtils.initKey;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        try {
            initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new NetworkManager();
    }
}
