package com.xiaoheizi;

import com.xiaoheizi.packet.*;

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
                    System.out.println(username + " received from com.ikun.server: " + receivedPacket);
                }
            } catch (IOException | ClassNotFoundException ignored) {

            }
        }).start();

        send(new PacketClientSignup(username, password));
        send(new PacketClientLogin(username, password));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread.sleep(5000);
        List<TestClient> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            users.add(new TestClient("user" + i, "user"));
        }
        for (TestClient user : users) {
            for (int i = 0; i < 20; i++) {
                user.send(new PacketClientFriendRequest("user" + i));
            }
        }
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            users.get(random.nextInt(users.size())).send(new PacketClientTextMessage(i, random.nextInt(10), getRandomString()));
        }
    }

    private static String getRandomString() {
        String[] strings = {"The quick brown fox jumps over the lazy dog.", "A journey of a thousand miles begins with a single step.", "Life is like a bicycle, to keep your balance, you must keep moving.", "The sun sets in the west, painting the sky with vibrant hues.", "In the midst of winter, I found there was, within me, an invincible summer.", "Coding is the closest thing we have to a superpower.", "The best way to predict the future is to create it.", "Raindrops on roses and whiskers on kittens, bright copper kettles and warm woolen mittens.", "Every child is an artist. The problem is how to remain an artist once we grow up.", "Do not dwell in the past; do not dream of the future, concentrate the mind on the present moment."};

        /*String[] strings = {
                "Clean Architecture sucks.",
                "Clean Architecture cumbersome and overrated.",
                "Clean Architecture, destroy!",
                "Clean Architecture ruins my development.",
                "Not a fan of Clean Architectures",
                "Everything is better than Clean Architecture.",
                "You're right, but Clean Architecture...",
                "Imo, rather use Genshin Impact."
        };*/
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