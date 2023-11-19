import client.data_access.ServerDataAccessObject;
import common.packet.Packet;
import common.packet.PacketClientSignup;

import static utils.MessageEncryptionUtils.md5Java;

public class TempClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 0x2304;
        ServerDataAccessObject dao = new ServerDataAccessObject(serverAddress, serverPort);
//        Packet msg = new PacketClientLogin("myuser1", md5Java("pwd123"));
        Packet msg = new PacketClientSignup("myuser5", md5Java("pwd12323"));
        dao.sendPacket(msg);
        Packet msg1 = new PacketClientSignup("myuser1", md5Java("pwd123"));
        dao.sendPacket(msg1);
        Packet msg2 = new PacketClientSignup("m", md5Java("pwd123"));
        dao.sendPacket(msg2);
        Packet msg3 = new PacketClientSignup("myufdsafdhsauifdhsahudfshafdfsa", md5Java("pwd123"));
        dao.sendPacket(msg3);
        Packet msg4 = new PacketClientSignup(null, md5Java("pwd123"));
        dao.sendPacket(msg4);
        Packet msg5 = new PacketClientSignup("myuser", null);
        dao.sendPacket(msg5);
        Packet msg6 = new PacketClientSignup(null, null);
        dao.sendPacket(msg6);
        Packet msg7 = new PacketClientSignup("myuser1.", md5Java("pwd123"));
        dao.sendPacket(msg7);
        while (true) {
            System.out.println(dao.getSignupResponse());
        }

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
