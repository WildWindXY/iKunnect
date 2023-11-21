import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketClientSignup;
import common.packet.PacketClientTextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient {
    private final ObjectOutputStream out;

    public TestClient(String username, String password) throws IOException, InterruptedException {
        Socket clientSocket = new Socket("localhost", 0x2304);
        System.out.println("Connected to server");

        out = new ObjectOutputStream(clientSocket.getOutputStream());
        new Thread(() -> {
            try {
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                while (true) {
                    Packet receivedPacket = (Packet) in.readObject();
                    System.out.println(username + " received from server: " + receivedPacket);
                }
            } catch (IOException | ClassNotFoundException ignored) {

            }
        }).start();

        send(new PacketClientSignup(username, password));
        send(new PacketClientLogin(username, password));
        send(new PacketClientTextMessage(0, 0, "hello"));
        send(new PacketClientTextMessage(0, 1, "hello"));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new TestClient("user1", "user");
        new TestClient("user2", "user");
    }

    private void send(Packet packet) throws InterruptedException, IOException {
        out.writeObject(packet);
        out.flush();
        System.out.println("sent: " + packet);
        Thread.sleep(50);
    }
}