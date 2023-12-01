import common.packet.*;

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

//        send(new PacketClientSignup(username, password));
        send(new PacketClientLogin(username, password));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        TestClient user1 = new TestClient("user1", "user");
        TestClient user2 = new TestClient("user2", "user");
        user2.send(new PacketClientFriendRequest("user1"));
        user1.send(new PacketClientFriendRequest("user2"));
        user2.send(new PacketClientTextMessage(2, 0, "message 2"));
        user1.send(new PacketClientTextMessage(1, 1, "message 1"));
        user1.send(new PacketClientGetFriendList());
        user2.send(new PacketClientGetFriendList());
    }

    public void send(Packet packet) throws InterruptedException, IOException {
        out.writeObject(packet);
        out.flush();
        System.out.println("sent: " + packet);
        Thread.sleep(50);
    }
}