package com.xiaoheizi.client.data_access;

import com.xiaoheizi.client.use_case.add_friend.AddFriendDataAccessInterface;
import com.xiaoheizi.client.use_case.login.LoginDataAccessInterface;
import com.xiaoheizi.client.use_case.signup.SignupDataAccessInterface;
import com.xiaoheizi.packet.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerDataAccessObject implements SignupDataAccessInterface, LoginDataAccessInterface, AddFriendDataAccessInterface {

    private final LinkedBlockingQueue<Packet> receivedPacket = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerLoginResponse> loginResponses = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerSignupResponse> signupResponses = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerTextMessage> serverMessages = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketServerGetFriendListResponse> getFriendListResponses = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketServerTextMessageResponse> sendMessageResponses = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketServerFriendRequestFrom> friendRequestFrom = new LinkedBlockingQueue<>();


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
            JOptionPane.showMessageDialog(null, "Error: Cannot connect to server, please try again later", "Error", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0x2304);
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
                    System.out.println("Received: " + packet);
                    if (packet instanceof PacketDebug) {
                        System.out.println("Received from com.ikun.server (debug message): " + packet);
                    } else if (packet instanceof PacketServerTextMessage) {
                        serverMessages.add((PacketServerTextMessage) packet);
                    } else if (packet instanceof PacketServerLoginResponse) {
                        loginResponses.add((PacketServerLoginResponse) packet);
                    } else if (packet instanceof PacketServerTextMessageResponse) {
                        sendMessageResponses.add((PacketServerTextMessageResponse) packet);
                    } else if (packet instanceof PacketServerSignupResponse) {
                        signupResponses.add((PacketServerSignupResponse) packet);
                    } else if (packet instanceof PacketServerGetFriendListResponse) {
                        getFriendListResponses.add((PacketServerGetFriendListResponse) packet);
                    } else if (packet instanceof PacketServerFriendRequestFrom) {
                        friendRequestFrom.add((PacketServerFriendRequestFrom) packet);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread.start();
    }

    public synchronized void sendPacket(Packet msg) { //TODO: Exception should be thrown and handled outside
        System.out.println("Message to send to com.ikun.server: " + msg.toString());
        try {
            out.writeObject(msg);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PacketServerLoginResponse getLoginResponse() {
        try {
            return loginResponses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketServerGetFriendListResponse getFriendListResponse() {
        try {
            return getFriendListResponses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketServerTextMessageResponse getSendMessageResponse() {
        try {
            return sendMessageResponses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketServerTextMessage getReceiveMessage() {
        try {
            return serverMessages.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketServerFriendRequestFrom getFriendRequest() {
        try {
            return friendRequestFrom.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PacketServerSignupResponse signup(String username, String password) {
        sendPacket(new PacketClientSignup(username, password));
        return getSignupResponse();
    }

    public PacketServerLoginResponse login(String username, String password) {
        sendPacket(new PacketClientLogin(username, password));
        return getLoginResponse();
    }

    @Override
    public PacketServerGetFriendListResponse getFriendList() {
        sendPacket(new PacketClientGetFriendList());
        return getFriendListResponse();
    }

    public PacketServerSignupResponse getSignupResponse() {
        try {
            return signupResponses.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFriend(String username) {
        sendPacket(new PacketClientFriendRequest(username));
    }
}
