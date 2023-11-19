package manual_tests;

import common.packet.PacketDebug;
import org.junit.Test;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerCapabilityTest {
    @Test
    public void attack() {
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            new Thread(() -> {
                String serverAddress = "localhost";
                int serverPort = 8964;
                try (Socket clientSocket = new Socket(serverAddress, serverPort)) {
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject(new PacketDebug("Client Number " + finalI));
                    out.flush();
                    Thread.sleep(500);
                } catch (Exception ignored) {

                }
            }).start();

        }
    }
}
