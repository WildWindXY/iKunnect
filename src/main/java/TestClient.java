import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketClientTextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient {
    public static ObjectOutputStream out;

    public static void main(String[] args) throws InterruptedException, IOException {
        Socket clientSocket = new Socket("localhost", 0x2304);
        System.out.println("Connected to server");

        out = new ObjectOutputStream(clientSocket.getOutputStream());
        new Thread(() -> {
            try {
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                while (true) {
                    Packet receivedPacket = (Packet) in.readObject();
                    System.out.println("Received from server: " + receivedPacket);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();

//        send(new PacketClientSignup("user1", "user"));
//        send(new PacketClientSignup("user2", "user"));
//        send(new PacketClientLogin("user1", "user"));
        send(new PacketClientLogin("user2", "user"));
        send(new PacketClientTextMessage(0, 0, "hello"));
    }

    public static void send(Packet packet) throws InterruptedException, IOException {
        out.writeObject(packet);
        out.flush();
        System.out.println("sent: " + packet);
        Thread.sleep(50);
    }
}