import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketDebug;
import client.data_access.ServerDataAccessObject;

import java.io.*;
import java.net.Socket;

import static utils.MessageEncryptionUtils.md5Java;

public class TempClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8964;
        ServerDataAccessObject dao = new ServerDataAccessObject(serverAddress, serverPort);
        Packet msg = new PacketClientLogin("myuser1", md5Java("pwd123"));
        dao.sendPacket(msg);


//        try {
//            Socket clientSocket = new Socket(serverAddress, serverPort);
//            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);
//
//            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
//
//            Thread receiveThread = new Thread(() -> {
//                try {
//                    while (true) {
//                        PacketDebug receivedPacket = (PacketDebug) in.readObject();
//                        System.out.println("Received from server: " + receivedPacket);
//                    }
//                } catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            });
//            receiveThread.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            while (true) {
//                System.out.print("Enter a message to send to server: ");
//                String message = reader.readLine();
//                Packet packet = new PacketDebug(message);
//                out.writeObject(packet);
//                out.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
