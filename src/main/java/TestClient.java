import common.packet.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        List<TestClient> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new TestClient("user" + i, "user"));
        }
        for (TestClient user : users) {
            for (int i = 0; i < 10; i++) {
                user.send(new PacketClientFriendRequest("user" + i));
            }
        }
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            users.get(random.nextInt(users.size())).send(new PacketClientTextMessage(i, random.nextInt(10), getRandomString()));
        }
    }

    private static String getRandomString() {
        String[] strings = {
                "Clean Architecture sucks.",
                "Clean Architecture cumbersome and overrated.",
                "Clean Architecture, destroy!",
                "Clean Architecture ruins my development.",
                "Not a fan of Clean Architectures",
                "Everything is better than Clean Architecture.",
                "You're right, but Clean Architecture...",
                "Imo, rather use Genshin Impact."
        };
        Random random = new Random();
        int randomIndex = random.nextInt(strings.length);
        return strings[randomIndex];
    }

    public void send(Packet packet) throws InterruptedException, IOException {
        out.writeObject(packet);
        out.flush();
        System.out.println("sent: " + packet);
        Thread.sleep(1);
    }
}