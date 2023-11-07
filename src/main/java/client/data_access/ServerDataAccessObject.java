package client.data_access;

import common.packet.Packet;
import common.packet.PacketDebug;

import java.io.*;
import java.net.Socket;

public class ServerDataAccessObject {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerDataAccessObject(String serverAddress, int serverPort) {
        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            receivePacket();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receivePacket() {
        Thread receiveThread = new Thread(() -> {
            try {
                while (true) {
                    PacketDebug receivedPacket = (PacketDebug) in.readObject();
                    System.out.println("Received from server: " + receivedPacket);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();
    }

    public void sendPacket(String message) {
        System.out.print("Message to send to server: " + message.toString());
        Packet packet = new PacketDebug(message);
        try {
            out.writeObject(packet);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
