package client.data_access;

import common.packet.Packet;
import common.packet.PacketDebug;
import common.packet.PacketServerLoginResponse;
import common.packet.PacketServerMessage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDataAccessObject {

    private final LinkedBlockingQueue<Packet> receivedPacket = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerLoginResponse> loginResponses = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerMessage> serverMessages = new LinkedBlockingQueue<>();

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerDataAccessObject(String serverAddress, int serverPort) {
        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            receivePacket();

            packetClassification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivePacket() {
        Thread receiveThread = new Thread(() -> {
            try {
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof Packet) {
                        receivedPacket.add((Packet) obj);
                    } else {
                        throw new IOException("Received object is not a packet");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
    }

    private void packetClassification() {
        Thread receiveThread = new Thread(() -> {
            while (true) {
                try {
                    Packet packet = receivedPacket.take();
                    if (packet instanceof PacketDebug) {
                        System.out.println("Received from server (debug message): " + packet);
                    } else if (packet instanceof PacketServerMessage) {
                        serverMessages.add((PacketServerMessage) packet);
                    } else if (packet instanceof PacketServerLoginResponse) {
                        loginResponses.add((PacketServerLoginResponse) packet);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread.start();
    }

    public void sendPacket(Packet msg) {
        System.out.println("Message to send to server: " + msg.toString());
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
