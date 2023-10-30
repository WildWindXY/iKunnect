import server.data_access.network.NetworkManager;

import java.io.IOException;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Hi");
        new NetworkManager();
    }
}
